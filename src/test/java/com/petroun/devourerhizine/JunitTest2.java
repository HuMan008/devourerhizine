package com.petroun.devourerhizine;

import com.petroun.devourerhizine.classes.tools.EntityUtil;
import com.petroun.devourerhizine.config.GTConfig;
import com.petroun.devourerhizine.enums.EnumCardStatus;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import com.petroun.devourerhizine.provider.gt.GTGateWay;
import com.petroun.devourerhizine.service.Oil.MobileCardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JunitTest2 {

    @Autowired
    GTGateWay gtGateWay;

    @Autowired
    GTConfig gtConfig;

    @Autowired
    MobileCardService mobileCardService;

    @Test
    public void t1(){
        //gtGateWay.phoneRegisterQuery(gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        //gtGateWay.phoneRegister(gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        OilMobileCardInfo mobileCardInfo=mobileCardService.queryMobileCardByMobile("10100000001");
        gtGateWay.userBindCardQuery(mobileCardInfo,gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
    }

    @Test
    public void t2(){
        //980210236292877
        //gtGateWay.recharge("980210236296627",1000,gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        //gtGateWay.phoneRegister(gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        //gtGateWay.getUserToken("D5D50D2D9F472F68", "D5D50D2D9F472F6806354899",gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        //System.out.println(gtGateWay.getUserToken("10100000020", "06398031",gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword()));
        //gtGateWay.setPayPwd("10100000020","398031",gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        System.out.println(gtGateWay.getQRCode("10100000020","www.baidu.com",15000,60*10,gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword()));
    }


}
