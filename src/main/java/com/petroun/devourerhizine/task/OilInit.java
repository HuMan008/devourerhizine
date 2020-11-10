package com.petroun.devourerhizine.task;

import com.petroun.devourerhizine.provider.gt.GTConfig;
import com.petroun.devourerhizine.enums.EnumCardStatus;
import com.petroun.devourerhizine.provider.gt.GTGateWay;
import com.petroun.devourerhizine.service.Oil.MobileCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class OilInit implements CommandLineRunner {

    @Autowired
    GTGateWay gtGateWay;

    @Autowired
    MobileCardService mobileCardService;

    @Autowired
    GTConfig gtConfig;

    private void phoneRegister(){
        ArrayList<Byte> list = new ArrayList<>();
        list.add(EnumCardStatus.Enable.getCode());
        list.add(EnumCardStatus.Useing.getCode());
        long count = mobileCardService.queryCardsInfo(list);
        if(count < 20){
            long add = 20 - count;
            for(long i = 0 ;  i < add ;i++ ){
                gtGateWay.phoneRegister(gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        phoneRegister();
    }
}
