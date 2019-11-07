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

package com.petroun.devourerhizine.provider.cnpc;

import java.util.Objects;

public class CnpcProviderResult {

    private Long orderId;
    private boolean submitSuccess = true;
    private boolean needRetry = false;
    private String providerOrder;
    private String failureReason;
    private CnpcProviderLog providerLog = new CnpcProviderLog();

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public boolean isSubmitSuccess() {
        return submitSuccess;
    }

    public void setSubmitSuccess(boolean submitSuccess) {
        this.submitSuccess = submitSuccess;
    }

    public boolean isNeedRetry() {
        return needRetry;
    }

    public void setNeedRetry(boolean needRetry) {
        this.needRetry = needRetry;
    }

    public String getProviderOrder() {
        return providerOrder;
    }

    public void setProviderOrder(String providerOrder) {
        this.providerOrder = providerOrder;
    }

    public CnpcProviderLog getProviderLog() {
        return providerLog;
    }

    public void setProviderLog(CnpcProviderLog providerLog) {
        this.providerLog = providerLog;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    @Override
    public String toString() {
        return "CnpcProviderResult{" +
                "orderId=" + orderId +
                ", submitSuccess=" + submitSuccess +
                ", needRetry=" + needRetry +
                ", providerOrder='" + providerOrder + '\'' +
                ", failureReason='" + failureReason + '\'' +
                ", providerLog=" + providerLog +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CnpcProviderResult)) return false;
        CnpcProviderResult result = (CnpcProviderResult) o;
        return submitSuccess == result.submitSuccess &&
                needRetry == result.needRetry &&
                Objects.equals(orderId, result.orderId) &&
                Objects.equals(providerOrder, result.providerOrder) &&
                Objects.equals(failureReason, result.failureReason) &&
                Objects.equals(providerLog, result.providerLog);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, submitSuccess, needRetry, providerOrder, failureReason, providerLog);
    }
}
