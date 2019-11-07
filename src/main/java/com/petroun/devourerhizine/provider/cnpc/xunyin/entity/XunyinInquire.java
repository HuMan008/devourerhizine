/*
 * ******************************************************
 *  * Copyright (C) 2018 cluries  <cluries#me.com>
 *  *
 *  * This file is part of Devourer.
 *  *
 *  * Devourer can not be copied and/or distributed without the express
 *  * permission of cluries
 *  ******************************************************
 */

package com.petroun.devourerhizine.provider.cnpc.xunyin.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class XunyinInquire {
    private String orderid;
    private String merchantorderid;
    private String state;
    private String totalPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XunyinInquire)) return false;
        XunyinInquire inquire = (XunyinInquire) o;
        return Objects.equals(orderid, inquire.orderid) &&
                Objects.equals(merchantorderid, inquire.merchantorderid) &&
                Objects.equals(state, inquire.state) &&
                Objects.equals(totalPrice, inquire.totalPrice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderid, merchantorderid, state, totalPrice);
    }

    @Override
    public String toString() {
        return "XunyinInquire{" +
                "orderid='" + orderid + '\'' +
                ", merchantorderid='" + merchantorderid + '\'' +
                ", state='" + state + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                '}';
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getMerchantorderid() {
        return merchantorderid;
    }

    public void setMerchantorderid(String merchantorderid) {
        this.merchantorderid = merchantorderid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("total-price")
    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
