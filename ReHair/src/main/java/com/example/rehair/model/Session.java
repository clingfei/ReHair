package com.example.rehair.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class Session {
    private static final long serialVersionUID = 9120765714832970813L;
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }


}
