package com.noplugins.keepfit.coachplatform.bean;

public class SelectDateBean {
    public boolean isIs_check() {
        return is_check;
    }

    public void setIs_check(boolean is_check) {
        this.is_check = is_check;
    }

    private boolean is_check;
    private String week_str;

    public String getWeek_str() {
        return week_str;
    }

    public void setWeek_str(String week_str) {
        this.week_str = week_str;
    }

    private String year;
    private String month;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

}
