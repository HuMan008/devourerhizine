package com.petroun.devourerhizine.service.impl.oil;

import cn.gotoil.bill.exception.BillException;
import com.petroun.devourerhizine.classes.tools.DateUtils;
import com.petroun.devourerhizine.enums.EnumTranStatus;
import com.petroun.devourerhizine.model.View.gt.ViewCardAndUse;
import com.petroun.devourerhizine.model.View.gt.ViewQRCode;
import com.petroun.devourerhizine.model.entity.OilCardUse;
import com.petroun.devourerhizine.model.entity.OilMobileCardDetail;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import com.petroun.devourerhizine.provider.gt.GTConfig;
import com.petroun.devourerhizine.provider.gt.GTCopartnerConfig;
import com.petroun.devourerhizine.provider.gt.GTGateWay;
import com.petroun.devourerhizine.service.oil.CardService;
import com.petroun.devourerhizine.service.oil.GotoilService;
import com.petroun.devourerhizine.service.oil.MobileCardService;
import com.petroun.devourerhizine.service.oil.OilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.*;

@Service
public class OilServceImpl implements OilService {

    @Autowired
    GTGateWay gtGateWay;

    @Autowired
    CardService cardService;

    @Autowired
    GTConfig gtConfig;

    @Autowired
    MobileCardService mobileCardService;

    @Autowired
    GotoilService gotoilService;

    /**
     * 获取二维码
     * @param sendUrl
     * @param amount
     * @param sed
     * @param mobile
     * @return
     *
     * 首次绑卡需要充值
     * 绑卡之后不能充值
     */
    @Override
    @Transactional
    public String getQRCode(String sendUrl, int amount, int sed, String mobile){
        ViewCardAndUse cardAndUser = cardService.getOilCard(sendUrl,sed,mobile,amount);
        OilMobileCardInfo mobileCard = cardAndUser.getOilMobileCardInfo();
        OilCardUse oilCardUse = cardAndUser.getOilCardUse();

        GTCopartnerConfig gtConfig = getGTConfig();

        String token = gtGateWay.getUserToken(mobileCard.getMobile(),("06"+mobileCard.getSalt()),gtConfig.getCopartnerId(),gtConfig.getCopartnerPwd());

        if(!StringUtils.isEmpty(token)){
            Integer cardBalance = queryCardBalace(mobileCard, amount);
            if("new".equals(cardAndUser.getType())){
                //判断实际卡的余额是否大于消费金额
                if (cardBalance == null) {
                    throw new BillException(9999, "卡查询失败");
                }
                if(cardBalance.compareTo(amount) > 0){
                    throw new BillException(9999, "卡余额有误,请重试");
                }
                if(cardBalance.compareTo(amount) < 0){
                    if(!recharge(mobileCard.getCardNo(),(amount-cardBalance))){
                        throw new BillException(9999,"充值失败");
                    }
                }
            }else{
                if(cardBalance.compareTo(amount) < 0){
                    if(!recharge(mobileCard.getCardNo(),(amount-cardBalance))){
                        throw new BillException(9999,"充值失败");
                    }
                }
            }

            ViewQRCode QRCode = gtGateWay.getQRCode(cardAndUser,sed,token,gtConfig.getCopartnerId(),gtConfig.getCopartnerPwd());
            if(QRCode != null) {
                if(cardService.updateCardUse(oilCardUse, QRCode.getTime(), sed, QRCode.getStrBRCode())){
                    gotoilService.appendGotoilQueryQueue(oilCardUse.getId());
                    return QRCode.getStrBRCode();
                }
            }
        }else{
            oilCardUseClear(oilCardUse);
        }

        return null;
    }

    private boolean oilCardUseClear(OilCardUse use){
        if(StringUtils.isEmpty(use.getQrcode()) && use.getCreatedAt() == null){
            return cardService.unbundlingByUseing(use.getId());
        }
        return false;
    }

    /**
     * 充值
     * @param cardNo
     * @param amount
     * @return
     */
    private boolean recharge(String cardNo,int amount){
        GTCopartnerConfig gtConfig = getGTConfig();
        gtGateWay.recharge(cardNo,amount,gtConfig.getCopartnerId(),gtConfig.getCopartnerPwd());
        OilMobileCardInfo cardInfo = mobileCardService.getMobileCardInfoByCardNo(cardNo);
        List<OilMobileCardDetail> insertDetails = gtGateWay.userBindCardQuery(cardInfo,gtConfig.getCopartnerId(),gtConfig.getCopartnerPwd());
        if(insertDetails == null){
            throw new BillException(9999,"查询卡信息失败");
        }
        return mobileCardService.insertOrUpdateMobileCardDetails(insertDetails);
    }

    private Integer queryCardBalace(OilMobileCardInfo mobileCard,int amonut){
        GTCopartnerConfig gtConfig = getGTConfig();
        List<OilMobileCardDetail> insertDetails = gtGateWay.userBindCardQuery(mobileCard,gtConfig.getCopartnerId(),gtConfig.getCopartnerPwd());
        if(insertDetails == null){
            throw new BillException(9999,"查询卡信息失败");
        }
        for(OilMobileCardDetail detail : insertDetails){
            if(detail.getCardType() == 40){
                if(detail.getCardBalance() == amonut){
                    return detail.getCardBalance();
                }else{
                    mobileCardService.insertOrUpdateMobileCardDetails(insertDetails);
                    return detail.getCardBalance();
                }

            }
        }
        return null;

    }

    public GTCopartnerConfig getGTConfig(){
        GTCopartnerConfig config = new GTCopartnerConfig();
        config.setCopartnerId(gtConfig.getCopartnerId());
        config.setCopartnerPwd(gtConfig.getCopartnerPassword());
        return config;
    }

    /**
     * 查询交易结果
     * @param useId
     * @return
     */
    @Override
    public OilCardUse queryMobileCardTrans(String useId){
        Date now = new Date();
        OilCardUse cardUse = cardService.queryById(useId);
        if(cardUse == null){
            return null;
        }
        OilCardUse query = gtGateWay.queryCardUserByRemote(cardUse.getId(), gtConfig.getCopartnerId(), gtConfig.getCopartnerPassword());
        if(query != null){
            if(query.getStatus() != EnumTranStatus.success.getCode()
                    && now.compareTo(DateUtils.DateAddSed(cardUse.getValidityTime(),60)) > 0){
                cardService.updateOilCardUseStatusAndunbundling(cardUse.getId(),EnumTranStatus.fail.getCode());
            }
        }else{
            if(now.compareTo(DateUtils.DateAddSed(cardUse.getValidityTime(),60)) > 0){
                cardService.updateOilCardUseStatusAndunbundling(cardUse.getId(),EnumTranStatus.fail.getCode());
            }
        }
        return cardService.queryById(useId);
    }
}
