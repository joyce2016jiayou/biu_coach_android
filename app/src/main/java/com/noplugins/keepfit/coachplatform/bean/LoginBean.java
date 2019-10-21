package com.noplugins.keepfit.coachplatform.bean;

public class LoginBean {



    private String userNum;
    private String token;
    private String teacherType;
    private int havePayPassWord;

    public int getHavePayPassWord() {
        return havePayPassWord;
    }

    public void setHavePayPassWord(int havePayPassWord) {
        this.havePayPassWord = havePayPassWord;
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
