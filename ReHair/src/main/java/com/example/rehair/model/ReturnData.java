package com.example.rehair.model;

public class ReturnData {
    boolean flag = true;
    String errorMsg;
    public ReturnData(boolean flag, String errorMsg) {
        this.flag = flag;
        this.errorMsg = errorMsg;
    }

    public ReturnData() {

    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public boolean getFlag() {
        return this.flag;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
