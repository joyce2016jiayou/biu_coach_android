package com.noplugins.keepfit.coachplatform.bean;

public class ClassDetailBean {


    /**
     * nickName : 来来来辣鸡把
     * finalPrice : 0.0
     * courseHome : 瑜伽
     * courseName : 专业单车训练
     * min : 1h/节
     * areaName : 超级新新
     * phone : 18380583083
     * person :
     * sportLog : 0
     * checkInStatus : 0
     * areaNum : GYM19091236750176
     * end : 1
     * time : 2019-09-18 17:00-18:00
     * classType :
     */

    private String nickName;
    private double finalPrice;
    private String courseHome;
    private String courseName;
    private String min;
    private String areaName;
    private String phone;
    private String person;
    private int sportLog;
    private String checkInStatus;
    private String areaNum;
    private int end;
    private String time;
    private String classType;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private String price;
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getCourseHome() {
        return courseHome;
    }

    public void setCourseHome(String courseHome) {
        this.courseHome = courseHome;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getSportLog() {
        return sportLog;
    }

    public void setSportLog(int sportLog) {
        this.sportLog = sportLog;
    }

    public String getCheckInStatus() {
        return checkInStatus;
    }

    public void setCheckInStatus(String checkInStatus) {
        this.checkInStatus = checkInStatus;
    }

    public String getAreaNum() {
        return areaNum;
    }

    public void setAreaNum(String areaNum) {
        this.areaNum = areaNum;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
}
