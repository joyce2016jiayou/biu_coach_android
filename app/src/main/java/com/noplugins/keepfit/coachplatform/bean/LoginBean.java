package com.noplugins.keepfit.coachplatform.bean;

public class LoginBean {



    private String userNum;
    private String token;
    private String teacherType;
    private int havePayPassword;

    public int getHavePayPassword() {
        return havePayPassword;
    }

    public void setHavePayPassword(int havePayPassword) {
        this.havePayPassword = havePayPassword;
    }

    public String getTeacherType() {
        return teacherType;
    }

    public void setTeacherType(String teacherType) {
        this.teacherType = teacherType;
    }




    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
