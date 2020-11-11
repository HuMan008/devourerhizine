package com.petroun.devourerhizine.model.entity;

import java.util.Date;

public class OilCardUse {
    private String id;

    private String cardNo;

    private String cardMobile;

    private String mobile;

    private String qrcode;

    private Integer qrcodeAmount;

    private Byte status;

    private String sendUrl;

    private Integer sendCount;

    private Byte sendStatus;

    private Integer validitySed;

    private Date validityTime;

    private String businessId;

    private String businessName;

    private String station;

    private String stationName;

    private String flowid;

    private Integer face;

    private Integer amount;

    private String merchant;

    private Integer balance;

    private String rise;

    private String riseAfter;

    private Integer oilPrice;

    private Date transactionTime;

    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    public String getCardMobile() {
        return cardMobile;
    }

    public void setCardMobile(String cardMobile) {
        this.cardMobile = cardMobile == null ? null : cardMobile.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode == null ? null : qrcode.trim();
    }

    public Integer getQrcodeAmount() {
        return qrcodeAmount;
    }

    public void setQrcodeAmount(Integer qrcodeAmount) {
        this.qrcodeAmount = qrcodeAmount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getSendUrl() {
        return sendUrl;
    }

    public void setSendUrl(String sendUrl) {
        this.sendUrl = sendUrl == null ? null : sendUrl.trim();
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Byte getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Byte sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getValiditySed() {
        return validitySed;
    }

    public void setValiditySed(Integer validitySed) {
        this.validitySed = validitySed;
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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station == null ? null : station.trim();
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
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

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise == null ? null : rise.trim();
    }

    public String getRiseAfter() {
        return riseAfter;
    }

    public void setRiseAfter(String riseAfter) {
        this.riseAfter = riseAfter == null ? null : riseAfter.trim();
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
        sb.append(", cardMobile=").append(cardMobile);
        sb.append(", mobile=").append(mobile);
        sb.append(", qrcode=").append(qrcode);
        sb.append(", qrcodeAmount=").append(qrcodeAmount);
        sb.append(", status=").append(status);
        sb.append(", sendUrl=").append(sendUrl);
        sb.append(", sendCount=").append(sendCount);
        sb.append(", sendStatus=").append(sendStatus);
        sb.append(", validitySed=").append(validitySed);
        sb.append(", validityTime=").append(validityTime);
        sb.append(", businessId=").append(businessId);
        sb.append(", businessName=").append(businessName);
        sb.append(", station=").append(station);
        sb.append(", stationName=").append(stationName);
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