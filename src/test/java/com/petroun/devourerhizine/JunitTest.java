package com.petroun.devourerhizine;

import cn.gotoil.bill.provider.bill.BillClient;
import cn.gotoil.bill.provider.bill.BillFlow;
import cn.gotoil.bill.provider.bill.BillObject;
import cn.gotoil.bill.tools.ObjectHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.x.protobuf.Mysqlx;
import com.petroun.devourerhizine.model.OptionKeys;
import com.petroun.devourerhizine.model.entity.CnpcOrder;
import com.petroun.devourerhizine.provider.petroun.Rhizine;
import com.petroun.devourerhizine.web.message.request.sinopec.Promo;
import okhttp3.OkHttpClient;
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


    @Test
   public void test2(){
       Rhizine.configure("aaa","a","b");
//       Rhizine.billClient();
        BillClient billClient =  new BillClient();;

        System.out.println(billClient);

        String x = "{\n" + "    \"status\": 0,\n" + "    \"message\": null,\n" + "    \"data\": {\n" + "        " +
                "\"id\": 1585725896467000133,\n" + "        \"uid\": 1577264207545000135,\n" + "        \"flowid\": \"68724065161969664\",\n" + "        \"nodrib\": 0,\n" + "        \"face\": -10000,\n" + "        \"amount\": -8500,\n" + "        \"amounthen\": 0,\n" + "        \"drib\": -1500,\n" + "        \"dribthen\": 8500,\n" + "        \"title\": \"中石油昆仑卡充值\",\n" + "        \"stitle\": null,\n" + "        \"extra\": \"\",\n" + "        \"operator\": 0,\n" + "        \"ccode\": \"3309\",\n" + "        \"state\": 8192,\n" + "        \"gradle\": null,\n" + "        \"createdAt\": \"2020-04-01 15:24:56\",\n" + "        \"user\": {\n" + "            \"id\": 1577264207545000135,\n" + "            \"mobile\": \"13436161008\",\n" + "            \"nick\": \"未命名\",\n" + "            \"passset\": null,\n" + "            \"avatar\": \"http://bole.dbbank.xyz/images/guest2.png\"\n" + "        },\n" + "        \"business\": {\n" + "            \"id\": 40000,\n" + "            \"cate\": 4,\n" + "            \"title\": \"中石油个人卡充值\"\n" + "        },\n" + "        \"point\": {\n" + "            \"type\": 1,\n" + "            \"name\": \"油滴App\",\n" + "            \"address\": \"油滴App\",\n" + "            \"tel\": \"023-62899460\",\n" + "            \"lng\": null,\n" + "            \"lat\": null\n" + "        },\n" + "        \"promos\": [],\n" + "        \"price\": null\n" + "    },\n" + "    \"profile\": {\n" + "        \"execTimeMillis\": 65\n" + "    }\n" + "}";
        BillFlow billFlow = new BillFlow();
        try {
            BillObject billObject =
                    new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false).readValue(x,
                            BillObject.class);
            billFlow.setBillObject(billObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }catch (IOException io){
            io.printStackTrace();
        }

    }
}
