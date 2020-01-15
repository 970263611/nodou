package com.dahuaboke.nodou.model;

public class ResultMsg {

    private boolean state;
    private String msg;
    private Object obj;

    public ResultMsg(boolean state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public ResultMsg(boolean state, Object obj) {
        this.state = state;
        this.obj = obj;
    }

    public ResultMsg(boolean state, String msg, Object obj) {
        this.state = state;
        this.msg = msg;
        this.obj = obj;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
