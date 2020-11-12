package com.petroun.devourerhizine;

import com.petroun.devourerhizine.classes.tools.DateUtils;
import com.petroun.devourerhizine.enums.EnumTranStatus;
import com.petroun.devourerhizine.model.entity.OilCardUse;
import com.petroun.devourerhizine.model.entity.OilMobileCardDetail;
import com.petroun.devourerhizine.provider.gt.GTConfig;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import com.petroun.devourerhizine.provider.gt.GTGateWay;
import com.petroun.devourerhizine.service.oil.CardService;
import com.petroun.devourerhizine.service.oil.GotoilService;
import com.petroun.devourerhizine.service.oil.MobileCardService;
import com.petroun.devourerhizine.service.oil.OilService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    OilService oilService;

    @Autowired
    GotoilService gotoilService;

    @Test
    public void t1(){
        //gtGateWay.phoneRegisterQuery(gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        //gtGateWay.phoneRegister(gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        OilMobileCardInfo mobileCardInfo=mobileCardService.queryMobileCardByMobile("10100000001");
        gtGateWay.userBindCardQuery(mobileCardInfo,gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
    }

    @Test
    public void t2(){
        //OilMobileCardInfo mobileCardInfo=mobileCardService.queryMobileCardByMobile("10100000025");
        //gtGateWay.userBindCardQuery(mobileCardInfo,gtConfig.getCopartnerId(), gtConfig.getCopartnerPassword());
        //gtGateWay.registerCard40("10100000025",gtConfig.getCopartnerId(), gtConfig.getCopartnerPassword());
        //gtGateWay.queryCardUserByRemote("202011101115322366312", gtConfig.getCopartnerId(), gtConfig.getCopartnerPassword());
        //gtGateWay.queryStaionName("70323013",gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        //gtGateWay.queryStaionName("70323013",gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
    }

    @Test
    public void t3(){
       //gtGateWay.getQRCode("12345678901","www.baidu.com",20000,60,gtConfig.getCopartnerId(), gtConfig.getCopartnerPassword());
        //gtGateWay.queryCardUserByRemote("202011111047110671849",gtConfig.getCopartnerId(), gtConfig.getCopartnerPassword());
       // String code = oilService.getQRCode("http://grusgrus.dbbank.xyz:60000/qrcode/notify",5000,60*2,"12345678901");
        //System.out.println(code);
    }

    @Test
    public void t4(){
            oilService.queryMobileCardTrans("202011120931502052172");


    }

    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void t5(){

       //gtGateWay.queryStaionName("70323013",gtConfig.getCopartnerId(), gtConfig.getCopartnerPassword());
    }
}
