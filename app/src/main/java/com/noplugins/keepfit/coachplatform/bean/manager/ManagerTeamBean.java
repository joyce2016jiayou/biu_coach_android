package com.noplugins.keepfit.coachplatform.bean.manager;

import java.util.List;

public class ManagerTeamBean {
    private ManagerBean.CourseListBean courseList;

    public ManagerBean.CourseListBean getCourseList() {
        return courseList;
    }

    public void setCourseList(ManagerBean.CourseListBean courseList) {
        this.courseList = courseList;
    }

    private List<String> pic;

    public List<String> getPic() {
        return pic;
    }

    public void setPic(List<String> pic) {
        this.pic = pic;
    }
}
