package com.petroun.devourerhizine.web.message.response.sinopec;


import java.util.Date;

public class Order {

    private Long id;
    private Long uid;
    private Long rhiflow;


    private String orderid;

    private Byte director;
    private String card;

    private Integer amount;

    private String mobile;

    private String extra;



    private Integer state;


    private Date date;

    private String failureReason;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getRhiflow() {
        return rhiflow;
    }

    public void setRhiflow(Long rhiflow) {
        this.rhiflow = rhiflow;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Byte getDirector() {
        return director;
    }

    public void setDirector(Byte director) {
        this.director = director;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
