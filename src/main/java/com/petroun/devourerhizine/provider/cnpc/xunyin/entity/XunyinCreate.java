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

public class XunyinCreate {

    private String cpid;

    private String code;

    private String orderid;

    private String order_no;

    private String msg;

    @Override
    public String toString() {
        return "XunyinCreate{" +
                "cpid='" + cpid + '\'' +
                ", code='" + code + '\'' +
                ", orderid='" + orderid + '\'' +
                ", order_no='" + order_no + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XunyinCreate)) return false;
        XunyinCreate that = (XunyinCreate) o;
        return Objects.equals(cpid, that.cpid) &&
                Objects.equals(code, that.code) &&
                Objects.equals(orderid, that.orderid) &&
                Objects.equals(order_no, that.order_no) &&
                Objects.equals(msg, that.msg);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cpid, code, orderid, order_no, msg);
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    @JsonProperty("Code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
