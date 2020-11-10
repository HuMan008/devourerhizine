package com.petroun.devourerhizine;

import com.petroun.devourerhizine.classes.tools.DateUtils;
import com.petroun.devourerhizine.classes.tools.EntityUtil;
import com.petroun.devourerhizine.config.GTConfig;
import com.petroun.devourerhizine.enums.EnumCardStatus;
import com.petroun.devourerhizine.enums.EnumTranStatus;
import com.petroun.devourerhizine.model.entity.OilCardUse;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import com.petroun.devourerhizine.provider.gt.GTGateWay;
import com.petroun.devourerhizine.service.Oil.CardService;
import com.petroun.devourerhizine.service.Oil.MobileCardService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        //980210236292877
        //gtGateWay.recharge("980210236296627",1000,gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        //gtGateWay.phoneRegister(gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        //gtGateWay.getUserToken("D5D50D2D9F472F68", "D5D50D2D9F472F6806354899",gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        //System.out.println(gtGateWay.getUserToken("10100000020", "06398031",gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword()));
        //gtGateWay.setPayPwd("10100000020","398031",gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
        System.out.println(gtGateWay.getQRCode("1245678901","www.baidu.com",15000,60*10,gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword()));
    }

    @Test
    public void t3(){

            List<OilCardUse> list = cardService.queryCardUseByStatus(EnumTranStatus.Trading.getCode());
            if(list != null){
                Date now = new Date();
                for(OilCardUse cardUse : list){
                    //没有有效时间表示二维码获取失败，直接解绑
                    if(cardUse.getValidityTime() == null){
                        OilCardUse update = new OilCardUse();
                        update.setId(cardUse.getId());
                        update.setStatus(EnumTranStatus.fail.getCode());
                        cardService.updateOilCardUse(update);
                        cardService.unbundlingNotInTrading(cardUse.getId());
                    }else{

                        OilCardUse query = gtGateWay.queryCardUserByRemote(cardUse.getId(), gtConfig.getCopartnerId(), gtConfig.getCopartnerPassword());
                        //超时
                        if(query.getStatus() != EnumTranStatus.success.getCode()
                                && now.compareTo(DateUtils.DateAddSed(cardUse.getValidityTime(),60)) > 0){
                            cardService.updateOilCardUseStatusAndunbundling(cardUse.getId(),EnumTranStatus.fail.getCode());

                        }
                    }

                }
            }

    }

}
