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

package com.petroun.devourerhizine.provider.cnpc.xunyin.entity;

import java.util.Objects;

public class XunyinBalance  {
    private String code;
    private String name;
    private Double balance;
    private String state;
    private String cpid;

    @Override
    public String toString() {
        return "XunyinBalance{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", state='" + state + '\'' +
                ", cpid='" + cpid + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof XunyinBalance)) return false;
        XunyinBalance balance1 = (XunyinBalance) o;
        return Objects.equals(code, balance1.code) &&
                Objects.equals(name, balance1.name) &&
                Objects.equals(balance, balance1.balance) &&
                Objects.equals(state, balance1.state) &&
                Objects.equals(cpid, balance1.cpid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(code, name, balance, state, cpid);
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }
}
