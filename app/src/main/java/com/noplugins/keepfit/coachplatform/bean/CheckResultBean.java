package com.noplugins.keepfit.coachplatform.bean;

public class CheckResultBean {

    /**
     * teacherType : 1  //团课1  私教2  两者都有3
     * checkType : 2   //团课申请中 1   私教申请中 2   没有申请则为0
     */

    private int teacherType;
    private Object checkType;

    public int getTeacherType() {
        return teacherType;
    }

    public void setTeacherType(int teacherType) {
        this.teacherType = teacherType;
    }

    public Object getCheckType() {
        return checkType;
    }

    public void setCheckType(Object checkType) {
        this.checkType = checkType;
    }
}
