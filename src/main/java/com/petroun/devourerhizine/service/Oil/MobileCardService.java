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
    boolean insertMobileCardDetails(List<OilMobileCardDetail> details);
}
