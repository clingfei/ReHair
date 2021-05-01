package com.example.rehair.model;

public class LoginData {
    Boolean flag;
    String errMsg;

    public LoginData(Boolean flag, String errMsg) {
        this.flag = flag;
        this.errMsg = errMsg;
    }

    public Boolean getFlag() {
        return this.flag;
    }

    public String getErrMsg() {
        return this.errMsg;
    }
}
