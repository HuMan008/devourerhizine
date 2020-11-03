package com.petroun.devourerhizine.model.entity;

import java.util.Date;

public class OilCardUse {
    private Integer id;

    private String cardNo;

    private String mobile;

    private Short status;

    private Date validityTime;

    private String businessId;

    private String flowid;

    private Integer face;

    private Integer amount;

    private String merchant;

    private Integer balance;

    private Integer rise;

    private Integer riseAfter;

    private Integer oilPrice;

    private Date transactionTime;

    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(Date validityTime) {
        this.validityTime = validityTime;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public String getFlowid() {
        return flowid;
    }

    public void setFlowid(String flowid) {
        this.flowid = flowid == null ? null : flowid.trim();
    }

    public Integer getFace() {
        return face;
    }

    public void setFace(Integer face) {
        this.face = face;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant == null ? null : merchant.trim();
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getRise() {
        return rise;
    }

    public void setRise(Integer rise) {
        this.rise = rise;
    }

    public Integer getRiseAfter() {
        return riseAfter;
    }

    public void setRiseAfter(Integer riseAfter) {
        this.riseAfter = riseAfter;
    }

    public Integer getOilPrice() {
        return oilPrice;
    }

    public void setOilPrice(Integer oilPrice) {
        this.oilPrice = oilPrice;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
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
        sb.append(", cardNo=").append(cardNo);
        sb.append(", mobile=").append(mobile);
        sb.append(", status=").append(status);
        sb.append(", validityTime=").append(validityTime);
        sb.append(", businessId=").append(businessId);
        sb.append(", flowid=").append(flowid);
        sb.append(", face=").append(face);
        sb.append(", amount=").append(amount);
        sb.append(", merchant=").append(merchant);
        sb.append(", balance=").append(balance);
        sb.append(", rise=").append(rise);
        sb.append(", riseAfter=").append(riseAfter);
        sb.append(", oilPrice=").append(oilPrice);
        sb.append(", transactionTime=").append(transactionTime);
        sb.append(", createdAt=").append(createdAt);
        sb.append("]");
        return sb.toString();
    }
}