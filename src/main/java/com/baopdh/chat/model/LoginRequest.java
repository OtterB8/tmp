package com.baopdh.chat.model;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    String username;
    @NotBlank
    String password;

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
}
