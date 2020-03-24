package com.petroun.devourerhizine.web.message.request.sinopec;

import cn.gotoil.bill.web.annotation.ThirdValidation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class Recharge implements Serializable {

    @NotNull
    @ThirdValidation(regex = "^1\\d{10}$")
    private String mobile;

    @NotNull
    @ThirdValidation(regex = "^(100011\\d{13})|(90\\d{14})$", message = "目标卡号验证错误")
    private String card;

    @NotNull
    @Max(100000)
    @Min(100)
    @ThirdValidation(regex = "\\d+0000$", message = "金额只能为100元的整倍数")
    private Integer amount;

    @NotNull
    @ThirdValidation(regex = "^\\w{1,64}$")
    private String orderid;

    @NotNull
    private Long uid;

    @NotNull
    private List<Promo> promos;

    @Size(max = 1024)
    private String extra;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Recharge{");
        sb.append("mobile='").append(mobile).append('\'');
        sb.append(", card='").append(card).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", orderid='").append(orderid).append('\'');
        sb.append(", uid=").append(uid);
        sb.append(", promos=").append(promos);
        sb.append(", extra='").append(extra).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public List<Promo> getPromos() {
        return promos;
    }

    public void setPromos(List<Promo> promos) {
        this.promos = promos;
    }



}

