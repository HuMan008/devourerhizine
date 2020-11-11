package com.petroun.devourerhizine.service.oil;

import com.petroun.devourerhizine.model.View.gt.ViewCardAndUse;
import com.petroun.devourerhizine.model.entity.OilCardUse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CardService {
    /**
     * 获取油卡并设置为使用中,并锁定
     * @return
     */
    @Transactional
    ViewCardAndUse getOilCard(String sendUrl,int sed,String mobile,int amount);

    /**
     * 更新创建时间，有效时间
     * @param oilCardUse
     * @param time
     * @param sed
     * @return
     */
    boolean updateCardUse(OilCardUse oilCardUse, String time, int sed,String strBRCode);

    OilCardUse queryById(String id);

    boolean updateOilCardUse(OilCardUse use);

    /**
     * 解绑使用中的卡
     * @param useId
     * @return
     */
    boolean unbundlingByUseing(String useId);

    /**
     * 解除卡绑定
     * @param useId
     * @return
     */
    boolean unbundlingNotInTrading(String useId);

    List<OilCardUse> queryCardUseByStatus(Byte status);

    boolean updateOilCardUseStatusAndunbundling(String useId, Byte status);

    boolean updateUse(OilCardUse use);
}
