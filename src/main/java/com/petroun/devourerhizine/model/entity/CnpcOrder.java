package com.petroun.devourerhizine.model.entity;

import java.util.Date;

public class CnpcOrder {




    private Long id;

    private Byte director;

    private String directorCard;

    private String directorOrder;

    private Long uid;

    private String mobile;

    private Integer fee;

    private String uflow;

    private String uextra;

    private Long rhiFlow;

    private Integer rhiThroughs;

    private Integer channel;

    private String channelOrder;

    private Integer channelThroughs;

    private Integer inquires;

    private Integer throughMask;

    private Integer regain;

    private Date regainAt;

    private Integer state;

    private String failureReason;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getDirector() {
        return director;
    }

    public void setDirector(Byte director) {
        this.director = director;
    }

    public String getDirectorCard() {
        return directorCard;
    }

    public void setDirectorCard(String directorCard) {
        this.directorCard = directorCard == null ? null : directorCard.trim();
    }

    public String getDirectorOrder() {
        return directorOrder;
    }

    public void setDirectorOrder(String directorOrder) {
        this.directorOrder = directorOrder == null ? null : directorOrder.trim();
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public String getUflow() {
        return uflow;
    }

    public void setUflow(String uflow) {
        this.uflow = uflow == null ? null : uflow.trim();
    }

    public String getUextra() {
        return uextra;
    }

    public void setUextra(String uextra) {
        this.uextra = uextra == null ? null : uextra.trim();
    }

    public Long getRhiFlow() {
        return rhiFlow;
    }

    public void setRhiFlow(Long rhiFlow) {
        this.rhiFlow = rhiFlow;
    }

    public Integer getRhiThroughs() {
        return rhiThroughs;
    }

    public void setRhiThroughs(Integer rhiThroughs) {
        this.rhiThroughs = rhiThroughs;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getChannelOrder() {
        return channelOrder;
    }

    public void setChannelOrder(String channelOrder) {
        this.channelOrder = channelOrder == null ? null : channelOrder.trim();
    }

    public Integer getChannelThroughs() {
        return channelThroughs;
    }

    public void setChannelThroughs(Integer channelThroughs) {
        this.channelThroughs = channelThroughs;
    }

    public Integer getInquires() {
        return inquires;
    }

    public void setInquires(Integer inquires) {
        this.inquires = inquires;
    }

    public Integer getThroughMask() {
        return throughMask;
    }

    public void setThroughMask(Integer throughMask) {
        this.throughMask = throughMask;
    }

    public Integer getRegain() {
        return regain;
    }

    public void setRegain(Integer regain) {
        this.regain = regain;
    }

    public Date getRegainAt() {
        return regainAt;
    }

    public void setRegainAt(Date regainAt) {
        this.regainAt = regainAt;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason == null ? null : failureReason.trim();
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
        sb.append(", id=").append(id);
        sb.append(", director=").append(director);
        sb.append(", directorCard=").append(directorCard);
        sb.append(", directorOrder=").append(directorOrder);
        sb.append(", uid=").append(uid);
        sb.append(", mobile=").append(mobile);
        sb.append(", fee=").append(fee);
        sb.append(", uflow=").append(uflow);
        sb.append(", uextra=").append(uextra);
        sb.append(", rhiFlow=").append(rhiFlow);
        sb.append(", rhiThroughs=").append(rhiThroughs);
        sb.append(", channel=").append(channel);
        sb.append(", channelOrder=").append(channelOrder);
        sb.append(", channelThroughs=").append(channelThroughs);
        sb.append(", inquires=").append(inquires);
        sb.append(", throughMask=").append(throughMask);
        sb.append(", regain=").append(regain);
        sb.append(", regainAt=").append(regainAt);
        sb.append(", state=").append(state);
        sb.append(", failureReason=").append(failureReason);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}