package com.petroun.devourerhizine.model.entity;

import java.util.Date;

public class OilMobileCardDetail {
    private String cardNo;

    private String mobile;

    private Byte cardType;

    private String userUid;

    private String oilNumber;

    private Byte cardStatus;

    private Integer cardBalance;

    private Integer payBalance;

    private Date createdAt;

    private Date updatedAt;

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

    public Byte getCardType() {
        return cardType;
    }

    public void setCardType(Byte cardType) {
        this.cardType = cardType;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid == null ? null : userUid.trim();
    }

    public String getOilNumber() {
        return oilNumber;
    }

    public void setOilNumber(String oilNumber) {
        this.oilNumber = oilNumber == null ? null : oilNumber.trim();
    }

    public Byte getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(Byte cardStatus) {
        this.cardStatus = cardStatus;
    }

    public Integer getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(Integer cardBalance) {
        this.cardBalance = cardBalance;
    }

    public Integer getPayBalance() {
        return payBalance;
    }

    public void setPayBalance(Integer payBalance) {
        this.payBalance = payBalance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", cardNo=").append(cardNo);
        sb.append(", mobile=").append(mobile);
        sb.append(", cardType=").append(cardType);
        sb.append(", userUid=").append(userUid);
        sb.append(", oilNumber=").append(oilNumber);
        sb.append(", cardStatus=").append(cardStatus);
        sb.append(", cardBalance=").append(cardBalance);
        sb.append(", payBalance=").append(payBalance);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}