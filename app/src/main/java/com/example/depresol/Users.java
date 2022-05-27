package com.example.depresol;

public class Users {
    String mail,password,role;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Users(String mail, String password, String role) {
        this.mail = mail;
        this.password = password;
        this.role = role;
    }
}
