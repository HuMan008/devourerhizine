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

import java.util.Date;
import java.util.Objects;

public class CnpcProviderLog {

    private String request;
    private String response;
    private Date requestAt;
    private Date responseAt;
    private Integer httpCode;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getRequestAt() {
        return requestAt;
    }

    public void setRequestAt(Date requestAt) {
        this.requestAt = requestAt;
    }

    public Date getResponseAt() {
        return responseAt;
    }

    public void setResponseAt(Date responseAt) {
        this.responseAt = responseAt;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CnpcProviderLog)) return false;
        CnpcProviderLog that = (CnpcProviderLog) o;
        return Objects.equals(request, that.request) &&
                Objects.equals(response, that.response) &&
                Objects.equals(requestAt, that.requestAt) &&
                Objects.equals(responseAt, that.responseAt) &&
                Objects.equals(httpCode, that.httpCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(request, response, requestAt, responseAt, httpCode);
    }

    @Override
    public String toString() {
        return "CnpcProviderLog{" +
                "request='" + request + '\'' +
                ", response='" + response + '\'' +
                ", requestAt=" + requestAt +
                ", responseAt=" + responseAt +
                ", httpCode=" + httpCode +
                '}';
    }
}
