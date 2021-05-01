package com.example.rehair.model;

public class RegisterData {
    Boolean flag;
    String errorMsg;


    public RegisterData(boolean b, String s) {
        this.flag = b;
        this.errorMsg = s;
    }
    public Boolean getFlag() {
        return this.flag;
    }
    public String getErrorMsg() {
        return this.errorMsg;
    }
}
