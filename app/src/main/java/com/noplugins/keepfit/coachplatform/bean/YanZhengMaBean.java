package com.noplugins.keepfit.coachplatform.bean;

public class YanZhengMaBean {

    /**
     * userNum : CUS19091728500044
     * teacherType :
     * havePassword : 0
     * logo : http://qnimg.ahcomg.com/AllDefaultLogo
     * firstLoad : 1
     * token : eyJhbGciOiJIUzUxMiJ9.eyJ1aWQiOiJDVVMxOTA5MTcyODUwMDA0NCIsInBlcmlvZCI6NjA0ODAwMDAwLCJsb2dpblRpbWUiOjE1Njg3MDY1OTkzMjgsInJvbGVzIjpbIm9wZXJhdGlvbiJdLCJ0eXBlIjoiT1BFUkFUT1IifQ.uAR5WcdxwMSdceiLp8E8OZpxaA2n3PqtQv7EvxJyXbaU7Ngn5xaaivc8QYjPf9MAtdZ9XidsjRwKRMnpOubtBQ
     */

    private String userNum;
    private String teacherType;
    private int havePassword;
    private String logo;
    private int firstLoad;
    private String token;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getTeacherType() {
        return teacherType;
    }

    public void setTeacherType(String teacherType) {
        this.teacherType = teacherType;
    }

    public int getHavePassword() {
        return havePassword;
    }

    public void setHavePassword(int havePassword) {
        this.havePassword = havePassword;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getFirstLoad() {
        return firstLoad;
    }

    public void setFirstLoad(int firstLoad) {
        this.firstLoad = firstLoad;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
