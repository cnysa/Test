package com.weixin.gacl.mapping.beans;

public class Vote {
    private String voteId;

    private String voteUserId;

    private String voteUserTime;

    private String voteUserNumber;

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId == null ? null : voteId.trim();
    }

    public String getVoteUserId() {
        return voteUserId;
    }

    public void setVoteUserId(String voteUserId) {
        this.voteUserId = voteUserId == null ? null : voteUserId.trim();
    }

    public String getVoteUserTime() {
        return voteUserTime;
    }

    public void setVoteUserTime(String voteUserTime) {
        this.voteUserTime = voteUserTime == null ? null : voteUserTime.trim();
    }

    public String getVoteUserNumber() {
        return voteUserNumber;
    }

    public void setVoteUserNumber(String voteUserNumber) {
        this.voteUserNumber = voteUserNumber == null ? null : voteUserNumber.trim();
    }
}