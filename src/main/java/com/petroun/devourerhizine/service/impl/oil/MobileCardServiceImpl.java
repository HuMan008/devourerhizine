package com.petroun.devourerhizine.service.impl.oil;

import com.petroun.devourerhizine.provider.gt.GTConfig;
import com.petroun.devourerhizine.enums.EnumCardStatus;
import com.petroun.devourerhizine.model.entity.*;
import com.petroun.devourerhizine.model.mapper.OilMobileCardDetailMapper;
import com.petroun.devourerhizine.model.mapper.OilMobileCardInfoMapper;
import com.petroun.devourerhizine.service.oil.MobileCardService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import javax.annotation.Resource;

@Service
public class MobileCardServiceImpl implements MobileCardService {

    @Autowired
    GTConfig gtConfig;

    @Resource
    OilMobileCardInfoMapper mobileCardInfoMapper;

    @Resource
    OilMobileCardDetailMapper oilMobileCardDetailMapper;



    @Override
    public OilMobileCardInfo getNewMobileCard(){
        OilMobileCardInfoExample example = new OilMobileCardInfoExample();
        example.setLimit(1);
        example.setOrderByClause(" mobile desc ");
        List<OilMobileCardInfo> mbCards = mobileCardInfoMapper.selectByExample(example);
        OilMobileCardInfo mbCard = new OilMobileCardInfo();
        if(mbCards ==null || mbCards.size() == 0){
            mbCard.setMobile(gtConfig.getHeadNumber()+"00000001");
        }else{
            OilMobileCardInfo queryCard = mbCards.get(0);
            mbCard.setMobile(String.valueOf(Long.valueOf(queryCard.getMobile()) + 1));
        }
        mbCard.setSalt(RandomStringUtils.random(6, false, true));
        mbCard.setCreatedAt(new Date());
        mbCard.setBalance(0);
        mbCard.setStatus(EnumCardStatus.Enable.getCode());
        return mbCard;
    }

    @Override
    public OilMobileCardInfo getNewMobileCard(String mobile){
        OilMobileCardInfo mbcard = mobileCardInfoMapper.selectByPrimaryKey(mobile);
        if(mbcard == null){
            OilMobileCardInfo mbCard = new OilMobileCardInfo();
            mbCard.setMobile(mobile);
            mbCard.setSalt(RandomStringUtils.random(6, false, true));
            mbCard.setCreatedAt(new Date());
            mbCard.setStatus(EnumCardStatus.Enable.getCode());
            return mbCard;
        }
        return null;
    }

    @Override
    public boolean insertMobileCard(OilMobileCardInfo oilMobileCardInfo){
        if(mobileCardInfoMapper.insert(oilMobileCardInfo) > 0){
            return true;
        }
        return false;
    }

    @Override
    public OilMobileCardInfo queryMobileCardByMobile(String mobile){
        return mobileCardInfoMapper.selectByPrimaryKey(mobile);
    }


    @Override
    @Transactional
    public boolean insertOrUpdateMobileCardDetails(List<OilMobileCardDetail> details){
        if(details != null && details.size() > 0){
            deleteCardDetailByMobile(details.get(0).getMobile());
            for(OilMobileCardDetail detail : details){
                if (oilMobileCardDetailMapper.insert(detail) > 0 ){
                    if(detail.getCardType() == 40){
                        updateCardInfo(detail.getMobile(),detail.getCardNo(),detail.getCardBalance());
                    }
                }
            }
            return true;
        }

        return false;
    }

    public void deleteCardDetailByMobile(String mobile){
        OilMobileCardDetailExample example = new OilMobileCardDetailExample();
        example.createCriteria().andMobileEqualTo(mobile);
        oilMobileCardDetailMapper.deleteByExample(example);

    }

    public boolean updateCardInfo(String mobile,String cardNo,Integer balance){
        OilMobileCardInfo card = new OilMobileCardInfo();
        card.setMobile(mobile);
        card.setCardNo(cardNo);
        card.setBalance(balance);
        card.setUpdatedAt(new Date());
        return mobileCardInfoMapper.updateByPrimaryKeySelective(card) > 0 ? true :false;
    }

    /**
     * 充值后更新卡记录
     * @param cardNo
     * @param amount
     * @return
     */
    @Override
    public boolean recharge(String cardNo, int amount){
        OilMobileCardInfoExample example = new OilMobileCardInfoExample();
        example.createCriteria().andCardNoEqualTo(cardNo);
        OilMobileCardInfo update = new OilMobileCardInfo();
        update.setBalance(amount);
        update.setUpdatedAt(new Date());
        int i = mobileCardInfoMapper.updateByExampleSelective(update,example);

        OilMobileCardDetail detail = new OilMobileCardDetail();
        detail.setCardNo(cardNo);
        detail.setCardBalance(amount);
        detail.setUpdatedAt(new Date());

        i = i + oilMobileCardDetailMapper.updateByPrimaryKeySelective(detail);
        if(i == 2){
            return false;
        }
        return false;
    }

    /**
     * 根据卡号获取卡信息
     * @param cardNo
     * @return
     */
    @Override
    public OilMobileCardInfo getMobileCardInfoByCardNo(String cardNo){
        OilMobileCardInfoExample example = new OilMobileCardInfoExample();
        example.createCriteria().andCardNoEqualTo(cardNo);
        List<OilMobileCardInfo> cs = mobileCardInfoMapper.selectByExample(example);
        if(cs != null && cs.size() > 0){
            return cs.get(0);
        }
        return null;
    }

    @Override
    public long queryCardsInfo(List<Byte> status){
        OilMobileCardInfoExample example = new OilMobileCardInfoExample();
        example.createCriteria().andStatusIn(status);
        Long count = mobileCardInfoMapper.countByExample(example);
        if(count == null){
            count = 0L;
        }
        return count;
    }

    @Override
    public List<OilMobileCardInfo> getMobileCardsByStatus(List<Byte> status){
        OilMobileCardInfoExample example = new OilMobileCardInfoExample();
        example.createCriteria().andStatusIn(status);
        return mobileCardInfoMapper.selectByExample(example);
    }
}
