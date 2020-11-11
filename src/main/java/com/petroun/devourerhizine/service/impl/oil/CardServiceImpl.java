package com.petroun.devourerhizine.service.impl.oil;

import cn.gotoil.bill.exception.BillException;
import com.petroun.devourerhizine.classes.tools.DateUtils;
import com.petroun.devourerhizine.classes.tools.EntityUtil;
import com.petroun.devourerhizine.provider.gt.GTConfig;
import com.petroun.devourerhizine.enums.EnumCardStatus;
import com.petroun.devourerhizine.enums.EnumOilSendStatus;
import com.petroun.devourerhizine.enums.EnumTranStatus;
import com.petroun.devourerhizine.model.View.gt.ViewCardAndUse;
import com.petroun.devourerhizine.model.entity.*;
import com.petroun.devourerhizine.model.mapper.OilCardUseMapper;
import com.petroun.devourerhizine.model.mapper.OilMobileCardInfoMapper;
import com.petroun.devourerhizine.provider.gt.GTGateWay;
import com.petroun.devourerhizine.service.oil.CardService;
import com.petroun.devourerhizine.service.oil.MobileCardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Resource
    OilCardUseMapper oilCardUseMapper;

    @Resource
    OilMobileCardInfoMapper mobileCardMapper;

    @Autowired
    GTGateWay gtGateWay;

    @Autowired
    GTConfig gtConfig;

    @Autowired
    MobileCardService mobileCardService;

    private OilMobileCardInfo getMobileCardCardNo(String cardNo){
        OilMobileCardInfoExample example = new OilMobileCardInfoExample();
        example.createCriteria().andCardNoEqualTo(cardNo);
        List<OilMobileCardInfo> list = mobileCardMapper.selectByExample(example);
        return list.get(0);
    }

    private OilMobileCardInfo getMobileCardByUseId(String useid){
        OilMobileCardInfoExample example = new OilMobileCardInfoExample();
        example.createCriteria().andBindIdEqualTo(useid);
        List<OilMobileCardInfo> list = mobileCardMapper.selectByExample(example);
        return list.size() > 0 ? list.get(0) : null;
    }

    private OilMobileCardInfo getAmonutLess(int amount){
        OilMobileCardInfoExample mobileCardInfoExample = new OilMobileCardInfoExample();
        mobileCardInfoExample.createCriteria().andStatusEqualTo(EnumCardStatus.Enable.getCode()).andBalanceLessThanOrEqualTo(amount);
        mobileCardInfoExample.setOrderByClause("balance desc");
        mobileCardInfoExample.setLimit(1);
        List<OilMobileCardInfo> mobileCards = mobileCardMapper.selectByExample(mobileCardInfoExample);
        if(mobileCards != null && mobileCards.size() > 0){
            return mobileCards.get(0);
        }
        return null;
    }


    private OilCardUse bindCard(String sendUrl,OilMobileCardInfo bind_mobileCard,OilCardUse use,String mobile,int amonut){
        if(bind_mobileCard != null){
            OilMobileCardInfoExample updateExample = new OilMobileCardInfoExample();
            updateExample.createCriteria().andCardNoEqualTo(bind_mobileCard.getCardNo()).andStatusEqualTo(EnumCardStatus.Enable.getCode());
            OilMobileCardInfo updateCard = new OilMobileCardInfo();
            String id = use == null ? null : use.getId();
            if(StringUtils.isEmpty(id)){
                id = EntityUtil.getId(4);
            }

            updateCard.setStatus(EnumCardStatus.Useing.getCode());
            Date now = new Date();
            updateCard.setBindId(id);
            updateCard.setUpdatedAt(now);
            updateCard.setBindTime(now);
            int i = mobileCardMapper.updateByExampleSelective(updateCard,updateExample);
            if(i > 0){
                //重新绑定的卡
                if(use != null){
                    OilCardUse updateUse = new OilCardUse();
                    updateUse.setId(id);
                    updateUse.setQrcodeAmount(amonut);
                    updateUse.setMobile(mobile);
                    updateUse.setCardMobile(bind_mobileCard.getMobile());
                    updateUse.setCardNo(bind_mobileCard.getCardNo());
                    if(oilCardUseMapper.updateByPrimaryKeySelective(updateUse) > 0) {
                        return oilCardUseMapper.selectByPrimaryKey(id);
                    }else{
                        throw new BillException(9999,"绑定油卡失败");
                    }
                }else{
                    //新绑卡
                    OilCardUse insertUse = new OilCardUse();

                    insertUse.setId(id);
                    insertUse.setSendUrl(sendUrl);
                    insertUse.setSendCount(0);
                    insertUse.setSendStatus(EnumOilSendStatus.Sending.getCode());
                    insertUse.setQrcodeAmount(amonut);
                    insertUse.setCardNo(bind_mobileCard.getCardNo());
                    insertUse.setCardMobile(bind_mobileCard.getMobile());
                    insertUse.setMobile(mobile);
                    insertUse.setStatus(EnumTranStatus.Trading.getCode());
                    //use.setCreatedAt(now);
                    if(oilCardUseMapper.insert(insertUse) > 0) {
                        return insertUse;
                    }else{
                        throw new BillException(9999,"绑定油卡失败");
                    }
                }
            }
        }
        return null;
    }

    /**
     * 获取油卡并设置为使用中,并锁定
     * 如果号码存在并在使用中，金额一致则直接返回，不一致重新绑卡
     * 如果号码不存在或者不在使用中，则新绑卡
     * @return
     */
    @Override
    @Transactional
    public ViewCardAndUse getOilCard(String sendUrl,String mobile,int amount){
        ViewCardAndUse viewCardAndUse = new ViewCardAndUse();

        //交易中直接返回
        OilCardUseExample useExample = new OilCardUseExample();
        useExample.createCriteria().andMobileEqualTo(mobile).andStatusEqualTo(EnumTranStatus.Trading.getCode());
        List<OilCardUse> listUser = oilCardUseMapper.selectByExample(useExample);
        if(listUser != null && listUser.size() > 0){
            OilCardUse use = listUser.get(0);
            OilMobileCardInfo Old_MobileCard = getMobileCardByUseId(use.getId());

            viewCardAndUse.setOilCardUse(use);
            viewCardAndUse.setType("old");

            if(Old_MobileCard.getBalance() != amount){
                //金额不一致换个金额最接近或相等
                OilMobileCardInfo new_mobileCard = getAmonutLess(amount);
                if(new_mobileCard == null){
                    throw new BillException(9999,"没有可用的卡");
                }
                //解绑
                unbundlingByUseing(use.getId());
                //重新绑定
                use.setAmount(amount);
                bindCard(sendUrl,new_mobileCard,use,mobile,amount);
                viewCardAndUse.setOilMobileCardInfo(new_mobileCard);
            }else{
                viewCardAndUse.setOilMobileCardInfo(Old_MobileCard);
            }

            return viewCardAndUse;
        }

        //不存在，或者不在使用中，绑卡
        OilMobileCardInfo mobileCard = getAmonutLess(amount);
        if(mobileCard == null){
            throw new BillException(9999,"没有可用的卡");
        }
        if(mobileCard != null){
            OilCardUse new_ues = bindCard(sendUrl,mobileCard,null,mobile,amount);
            if(new_ues == null){
                throw new BillException(9999,"绑定油卡失败");
            }
            viewCardAndUse.setOilCardUse(new_ues);
            viewCardAndUse.setOilMobileCardInfo(mobileCard);
            viewCardAndUse.setType("new");
            return viewCardAndUse;
        }else{
            throw new BillException(9999,"绑定油卡失败");
        }
    }

    /**
     * 更新创建时间，有效时间
     * @param oilCardUse
     * @param time
     * @param sed
     * @return
     */
    @Override
    public boolean updateCardUse(OilCardUse oilCardUse,String time,int sed,String strBRCode){
        OilCardUse old = oilCardUseMapper.selectByPrimaryKey(oilCardUse.getId());


        OilCardUse update = new OilCardUse();
        update.setId(oilCardUse.getId());
        if(old == null || old.getCreatedAt() == null){
            update.setCreatedAt(DateUtils.strToDate(time));
        }

        update.setQrcode(strBRCode);
        update.setValidityTime(DateUtils.DateAddSedGTTIME(time,sed));
        if(oilCardUseMapper.updateByPrimaryKeySelective(update) > 0){
            return true;
        }
        return false;
    }

    @Override
    public OilCardUse queryById(String id){
        return oilCardUseMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateOilCardUse(OilCardUse use){
        if(oilCardUseMapper.updateByPrimaryKeySelective(use) > 0){
            return true;
        }
        return false;
    }

    /**
     * 解绑使用中的卡
     * @param useId
     * @return
     */
    private boolean unbundlingByUseing(String useId){
        OilMobileCardInfo card = getMobileCardByUseId(useId);
        if(!card.getBindId().equals(useId)){
            return false;
        }
        card.setBindTime(null);
        card.setBindId(null);
        card.setStatus(EnumCardStatus.Enable.getCode());

        OilMobileCardInfoExample example = new OilMobileCardInfoExample();
        example.createCriteria().andMobileEqualTo(card.getMobile()).andStatusEqualTo(EnumCardStatus.Useing.getCode()).andBindIdEqualTo(useId);
        if(mobileCardMapper.updateByExample(card,example)> 0){
            return true;
        }
        return false;
    }

    /**
     * 解绑不在交易中的卡
     * @param useId
     * @return
     */
    @Override
    @Transactional
    public boolean unbundlingNotInTrading(String useId){
        OilCardUse use = oilCardUseMapper.selectByPrimaryKey(useId);
        if(use != null && use.getStatus() != EnumTranStatus.Trading.getCode()) {
            OilMobileCardInfo card = getMobileCardByUseId(useId);
            if(card == null || !card.getBindId().equals(useId)){
                return false;
            }
            card.setStatus(EnumCardStatus.Enable.getCode());
            card.setBindTime(null);
            card.setBindId(null);
            OilMobileCardInfoExample example = new OilMobileCardInfoExample();
            example.createCriteria().andMobileEqualTo(use.getCardMobile()).andStatusEqualTo(EnumCardStatus.Useing.getCode());

            if(mobileCardMapper.updateByExample(card,example)> 0){
                if(use.getStatus() == EnumTranStatus.success.getCode()) {
                    OilMobileCardInfo mobileCard = getMobileCardCardNo(use.getCardNo());
                    List<OilMobileCardDetail> details = gtGateWay.userBindCardQuery(mobileCard,gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
                    mobileCardService.insertOrUpdateMobileCardDetails(details);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public List<OilCardUse> queryCardUseByStatus(Byte status){
        OilCardUseExample example = new OilCardUseExample();
        example.createCriteria().andStatusEqualTo(status);
        return oilCardUseMapper.selectByExample(example);
    }

    @Override
    public boolean updateOilCardUseStatusAndunbundling(String useId, Byte status){
        if(EnumTranStatus.success.getCode() == status || EnumTranStatus.fail.getCode() == status ){
            OilCardUse use = new OilCardUse();
            use.setId(useId);
            use.setStatus(status);
            oilCardUseMapper.updateByPrimaryKeySelective(use);
            unbundlingNotInTrading(useId);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUse(OilCardUse use){
        if(use != null && use.getId() != null){
            return oilCardUseMapper.updateByPrimaryKeySelective(use) > 0 ? true : false;
        }
        return false;
    }



}
