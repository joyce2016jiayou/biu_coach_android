package com.noplugins.keepfit.coachplatform.bean;

import java.io.Serializable;
import java.util.List;

public class YueKeBean {

    private List<CourseListBean> courseList;

    public List<CourseListBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseListBean> courseList) {
        this.courseList = courseList;
    }

    public static class CourseListBean implements Serializable {
        /**
         * courseType : 1
         * updateDate : 2019-09-20 11:01:42
         * custOrderItemNum : CUS19092068391395
         * custUserNum : CUS19090527313452
         * startTimeStamp : 1568973600000
         * coachUserNum : GEN23456
         * areaName : 超级新新
         * price : 3
         * endTimeStamp : 1568977200000
         * startTime : 2019-09-20 18:00:00
         * id : 406
         * beforeFace : 0
         * createDate : 2019-09-20 11:01:42
         * teacherTime : 0
         * address : 上海闵行区七莘路3011号星钻城4楼
         * afterFace : 0
         * checkInTime : 2019-09-20 17:07:33
         * teacherCourseType : 舞蹈
         * custOrderNum : CUS19092082728865
         * pkname : id
         * courseName : Dance Party
         * coachUserName : 钱伟
         * checkIn : 0
         * deleted : 0
         * checkOutTime : 2019-09-20 18:07:54
         * courseNum : GYM19091237142493
         * areaNum : GYM19091236750176
         * endTime : 2019-09-20 19:00:00
         * checkOut : 0
         * status : 1
         * courseTime : 18:00-19:00
         * userName :
         */

        private int courseType;
        private String updateDate;
        private String custOrderItemNum;
        private String custUserNum;
        private long startTimeStamp;
        private String coachUserNum;
        private String areaName;
        private int price;
        private long endTimeStamp;
        private String startTime;
        private int id;
        private int beforeFace;
        private String createDate;
        private int teacherTime;
        private String address;
        private int afterFace;
        private String checkInTime;
        private String teacherCourseType;
        private String custOrderNum;
        private String pkname;
        private String courseName;
        private String coachUserName;
        private int checkIn;
        private int deleted;
        private String checkOutTime;
        private String courseNum;
        private String areaNum;
        private String endTime;
        private int checkOut;
        private int status;
        private String courseTime;
        private String userName;

        public String getCourseStatus() {
            return courseStatus;
        }

        public void setCourseStatus(String courseStatus) {
            this.courseStatus = courseStatus;
        }

        private String courseStatus;

        private static final long serialVersionUID = 1L;
        private int week;
        private String name;
        private int start;
        private int step;
        private int class_status;//课程状态,已签到，已结束
        private String date_top;
        private int time_left;

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

        public int getCourseType() {
            return courseType;
        }

        public void setCourseType(int courseType) {
            this.courseType = courseType;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getCustOrderItemNum() {
            return custOrderItemNum;
        }

        public void setCustOrderItemNum(String custOrderItemNum) {
            this.custOrderItemNum = custOrderItemNum;
        }

        public String getCustUserNum() {
            return custUserNum;
        }

        public void setCustUserNum(String custUserNum) {
            this.custUserNum = custUserNum;
        }

        public long getStartTimeStamp() {
            return startTimeStamp;
        }

        public void setStartTimeStamp(long startTimeStamp) {
            this.startTimeStamp = startTimeStamp;
        }

        public String getCoachUserNum() {
            return coachUserNum;
        }

        public void setCoachUserNum(String coachUserNum) {
            this.coachUserNum = coachUserNum;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public long getEndTimeStamp() {
            return endTimeStamp;
        }

        public void setEndTimeStamp(long endTimeStamp) {
            this.endTimeStamp = endTimeStamp;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getBeforeFace() {
            return beforeFace;
        }

        public void setBeforeFace(int beforeFace) {
            this.beforeFace = beforeFace;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getTeacherTime() {
            return teacherTime;
        }

        public void setTeacherTime(int teacherTime) {
            this.teacherTime = teacherTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getAfterFace() {
            return afterFace;
        }

        public void setAfterFace(int afterFace) {
            this.afterFace = afterFace;
        }

        public String getCheckInTime() {
            return checkInTime;
        }

        public void setCheckInTime(String checkInTime) {
            this.checkInTime = checkInTime;
        }

        public String getTeacherCourseType() {
            return teacherCourseType;
        }

        public void setTeacherCourseType(String teacherCourseType) {
            this.teacherCourseType = teacherCourseType;
        }

        public String getCustOrderNum() {
            return custOrderNum;
        }

        public void setCustOrderNum(String custOrderNum) {
            this.custOrderNum = custOrderNum;
        }

        public String getPkname() {
            return pkname;
        }

        public void setPkname(String pkname) {
            this.pkname = pkname;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCoachUserName() {
            return coachUserName;
        }

        public void setCoachUserName(String coachUserName) {
            this.coachUserName = coachUserName;
        }

        public int getCheckIn() {
            return checkIn;
        }

        public void setCheckIn(int checkIn) {
            this.checkIn = checkIn;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public String getCheckOutTime() {
            return checkOutTime;
        }

        public void setCheckOutTime(String checkOutTime) {
            this.checkOutTime = checkOutTime;
        }

        public String getCourseNum() {
            return courseNum;
        }

        public void setCourseNum(String courseNum) {
            this.courseNum = courseNum;
        }

        public String getAreaNum() {
            return areaNum;
        }

        public void setAreaNum(String areaNum) {
            this.areaNum = areaNum;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getCheckOut() {
            return checkOut;
        }

        public void setCheckOut(int checkOut) {
            this.checkOut = checkOut;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCourseTime() {
            return courseTime;
        }

        public void setCourseTime(String courseTime) {
            this.courseTime = courseTime;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
