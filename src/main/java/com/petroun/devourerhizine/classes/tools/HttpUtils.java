package com.petroun.devourerhizine.classes.tools;

import com.petroun.devourerhizine.provider.gt.GTGateWay;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static Response okHttpPost(String url,String context){
        RequestBody requestBody = RequestBody.create(context, MediaType.parse("application/xml; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody) //post请求
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            return response;
        }catch (Exception e){
            logger.debug("",e);
        }
        return null;
    }
}
