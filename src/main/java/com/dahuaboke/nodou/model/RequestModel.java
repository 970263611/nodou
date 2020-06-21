package com.dahuaboke.nodou.model;

import java.io.Serializable;

public class RequestModel implements Serializable {

    private String username;
    private String password;
    private String nodeKey;
    private String nodeValue;
    private String version;
    private boolean autoRemove = true;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isAutoRemove() {
        return autoRemove;
    }

    public void setAutoRemove(boolean autoRemove) {
        this.autoRemove = autoRemove;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nodeKey='" + nodeKey + '\'' +
                ", nodeValue='" + nodeValue + '\'' +
                ", version='" + version + '\'' +
                ", autoRemove=" + autoRemove +
                '}';
    }
}
