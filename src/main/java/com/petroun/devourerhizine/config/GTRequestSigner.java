package com.petroun.devourerhizine.config;

import cn.gotoil.bill.tools.encoder.Hash;
import com.petroun.devourerhizine.model.RequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by think on 2017/11/28.
 */
public class GTRequestSigner {

    private static Logger logger = LoggerFactory.getLogger(GTRequestSigner.class);
    private static ThreadLocal<SimpleDateFormat> threadLocalDateFormatter = new ThreadLocal() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        }
    };
    

    private static void signMD5(RequestEntity request , String copartnerPassword) {
        String payload = stringfly(request.getRequestId()) + stringfly(request.getRequestFlow()) + stringfly(request.getCopartnerId()) + stringfly(request.getCard()) + stringfly(request.getPassword()) + request.getMoney() + request.getTime() +copartnerPassword ;
        request.setMd5(Hash.md5(payload));
    }

    private static void signMD52(RequestEntity request, String copartnerPassword) {
        /*HashMap parameters = request.getParameters();
        String payload;
        if(parameters != null && parameters.size() >= 1) {
            StringBuilder stringBuilder = new StringBuilder();
            request.getEntity().getSeq().forEach((key) -> {
                stringBuilder.append((String)parameters.get(key));
            });
            stringBuilder.append(copartnerPassword);
            payload = stringBuilder.toString();
        } else {
            payload = copartnerPassword;
        }*/

        request.setMd52(Hash.md5(copartnerPassword));
    }

    private static String stringfly(String value) {
        return null == value?"":value;
    }


    public synchronized static void signedRequest(RequestEntity requestEntity, String copartnerPassword) {
        /*Request request = new Request(requestEntity);
        request.setTime(((SimpleDateFormat)threadLocalDateFormatter.get()).format(new Date()));
        request.setCopartnerId(copartnerId);*/
        requestEntity.setTime(((SimpleDateFormat)threadLocalDateFormatter.get()).format(new Date()));
        signMD5(requestEntity,copartnerPassword);
        signMD52(requestEntity,copartnerPassword);
    }
}
