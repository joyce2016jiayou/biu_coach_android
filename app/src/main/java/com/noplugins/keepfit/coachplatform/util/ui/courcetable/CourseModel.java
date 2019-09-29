package com.noplugins.keepfit.coachplatform.util.ui.courcetable;

import java.io.Serializable;

/**
 * Created by HMY on 2016/7
 */
public class CourseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private int week;
    private String name;
    /**
     * 开始上课节次
     */
    private int start;
    /**
     * 共几节课
     */
    private int step;

    private int class_status;//课程状态,已签到，已结束

    private String date_top;
    private int time_left;

    public String getDate_top() {
        return date_top;
    }

    public void setDate_top(String date_top) {
        this.date_top = date_top;
    }

    public int getTime_left() {
        return time_left;
    }

    public void setTime_left(int time_left) {
        this.time_left = time_left;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getClass_status() {
        return class_status;
    }

    public void setClass_status(int class_status) {
        this.class_status = class_status;
    }

}
