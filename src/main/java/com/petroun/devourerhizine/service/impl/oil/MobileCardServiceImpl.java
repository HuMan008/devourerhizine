package com.petroun.devourerhizine.service.impl.oil;

import com.petroun.devourerhizine.config.GTConfig;
import com.petroun.devourerhizine.enums.EnumCardStatus;
import com.petroun.devourerhizine.model.entity.OilMobileCardDetail;
import com.petroun.devourerhizine.model.entity.OilMobileCardDetailExample;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfoExample;
import com.petroun.devourerhizine.model.mapper.OilMobileCardDetailMapper;
import com.petroun.devourerhizine.model.mapper.OilMobileCardInfoMapper;
import com.petroun.devourerhizine.service.Oil.MobileCardService;
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
            mbCard.setMobile(String.valueOf(Integer.valueOf(mbCard.getMobile()) + 1));
        }
        mbCard.setSalt(RandomStringUtils.random(8, false, true));
        mbCard.setCreatedAt(new Date());
        mbCard.setStatus(EnumCardStatus.Enable.getCode());
        return mbCard;
    }

    @Override
    public OilMobileCardInfo getNewMobileCard(String mobile){
        OilMobileCardInfo mbcard = mobileCardInfoMapper.selectByPrimaryKey(mobile);
        if(mbcard == null){
            OilMobileCardInfo mbCard = new OilMobileCardInfo();
            mbCard.setMobile(mobile);
            mbCard.setSalt(RandomStringUtils.random(8, false, true));
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
    public boolean insertMobileCardDetails(List<OilMobileCardDetail> details){
        if(details != null && details.size() > 0){
            deleteCardDetailByMobile(details.get(0).getMobile());
            for(OilMobileCardDetail detail : details){
                if (oilMobileCardDetailMapper.insert(detail) > 0 ){
                    if(detail.getCardType() == 20){
                        updateCardInfo(detail.getMobile(),detail.getCardNo(),detail.getCardBalance());
                    }
                }
            }
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
}
