package com.petroun.devourerhizine.model.View;

import com.petroun.devourerhizine.model.entity.OilCardInfo;
import com.petroun.devourerhizine.model.entity.OilCardUse;
import lombok.Data;

@Data
public class ViewCardAndUse {
    private OilCardUse oilCardUse;
    private OilCardInfo oilCardInfo;
    private String type;
}
