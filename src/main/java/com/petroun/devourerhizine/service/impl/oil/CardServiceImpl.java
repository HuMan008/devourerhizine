package com.petroun.devourerhizine.service.impl.oil;

import cn.gotoil.bill.exception.BillException;
import com.petroun.devourerhizine.classes.tools.DateUtils;
import com.petroun.devourerhizine.enums.EnumCardStatus;
import com.petroun.devourerhizine.enums.EnumTranStatus;
import com.petroun.devourerhizine.model.View.ViewCardAndUse;
import com.petroun.devourerhizine.model.entity.*;
import com.petroun.devourerhizine.model.mapper.OilCardUseMapper;
import com.petroun.devourerhizine.model.mapper.OilMobileCardInfoMapper;
import com.petroun.devourerhizine.service.Oil.CardService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import javax.annotation.Resource;

@Service
public class CardServiceImpl implements CardService {

    @Resource
    OilCardUseMapper oilCardUseMapper;

    @Resource
    OilMobileCardInfoMapper mobileCardMapper;


    private OilMobileCardInfo getMobileCardByUseId(String useid){
        OilMobileCardInfoExample example = new OilMobileCardInfoExample();
        example.createCriteria().andBindIdEqualTo(useid);
        List<OilMobileCardInfo> list = mobileCardMapper.selectByExample(example);
        return list.get(0);
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


    private OilCardUse bindCard(OilMobileCardInfo bind_mobileCard,OilCardUse use,String mobile,int amonut){
        if(bind_mobileCard != null){
            OilMobileCardInfoExample updateExample = new OilMobileCardInfoExample();
            if(use == null) {//新绑卡
                updateExample.createCriteria().andCardNoEqualTo(bind_mobileCard.getCardNo()).andStatusEqualTo(EnumCardStatus.Enable.getCode());
            }else{//重新绑卡
                updateExample.createCriteria().andCardNoEqualTo(bind_mobileCard.getCardNo()).andStatusEqualTo(EnumCardStatus.Useing.getCode())
                .andBindIdEqualTo(use.getId());
            }

            OilMobileCardInfo updateCard = new OilMobileCardInfo();
            String id = use == null ? null : use.getId();
            if(StringUtils.isEmpty(id)){
                String currentDateTime = DateUtils.simpleDateTimeWithMilliSecondNoSymbolFormatter().format(new Date());
                id = currentDateTime.concat(RandomStringUtils.random(4, false, true));
            }

            updateCard.setStatus(EnumCardStatus.Useing.getCode());
            Date now = new Date();
            updateCard.setBindId(id);
            updateCard.setUpdatedAt(now);
            updateCard.setBindTime(now);
            int i = mobileCardMapper.updateByExample(updateCard,updateExample);
            if(i > 0){
                //重新绑定的卡
                if(use != null){
                    OilCardUse updateUse = new OilCardUse();
                    updateUse.setId(id);
                    updateUse.setQrcodeAmount(amonut);
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
                    insertUse.setQrcodeAmount(amonut);
                    insertUse.setCardNo(bind_mobileCard.getCardNo());
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
    public ViewCardAndUse getOilCard(String mobile,int amount){
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
                //解绑
                unbundlingAll(use.getId());
                //重新绑定
                use.setAmount(amount);
                bindCard(new_mobileCard,use,mobile,amount);

            }else{
                viewCardAndUse.setOilMobileCardInfo(Old_MobileCard);
            }

            return viewCardAndUse;
        }

        //不存在，或者不在使用中，绑卡
        OilMobileCardInfo mobileCard = getAmonutLess(amount);
        if(mobileCard != null){
            OilCardUse new_ues = bindCard(mobileCard,null,mobile,amount);
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
    public boolean updateCardUse(OilCardUse oilCardUse,String time,int sed){
        OilCardUse update = new OilCardUse();
        update.setId(oilCardUse.getId());
        update.setCreatedAt(DateUtils.strToDate(time));
        update.setValidityTime(DateUtils.DateAddSed(time,sed));
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
     * 解绑
     * @param useId
     * @return
     */
    private boolean unbundlingAll(String useId){
        OilMobileCardInfo card = getMobileCardByUseId(useId);
        card.setBindTime(null);
        card.setBindId(null);
        card.setStatus(EnumCardStatus.Enable.getCode());
        if(!card.getBindId().equals(useId)){
            return false;
        }
        if(mobileCardMapper.updateByPrimaryKey(card)> 0){
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
    public boolean unbundlingNotInTrading(String useId){
        OilCardUse use = oilCardUseMapper.selectByPrimaryKey(useId);
        if(use.getStatus() != EnumTranStatus.Trading.getCode()) {
            OilMobileCardInfo card = getMobileCardByUseId(useId);
            card.setBindTime(null);
            card.setBindId(null);
            card.setStatus(EnumCardStatus.Enable.getCode());
            if(!card.getBindId().equals(useId)){
                return false;
            }
            if(mobileCardMapper.updateByPrimaryKey(card)> 0){
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
    public List<OilCardInfo> getCardByStatus(Byte status){
        /*OilCardInfoExample example = new OilCardInfoExample();
        example.createCriteria().andStatusEqualTo(EnumCardStatus.Useing.getCode());
        return oilCardInfoMapper.selectByExample(example);*/
        return null;
    }
}
