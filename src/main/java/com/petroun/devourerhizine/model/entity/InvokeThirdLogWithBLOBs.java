package com.petroun.devourerhizine.model.entity;

public class InvokeThirdLogWithBLOBs extends InvokeThirdLog {
    private String request;

    private String response;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", request=").append(request);
        sb.append(", response=").append(response);
        sb.append("]");
        return sb.toString();
    }
}