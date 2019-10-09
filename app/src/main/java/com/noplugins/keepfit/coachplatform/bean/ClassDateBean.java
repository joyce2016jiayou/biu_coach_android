package com.noplugins.keepfit.coachplatform.bean;

import java.util.ArrayList;
import java.util.List;

public class ClassDateBean {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    private List<ScheduleBean.NoEndCourseBean> weijieshu_list = new ArrayList<>();

    public List<ScheduleBean.NoEndCourseBean> getWeijieshu_list() {
        return weijieshu_list;
    }

    public void setWeijieshu_list(List<ScheduleBean.NoEndCourseBean> weijieshu_list) {
        this.weijieshu_list = weijieshu_list;
    }

    public List<ScheduleBean.AlreadyEndCourseBean> getYijieshu_list() {
        return yijieshu_list;
    }

    public void setYijieshu_list(List<ScheduleBean.AlreadyEndCourseBean> yijieshu_list) {
        this.yijieshu_list = yijieshu_list;
    }

    private List<ScheduleBean.AlreadyEndCourseBean> yijieshu_list = new ArrayList<>();

}
