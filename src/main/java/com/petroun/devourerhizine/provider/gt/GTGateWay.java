package com.petroun.devourerhizine.provider.gt;

import com.petroun.devourerhizine.classes.tools.EntityUtil;
import com.petroun.devourerhizine.classes.tools.XmlUtils;
import com.petroun.devourerhizine.config.GTConfig;
import com.petroun.devourerhizine.config.GTRequestSigner;
import com.petroun.devourerhizine.enums.EnumGtOil;
import com.petroun.devourerhizine.model.RequestEntity;
import com.petroun.devourerhizine.model.ResponseEntity;
import com.petroun.devourerhizine.model.entity.InvokeThirdLogWithBLOBs;
import com.petroun.devourerhizine.model.mapper.InvokeThirdLogMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GTGateWay {

    private final static Logger logger = LoggerFactory.getLogger(GTGateWay.class);

    @Autowired
    GTConfig gtConfig;

    @Autowired
    InvokeThirdLogMapper invokeThirdLogMapper;

    public String getUserToken(String cardNo,String pwd,String copartnerId,String copartnerPwd){
        RequestEntity requestEntity = new RequestEntity();
        requestEntity.setRequestId("GetCardInfoTokenEx");
        requestEntity.setMoney("0");
        requestEntity.setCopartnerId(copartnerId);
        requestEntity.setPassword(pwd);
        requestEntity.setCard(cardNo);
        GTRequestSigner.signedRequest(requestEntity,copartnerPwd);

        InvokeThirdLogWithBLOBs invokeThirdLogWithBLOBs = EntityUtil.createInvokeThirdLog(requestEntity.getRequestFlow(), EnumGtOil.UseToken.getCode(),requestEntity.getRequestId());

        try{
            ResponseEntity responseEntity = null;

            String ex = XmlUtils.toStr(requestEntity,false,true);
            logger.debug("请求request-->{}", ex);

            RequestBody requestBody = RequestBody.create(ex,MediaType.parse("application/xml; charset=utf-8"));
            Request request = new Request.Builder()
                      .url(gtConfig.getUrl())
                         .post(requestBody) //post请求
                        .build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            System.out.println(response.body().string());
            String token = "";
        }catch (Exception ex){
            logger.error("{}", ex);
            invokeThirdLogWithBLOBs.setResponse(ex.toString());
        }finally {
            invokeThirdLogMapper.insert(invokeThirdLogWithBLOBs);
        }
        return null;
    }
}
