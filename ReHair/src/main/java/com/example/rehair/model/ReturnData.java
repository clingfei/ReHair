package com.example.rehair.model;

public class ReturnData {
    boolean flag;
    String errorMsg;
    public ReturnData(boolean flag, String errorMsg) {
        this.flag = flag;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public boolean getFlag() {
        return this.flag;
    }
}
