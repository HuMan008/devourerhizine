package com.petroun.devourerhizine;

import cn.gotoil.bill.tools.ObjectHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.petroun.devourerhizine.model.entity.CnpcOrder;
import com.petroun.devourerhizine.web.message.request.sinopec.Promo;
import org.json.JSONArray;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 单元测试
 *
 * @author think <syj247@qq.com>、
 * @date 2020-3-24、16:49
 */
public class JunitTest {
    @Test
    public void test1() {

        List<Promo> promoList = new ArrayList<>();

        Promo promo1 = new Promo();
        promo1.setId(1);
        promo1.setPromo(1);
        promoList.add(promo1);

        Promo promo2 = new Promo();
        promo2.setId(2);
        promo2.setPromo(2);
        promoList.add(promo2);

        CnpcOrder order = new CnpcOrder();
        order.setPromoList(ObjectHelper.jsonString(promoList));
        System.out.println(order.toString());

        try {
            List<Promo> promos =  ObjectHelper.getObjectMapper().readValue("",new TypeReference<List<Promo>>() { });
            System.out.println(promos.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
      ;

    }
}
