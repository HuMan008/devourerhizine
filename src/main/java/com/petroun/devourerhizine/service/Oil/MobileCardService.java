package com.petroun.devourerhizine.service.Oil;

import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;

public interface MobileCardService {

    OilMobileCardInfo getNewMobileCard();

    OilMobileCardInfo getNewMobileCard(String mobile);
}
