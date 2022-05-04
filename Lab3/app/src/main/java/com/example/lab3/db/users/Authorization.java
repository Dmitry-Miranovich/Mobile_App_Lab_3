package com.example.lab3.db.users;

public class Authorization {
    private String password;
    private String hex;

    public Authorization(String password, String hex){
        this.password = password;
        this.hex = hex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    @Override
    public String toString() {
        return "Authorization{" +
                "password='" + password + '\'' +
                ", hex='" + hex + '\'' +
                '}';
    }
}
