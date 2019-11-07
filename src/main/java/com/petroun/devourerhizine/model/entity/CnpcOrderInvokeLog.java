package com.petroun.devourerhizine.model.entity;

import java.util.Date;

public class CnpcOrderInvokeLog {
    private Integer id;

    private Long orderId;

    private Integer type;

    private String request;

    private String response;

    private Integer httpcode;

    private Date requestAt;

    private Date responseAt;

    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request == null ? null : request.trim();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response == null ? null : response.trim();
    }

    public Integer getHttpcode() {
        return httpcode;
    }

    public void setHttpcode(Integer httpcode) {
        this.httpcode = httpcode;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", type=").append(type);
        sb.append(", request=").append(request);
        sb.append(", response=").append(response);
        sb.append(", httpcode=").append(httpcode);
        sb.append(", requestAt=").append(requestAt);
        sb.append(", responseAt=").append(responseAt);
        sb.append(", createdAt=").append(createdAt);
        sb.append("]");
        return sb.toString();
    }
}