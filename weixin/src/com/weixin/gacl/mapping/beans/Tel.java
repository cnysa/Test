package com.weixin.gacl.mapping.beans;

public class Tel {
    private String telName;

    private String telNum1;

    private String telNum2;

    public String getTelName() {
        return telName;
    }

    public void setTelName(String telName) {
        this.telName = telName == null ? null : telName.trim();
    }

    public String getTelNum1() {
        return telNum1;
    }

    public void setTelNum1(String telNum1) {
        this.telNum1 = telNum1 == null ? null : telNum1.trim();
    }

    public String getTelNum2() {
        return telNum2;
    }

    public void setTelNum2(String telNum2) {
        this.telNum2 = telNum2 == null ? null : telNum2.trim();
    }
}