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

import com.google.common.base.Optional;

import java.util.Objects;

public class CnpcProviderInquireResult {

    private Long orderId;
    private String providerOrder;
    private String directorOrder;
    private String failureReason;
    private boolean needRetry = false;
    private boolean exist = false;
    private CnpcProviderLog providerLog = new CnpcProviderLog();
    private Optional<Boolean> result = Optional.absent();

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProviderOrder() {
        return providerOrder;
    }

    public void setProviderOrder(String providerOrder) {
        this.providerOrder = providerOrder;
    }

    public String getDirectorOrder() {
        return directorOrder;
    }

    public void setDirectorOrder(String directorOrder) {
        this.directorOrder = directorOrder;
    }

    public boolean isNeedRetry() {
        return needRetry;
    }

    public void setNeedRetry(boolean needRetry) {
        this.needRetry = needRetry;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public Optional<Boolean> getResult() {
        return result;
    }

    public void setResult(Optional<Boolean> result) {
        this.result = result;
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
        return "CnpcProviderInquireResult{" +
                "orderId=" + orderId +
                ", providerOrder='" + providerOrder + '\'' +
                ", directorOrder='" + directorOrder + '\'' +
                ", failureReason='" + failureReason + '\'' +
                ", needRetry=" + needRetry +
                ", exist=" + exist +
                ", providerLog=" + providerLog +
                ", result=" + result +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CnpcProviderInquireResult)) return false;
        CnpcProviderInquireResult that = (CnpcProviderInquireResult) o;
        return needRetry == that.needRetry &&
                exist == that.exist &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(providerOrder, that.providerOrder) &&
                Objects.equals(directorOrder, that.directorOrder) &&
                Objects.equals(failureReason, that.failureReason) &&
                Objects.equals(providerLog, that.providerLog) &&
                Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {

        return Objects.hash(orderId, providerOrder, directorOrder, failureReason, needRetry, exist, providerLog, result);
    }
}
