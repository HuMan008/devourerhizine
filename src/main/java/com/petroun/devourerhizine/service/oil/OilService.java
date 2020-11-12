package com.petroun.devourerhizine.service.oil;

import com.petroun.devourerhizine.model.entity.OilCardUse;

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

    /**
     * 查询交易结果
     * @param useId
     * @return
     */
    OilCardUse queryMobileCardTrans(String useId);
}
