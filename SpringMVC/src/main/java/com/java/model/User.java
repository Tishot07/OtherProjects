package com.java.model;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Size;

@Component
public class User {

    @Size(min = 6, message = "Имя должно быть больше 6 символов")
    private String user;

    @Size(min = 5, max=10, message = "пароль должен быть от 5 до 10 симолов")
    private String pwd;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
