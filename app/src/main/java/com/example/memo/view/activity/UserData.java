package com.example.memo.view.activity;

public class UserData {
    private String name;
    private String idToken;
    private String email;
    private String pw;
    private String country;

    public UserData() { }

    public UserData(String name, String idToken, String email, String pw, String country) {
        this.name = name;
        this.idToken = idToken;
        this.email = email;
        this.pw = pw;
        this.country = country;
    }

    public String getCountry() {
        return country;
    }


    public String getEmail() {
        return email;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getName() {
        return name;
    }

    public String getPw() {
        return pw;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
