package com.petroun.devourerhizine;

import com.petroun.devourerhizine.config.GTConfig;
import com.petroun.devourerhizine.provider.gt.GTGateWay;
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

    @Test
    public void t1(){
        gtGateWay.phoneRegisterQuery(gtConfig.getCopartnerId(),gtConfig.getCopartnerPassword());
    }
}
