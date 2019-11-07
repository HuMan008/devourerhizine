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

public class BalanceResponse {

    private String coopId;
    private Double balance;
    private String requestStatus;
    private Integer failedCode;
    private String failedReason;

    public String getCoopId() {
        return coopId;
    }

    public void setCoopId(String coopId) {
        this.coopId = coopId;
    }

    @Override
    public String toString() {
        return "BalanceResponse{" +
                "coopId='" + coopId + '\'' +
                ", balance=" + balance +
                ", requestStatus='" + requestStatus + '\'' +
                ", failedCode=" + failedCode +
                ", failedReason='" + failedReason + '\'' +
                '}';
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
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
}
