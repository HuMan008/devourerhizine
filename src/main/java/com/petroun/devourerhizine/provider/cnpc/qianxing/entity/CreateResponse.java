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

package com.petroun.devourerhizine.provider.cnpc.qianxing.entity;


public class CreateResponse {

    private String coopOrderNo;
    private String qxOrderNo;
    private String orderStatus;
    private Integer failedCode;
    private String failedReason;

    public String getCoopOrderNo() {
        return coopOrderNo;
    }

    public void setCoopOrderNo(String coopOrderNo) {
        this.coopOrderNo = coopOrderNo;
    }

    public String getQxOrderNo() {
        return qxOrderNo;
    }

    public void setQxOrderNo(String qxOrderNo) {
        this.qxOrderNo = qxOrderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getFailedCode() {
        return failedCode;
    }

    public void setFailedCode(Integer failedCode) {
        this.failedCode = failedCode;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    @Override
    public String toString() {
        return "CreateResponse{" +
                "coopOrderNo='" + coopOrderNo + '\'' +
                ", qxOrderNo='" + qxOrderNo + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", failedCode=" + failedCode +
                ", failedReason='" + failedReason + '\'' +
                '}';
    }
}
