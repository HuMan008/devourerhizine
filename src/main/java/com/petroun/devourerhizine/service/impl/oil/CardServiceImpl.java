package com.petroun.devourerhizine.service.impl.oil;

import cn.gotoil.bill.exception.BillException;
import com.petroun.devourerhizine.classes.tools.DateUtils;
import com.petroun.devourerhizine.enums.EnumCardStatus;
import com.petroun.devourerhizine.enums.EnumTranStatus;
import com.petroun.devourerhizine.model.View.ViewCardAndUse;
import com.petroun.devourerhizine.model.entity.OilCardInfo;
import com.petroun.devourerhizine.model.entity.OilCardInfoExample;
import com.petroun.devourerhizine.model.entity.OilCardUse;
import com.petroun.devourerhizine.model.entity.OilCardUseExample;
import com.petroun.devourerhizine.model.mapper.OilCardInfoMapper;
import com.petroun.devourerhizine.model.mapper.OilCardUseMapper;
import com.petroun.devourerhizine.service.CardService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import javax.annotation.Resource;

@Service
public class CardServiceImpl implements CardService {

    @Resource
    OilCardInfoMapper oilCardInfoMapper;

    @Resource
    OilCardUseMapper oilCardUseMapper;

    /**
     * 获取油卡并设置为使用中,并锁定
     * 如果号码存在并在使用中，则直接返回
     * 如果号码不存在或者不在使用中，则从新绑卡
     * @return
     */
    @Override
    @Transactional
    public ViewCardAndUse getOilCard(String mobile){
        ViewCardAndUse viewCardAndUse = new ViewCardAndUse();

        //交易中直接返回
        OilCardUseExample useExample = new OilCardUseExample();
        useExample.createCriteria().andMobileEqualTo(mobile).andStatusEqualTo(EnumTranStatus.Trading.getCode());
        List<OilCardUse> listUser = oilCardUseMapper.selectByExample(useExample);
        if(listUser != null && listUser.size() > 0){
            OilCardUse use = listUser.get(0);
            viewCardAndUse.setOilCardUse(use);
            viewCardAndUse.setOilCardInfo(oilCardInfoMapper.selectByPrimaryKey(use.getCardNo()));
            viewCardAndUse.setType("old");
            return viewCardAndUse;
        }

        //不存在，或者不在使用中，绑卡
        OilCardInfoExample example = new OilCardInfoExample();
        example.createCriteria().andStatusEqualTo(EnumCardStatus.Enable.getCode());
        example.setOrderByClause("created_at");
        example.setLimit(1);
        List<OilCardInfo> cardInfo = oilCardInfoMapper.selectByExample(example);
        if(cardInfo != null && cardInfo.size() > 0){
            OilCardInfo card = cardInfo.get(0);
            OilCardInfoExample updateExample = new OilCardInfoExample();
            updateExample.createCriteria().andCardNoEqualTo(card.getCardNo()).andStatusEqualTo(EnumCardStatus.Enable.getCode());

            OilCardInfo updateCard = new OilCardInfo();
            String currentDateTime = DateUtils.simpleDateTimeWithMilliSecondNoSymbolFormatter().format(new Date());
            String id = currentDateTime.concat(RandomStringUtils.random(4, false, true));
            updateCard.setStatus(EnumCardStatus.Useing.getCode());
            Date now = new Date();
            updateCard.setBindId(id);
            updateCard.setUpdatedAt(now);
            updateCard.setBindTime(now);
            int i = oilCardInfoMapper.updateByExample(updateCard,updateExample);
            if(i > 0){
                card.setStatus(EnumCardStatus.Useing.getCode());

                OilCardUse use = new OilCardUse();

                use.setId(id);
                use.setCardNo(card.getCardNo());
                use.setMobile(mobile);
                use.setStatus(EnumTranStatus.Trading.getCode());
                //use.setCreatedAt(now);
                if(oilCardUseMapper.insert(use) > 0) {
                    viewCardAndUse.setOilCardUse(use);
                    viewCardAndUse.setOilCardInfo(card);
                    viewCardAndUse.setType("new");
                    return viewCardAndUse;
                }else{
                    throw new BillException(9999,"绑定油卡失败");
                }
            }else{
                throw new BillException(9999,"绑定油卡失败");
            }
        }
        return null;
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
     * 解除卡绑定
     * @param useId
     * @return
     */
    @Override
    public boolean unbundling(String useId){
        OilCardUse use = oilCardUseMapper.selectByPrimaryKey(useId);
        if(use.getStatus() != EnumTranStatus.Trading.getCode()) {
            OilCardInfo card = oilCardInfoMapper.selectByPrimaryKey(use.getCardNo());
            card.setBindTime(null);
            card.setBindId(null);
            card.setStatus(EnumCardStatus.Enable.getCode());
            if(!card.getBindId().equals(useId)){
                return false;
            }
            if(oilCardInfoMapper.updateByPrimaryKey(card)> 0){
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
            unbundling(useId);
            return true;
        }
        return false;
    }

    @Override
    public List<OilCardInfo> getCardByStatus(Byte status){
        OilCardInfoExample example = new OilCardInfoExample();
        example.createCriteria().andStatusEqualTo(EnumCardStatus.Useing.getCode());
        return oilCardInfoMapper.selectByExample(example);
    }
}
