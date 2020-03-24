package com.petroun.devourerhizine.web.message.request.sinopec;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author think <syj247@qq.com>、
 * @date 2020-3-24、16:55
 */
public class Promo {

    @NotNull
    @Min(10000)
    private Integer id;

    @NotNull
    @Min(0)
    private Integer promo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPromo() {
        return promo;
    }

    public void setPromo(Integer promo) {
        this.promo = promo;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Promo{");
        sb.append("id=").append(id);
        sb.append(", promo=").append(promo);
        sb.append('}');
        return sb.toString();
    }
}
