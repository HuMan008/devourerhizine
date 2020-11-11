package com.petroun.devourerhizine.service.oil;

public interface OilService {
    /**
     * 获取二维码
     * @param sendUrl
     * @param amount
     * @param sed
     * @param mobile
     * @return
     */
    String getQRCode(String sendUrl, int amount, int sed, String mobile);
}
