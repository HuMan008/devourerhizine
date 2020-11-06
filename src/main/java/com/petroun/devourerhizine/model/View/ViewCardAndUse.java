package com.petroun.devourerhizine.model.View;

import com.petroun.devourerhizine.model.entity.OilCardUse;
import com.petroun.devourerhizine.model.entity.OilMobileCardInfo;
import lombok.Data;

@Data
public class ViewCardAndUse {
    private OilCardUse oilCardUse;
    private OilMobileCardInfo oilMobileCardInfo;
    private String type;
}
