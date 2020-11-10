package com.petroun.devourerhizine;

import com.petroun.devourerhizine.provider.gt.GTConfig;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import com.petroun.devourerhizine.provider.gt.GTGateWay;
import com.petroun.devourerhizine.service.Oil.CardService;
import com.petroun.devourerhizine.service.Oil.MobileCardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JunitTest2 {

    @Autowired
    GTGateWay gtGateWay;

    @Autowired
    GTConfig gtConfig;

    @Autowired
    MobileCardService mobileCardService;

    @Autowired
    CardService cardService;

    @Test
    public void t1(){
        //gtGateWay.phoneRegisterQuery(gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        //gtGateWay.phoneRegister(gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        OilMobileCardInfo mobileCardInfo=mobileCardService.queryMobileCardByMobile("10100000001");
        gtGateWay.userBindCardQuery(mobileCardInfo,gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
    }

    @Test
    public void t2(){
        OilMobileCardInfo mobileCardInfo=mobileCardService.queryMobileCardByMobile("10100000025");
        gtGateWay.userBindCardQuery(mobileCardInfo,gtConfig.getCopartnerId(), gtConfig.getCopartnerPassword());
        //gtGateWay.registerCard40("10100000025",gtConfig.getCopartnerId(), gtConfig.getCopartnerPassword());
        //gtGateWay.queryCardUserByRemote("202011101115322366312", gtConfig.getCopartnerId(), gtConfig.getCopartnerPassword());
        //gtGateWay.queryStaionName("70323013",gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
    }

    @Test
    public void t3(){
        gtGateWay.getQRCode("12345678901","www.baidu.com",20000,60,gtConfig.getCopartnerId(), gtConfig.getCopartnerPassword());


    }

}
