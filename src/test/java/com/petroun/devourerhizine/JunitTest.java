package com.petroun.devourerhizine;

import cn.gotoil.bill.provider.bill.BillClient;
import cn.gotoil.bill.provider.bill.BillFlow;
import cn.gotoil.bill.provider.bill.BillObject;
import cn.gotoil.bill.tools.ObjectHelper;
import cn.gotoil.bill.tools.date.DateUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petroun.devourerhizine.classes.BitMask;
import com.petroun.devourerhizine.classes.tools.EntityUtil;
import com.petroun.devourerhizine.classes.tools.MathUtils;
import com.petroun.devourerhizine.classes.tools.XmlUtils;
import com.petroun.devourerhizine.enums.EnumTranStatus;
import com.petroun.devourerhizine.model.ReqParameters;
import com.petroun.devourerhizine.provider.gt.RequestEntity;
import com.petroun.devourerhizine.provider.gt.ResponseEntity;
import com.petroun.devourerhizine.model.entity.CnpcOrder;
import com.petroun.devourerhizine.model.entity.OilCardUse;
import com.petroun.devourerhizine.provider.petroun.Rhizine;
import com.petroun.devourerhizine.web.message.request.sinopec.Promo;
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
            BillObject billObject = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false).readValue(x, BillObject.class);
            billFlow.setBillObject(billObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }catch (IOException io){
            io.printStackTrace();
        }

    }

    @Test
    public void t03() {
        System.out.println(BitMask.isSeted(0, 0));
    }

    @Test
    public void t01() {
        for (int i = 0; i < 10; i++) {
            System.out.println(i + "-->" + (i & 0x01) + "\t" + (i & 0x02));
        }
        System.out.println(0x00);
    }

    @Test
    public void t4(){
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("UserPhoneIsRegisterQuery");
        requestEntity.setRequestFlow("");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId("11");
        requestEntity.setPassword("");
        requestEntity.setCard("");
        requestEntity.setExtend("");
        requestEntity.setExtend2("");
        //TreeMap<String, String> parameter = new TreeMap<>();
        ///parameter.put("BuExtend1","12344555554");
        ///requestEntity.setParameter(parameter);
        ReqParameters reqParameters = new ReqParameters();
        reqParameters.add("xixix1111","xixixiaaa");
        reqParameters.add("xixix22222","xixixibbbb");
        reqParameters.add("xixix3333","xixixicccc");
        requestEntity.setReqParameters(reqParameters);


        String ex = XmlUtils.toStr(requestEntity,false,true);
        System.out.println(ex);
        //
        // GTRequestSigner.signedRequest(requestEntity,"1212");
    }

    @Test
    public void t5(){
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<response>\n" +
                "<response-id>UserBindCardQuery</response-id>\n" +
                "<request-flow></request-flow>\n" +
                "<response-flow></response-flow>\n" +
                "<copartner-id>60020021</copartner-id>\n" +
                "<card></card>\n" +
                "<money>0</money>\n" +
                "<balance>0</balance>\n" +
                "<taxbal>0</taxbal>\n" +
                "<parameters>\n" +
                "<parameter name=\"BusiExtend\" value=\"980620006271377~24~2020110600053690~6000~1~0~0~00003009~1~1~0~~~0~0~1~0~23~1~0~~0~2020-11-06 15:10:11~~0~0|980420006288380~22~2020110600053691~6000~1~0~0~00003007~1~1~0~~~0~0~1~0~23~1~0~~0~2020-11-06 15:10:11~~0~0|980210236292877~20~2020110600053689~6000~1~0~0~00003003~1~1~0~~~0~0~1~0~23~1~0~~0~2020-11-06 15:10:10~~0~0\"></parameter>\n" +
                "</parameters>\n" +
                "<code>0</code>\n" +
                "<description>查询成功</description>\n" +
                "<mac></mac>\n" +
                "<md5>c5e60cc992e21d3b955e88ac8c4d62cf</md5>\n" +
                "<time>2020.11.06 15:28:33</time>\n" +
                "<extend><![CDATA[]]></extend>\n" +
                "<extend2><![CDATA[]]></extend2>\n" +
                "<seq>BusiExtend</seq>\n" +
                "<md5-2>a7f76ed5d57fad97898e56958e535941</md5-2>\n" +
                "</response>";
        ResponseEntity e = XmlUtils.parseBean(xml, ResponseEntity.class);
        System.out.println(e);
        String busiExtend = EntityUtil.ReqParametersByKey(e.getReqParameters(),"BusiExtend");
        System.out.println(busiExtend);
        List<String[]> result = EntityUtil.formatResult(busiExtend);
        System.out.println(result);

    }

    @Test
    public void t6(){
        try {
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<response>\n" +
                    "<response-id>UserHistoryQueryFYALL</response-id>\n" +
                    "<request-flow></request-flow>\n" +
                    "<response-flow></response-flow>\n" +
                    "<copartner-id>60020021</copartner-id>\n" +
                    "<card></card>\n" +
                    "<money>0</money>\n" +
                    "<balance>0</balance>\n" +
                    "<taxbal>0</taxbal>\n" +
                    "<parameters>\n" +
                    "<parameter name=\"BusiExtend\" value=\"70323013~2020-11-10 11:18:23~113~92#汽油~100~中石油江北分公司~0~4733822~0.000000~14.599~0~100~70323013201110111807~~~0.00~\"></parameter>\n" +
                    "</parameters>\n" +
                    "<code>0</code>\n" +
                    "<description>查询成功</description>\n" +
                    "<mac></mac>\n" +
                    "<md5>8ead5355382c56432b3fc490a41945ae</md5>\n" +
                    "<time>2020.11.10 11:46:48</time>\n" +
                    "<extend><![CDATA[]]></extend>\n" +
                    "<extend2><![CDATA[]]></extend2>\n" +
                    "<seq>BusiExtend</seq>\n" +
                    "<md5-2>1931a2f08b3afe77cba8327dbd1bc169</md5-2>\n" +
                    "</response>\n";
            ResponseEntity responseEntity = XmlUtils.parseBean(xml, ResponseEntity.class);
            ;
            String str = EntityUtil.ReqParametersByKey(responseEntity.getReqParameters(), "BusiExtend");
            List<String[]> result = EntityUtil.formatResult(str);
            String[] resultDetail = result.get(0);
            OilCardUse updateOilCardUser = new OilCardUse();
            updateOilCardUser.setId("11");
            updateOilCardUser.setStatus(EnumTranStatus.Success.getCode());
            updateOilCardUser.setStation(resultDetail[0]);
            updateOilCardUser.setTransactionTime(DateUtils.simpleDatetimeFormatter().parse(resultDetail[1]));
            updateOilCardUser.setBusinessId(resultDetail[2]);
            updateOilCardUser.setBusinessName(resultDetail[3]);
            updateOilCardUser.setFace(Integer.valueOf(resultDetail[4]));
            updateOilCardUser.setMerchant(resultDetail[5]);
            updateOilCardUser.setBalance(Integer.valueOf(resultDetail[6]));
            updateOilCardUser.setFlowid(resultDetail[7]);
            updateOilCardUser.setRise(resultDetail[8]);
            updateOilCardUser.setRiseAfter(resultDetail[9]);
            updateOilCardUser.setAmount(Integer.valueOf(resultDetail[10]));
            updateOilCardUser.setOilPrice(Integer.valueOf(resultDetail[11]));
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Test
    public void t7(){
        String str = "0.01001";
        System.out.println(MathUtils.multiply100(str));
    }
}
