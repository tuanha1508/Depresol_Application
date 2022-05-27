package com.example.depresol;

public class Users {
    String name,url_anh,mail,password,role;

    public String getMail() {
        return mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl_anh() {
        return url_anh;
    }

    public void setUrl_anh(String url_anh) {
        this.url_anh = url_anh;
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

    public Users(String mail, String password, String role,String name, String url_anh) {
        this.mail = mail;
        this.password = password;
        this.role = role;
        this.name = name;
        this.url_anh = url_anh;
    }
}
