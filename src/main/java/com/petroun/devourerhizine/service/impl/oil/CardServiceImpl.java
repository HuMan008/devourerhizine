package com.petroun.devourerhizine.service.impl.oil;

import com.petroun.devourerhizine.model.entity.OilCardInfo;
import com.petroun.devourerhizine.model.entity.OilCardInfoExample;
import com.petroun.devourerhizine.model.mapper.OilCardInfoMapper;
import com.petroun.devourerhizine.service.CardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CardServiceImpl implements CardService {

    @Resource
    OilCardInfoMapper oilCardInfoMapper;

    public OilCardInfo getOilCard(){
        OilCardInfoExample example = new OilCardInfoExample();

        return null;
    }
}
