package com.petroun.devourerhizine.classes.tools;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;

public class MathUtils {
    public static Integer multiply100(String str){
        if(StringUtils.isEmpty(str) || "0".equals(str)){
            return 0;
        }
        BigDecimal b1 = new BigDecimal(str);
        b1 = b1.multiply(new BigDecimal(100));
        return b1.intValue();
    }
}
