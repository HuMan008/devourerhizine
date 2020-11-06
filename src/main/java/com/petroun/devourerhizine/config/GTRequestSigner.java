package com.petroun.devourerhizine.config;

import cn.gotoil.bill.tools.encoder.Hash;
import com.petroun.devourerhizine.model.RequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
        HashMap<String,String> parameters = request.getParameter();
        String payload;
        if(parameters != null && parameters.size() >= 1) {
            StringBuilder values = new StringBuilder();
            StringBuilder seq = new StringBuilder();
            for(Map.Entry entry : parameters.entrySet()){
                if(seq.length() == 0){
                    seq.append(entry.getValue());
                }else{
                    seq.append(";");
                    seq.append(entry.getValue());
                }
                values.append(parameters.get(entry.getKey()));
            }
            values.append(copartnerPassword);
            payload = values.toString();
            request.setSeq(seq.toString());
        } else {
            payload = copartnerPassword;
        }

        request.setMd52(Hash.md5(payload));
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
