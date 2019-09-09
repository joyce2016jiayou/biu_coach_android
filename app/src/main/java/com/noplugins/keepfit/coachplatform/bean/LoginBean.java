package com.noplugins.keepfit.coachplatform.bean;

public class LoginBean {

    /**
     * userNum : CUS19081342887917
     * right : 1
     * token : eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOiJDVVMxOTA4MTM0Mjg4NzkxNyIsInBlcmlvZCI6NjA0ODAwMDAwLCJsb2dpblRpbWUiOjE1NjU3NjM0MzQ0MjcsInJvbGVzIjpbIm9wZXJhdGlvbiJdLCJ0eXBlIjoiT1BFUkFUT1IifQ.M7q91_WNs0GeWpU6Axslb_dcrqCpTTTGclzgKn3-j19Iiit5LN6Tb6FeuWRXfSjuiUCx_XZRmmATcLvjFyt9qQ
     */

    private String userNum;
    private int right;
    private String token;
    private int havePassword;
    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getHavePassword() {
        return havePassword;
    }

    public void setHavePassword(int havePassword) {
        this.havePassword = havePassword;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
