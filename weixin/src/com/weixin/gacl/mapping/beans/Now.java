package com.weixin.gacl.mapping.beans;

public class Now {
    private String nowId;

    private String nowType;

    private String nowName;

    private String nowTodo;

    private String nowStartTime;

    private String nowEndTime;

    private String nowNumbers;

    public String getNowId() {
        return nowId;
    }

    public void setNowId(String nowId) {
        this.nowId = nowId == null ? null : nowId.trim();
    }

    public String getNowType() {
        return nowType;
    }

    public void setNowType(String nowType) {
        this.nowType = nowType == null ? null : nowType.trim();
    }

    public String getNowName() {
        return nowName;
    }

    public void setNowName(String nowName) {
        this.nowName = nowName == null ? null : nowName.trim();
    }

    public String getNowTodo() {
        return nowTodo;
    }

    public void setNowTodo(String nowTodo) {
        this.nowTodo = nowTodo == null ? null : nowTodo.trim();
    }

    public String getNowStartTime() {
        return nowStartTime;
    }

    public void setNowStartTime(String nowStartTime) {
        this.nowStartTime = nowStartTime == null ? null : nowStartTime.trim();
    }

    public String getNowEndTime() {
        return nowEndTime;
    }

    public void setNowEndTime(String nowEndTime) {
        this.nowEndTime = nowEndTime == null ? null : nowEndTime.trim();
    }

    public String getNowNumbers() {
        return nowNumbers;
    }

    public void setNowNumbers(String nowNumbers) {
        this.nowNumbers = nowNumbers == null ? null : nowNumbers.trim();
    }
}