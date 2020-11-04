package com.petroun.devourerhizine.service;

import com.petroun.devourerhizine.model.View.ViewCardAndUse;
import com.petroun.devourerhizine.model.entity.OilCardInfo;
import com.petroun.devourerhizine.model.entity.OilCardUse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CardService {
    /**
     * 获取油卡并设置为使用中,并锁定
     * @return
     */
    @Transactional
    ViewCardAndUse getOilCard(String mobile);

    /**
     * 更新创建时间，有效时间
     * @param oilCardUse
     * @param time
     * @param sed
     * @return
     */
    boolean updateCardUse(OilCardUse oilCardUse, String time, int sed);

    OilCardUse queryById(String id);

    boolean updateOilCardUse(OilCardUse use);

    /**
     * 解除卡绑定
     * @param useId
     * @return
     */
    boolean unbundling(String useId);

    List<OilCardUse> queryCardUseByStatus(Byte status);

    boolean updateOilCardUseStatusAndunbundling(String useId, Byte status);

    List<OilCardInfo> getCardByStatus(Byte status);
}