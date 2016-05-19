package com.weixin.gacl.mapping.beans;

public class Match {
    private String matchId;

    private String matchName;

    private String matchTodo;

    private String matchUrl;

    private String matchGroup;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId == null ? null : matchId.trim();
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName == null ? null : matchName.trim();
    }

    public String getMatchTodo() {
        return matchTodo;
    }

    public void setMatchTodo(String matchTodo) {
        this.matchTodo = matchTodo == null ? null : matchTodo.trim();
    }

    public String getMatchUrl() {
        return matchUrl;
    }

    public void setMatchUrl(String matchUrl) {
        this.matchUrl = matchUrl == null ? null : matchUrl.trim();
    }

    public String getMatchGroup() {
        return matchGroup;
    }

    public void setMatchGroup(String matchGroup) {
        this.matchGroup = matchGroup == null ? null : matchGroup.trim();
    }
}