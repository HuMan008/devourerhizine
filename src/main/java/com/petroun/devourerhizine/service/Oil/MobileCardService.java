package com.petroun.devourerhizine.service.Oil;

import com.petroun.devourerhizine.model.entity.OilMobileCardDetail;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MobileCardService {

    OilMobileCardInfo getNewMobileCard();

    OilMobileCardInfo getNewMobileCard(String mobile);

    boolean insertMobileCard(OilMobileCardInfo oilMobileCardInfo);

    OilMobileCardInfo queryMobileCardByMobile(String mobile);


    @Transactional
    boolean insertOrUpdateMobileCardDetails(List<OilMobileCardDetail> details);

    /**
     * 充值后更新卡记录
     * @param cardNo
     * @param amount
     * @return
     */
    boolean recharge(String cardNo, int amount);

    /**
     * 根据卡号获取卡信息
     * @param cardNo
     * @return
     */
    OilMobileCardInfo getMobileCardInfoByCardNo(String cardNo);

    long queryCardsInfo(List<Byte> status);
}
