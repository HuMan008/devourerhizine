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

public class InquireResponse {

    private String coopOrderNo;
    private String coopId;
    private String orderNo;
    private String orderStatus;
    private Integer failedCode;
    private String failedReason;
    private String reasonCode;
    private String reasonMsg;

    public String getCoopOrderNo() {
        return coopOrderNo;
    }

    public void setCoopOrderNo(String coopOrderNo) {
        this.coopOrderNo = coopOrderNo;
    }

    public String getCoopId() {
        return coopId;
    }

    public void setCoopId(String coopId) {
        this.coopId = coopId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getReasonMsg() {
        return reasonMsg;
    }

    public void setReasonMsg(String reasonMsg) {
        this.reasonMsg = reasonMsg;
    }

    @Override
    public String toString() {
        return "InquireResponse{" +
                "coopOrderNo='" + coopOrderNo + '\'' +
                ", coopId='" + coopId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", failedCode=" + failedCode +
                ", failedReason='" + failedReason + '\'' +
                ", reasonCode='" + reasonCode + '\'' +
                ", reasonMsg='" + reasonMsg + '\'' +
                '}';
    }
}
