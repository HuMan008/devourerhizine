package com.petroun.devourerhizine.service.impl.oil;

import com.petroun.devourerhizine.enums.EnumCardStatus;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfoExample;
import com.petroun.devourerhizine.model.mapper.OilMobileCardInfoMapper;
import com.petroun.devourerhizine.service.Oil.MobileCardService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import java.util.*;

import javax.annotation.Resource;

@Service
public class MobileCardServiceImpl implements MobileCardService {

    public static final String HEADNUMBER = "100";

    @Resource
    OilMobileCardInfoMapper mobileCardInfoMapper;

    @Override
    public OilMobileCardInfo getNewMobileCard(){
        OilMobileCardInfoExample example = new OilMobileCardInfoExample();
        example.setLimit(1);
        example.setOrderByClause(" mobile desc ");
        List<OilMobileCardInfo> mbCards = mobileCardInfoMapper.selectByExample(example);
        OilMobileCardInfo mbCard = new OilMobileCardInfo();
        if(mbCards ==null || mbCards.size() == 0){
            mbCard.setMobile(HEADNUMBER+"00000001");
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
}
