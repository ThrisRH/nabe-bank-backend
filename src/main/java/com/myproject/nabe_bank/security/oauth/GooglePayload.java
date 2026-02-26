package com.myproject.nabe_bank.security.oauth;

public class GooglePayload {
    private String sub;
    private String email;
    private String name;
    private String picture;
    private Boolean emailVerified;

    public GooglePayload(String sub, String email, String name, String picture, Boolean emailVerified) {
        this.sub = sub;
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.emailVerified = emailVerified;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
