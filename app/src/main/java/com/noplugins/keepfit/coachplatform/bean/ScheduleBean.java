package com.noplugins.keepfit.coachplatform.bean;

import java.util.List;

public class ScheduleBean {


    private List<NoEndCourseBean> noEndCourse;
    private List<AlreadyEndCourseBean> alreadyEndCourse;

    public List<NoEndCourseBean> getNoEndCourse() {
        return noEndCourse;
    }

    public void setNoEndCourse(List<NoEndCourseBean> noEndCourse) {
        this.noEndCourse = noEndCourse;
    }

    public List<AlreadyEndCourseBean> getAlreadyEndCourse() {
        return alreadyEndCourse;
    }

    public void setAlreadyEndCourse(List<AlreadyEndCourseBean> alreadyEndCourse) {
        this.alreadyEndCourse = alreadyEndCourse;
    }

    public static class NoEndCourseBean {
        /**
         * courseType : 2
         * updateDate : 2019-09-20 12:20:48
         * userPhone : 15618081181
         * custOrderItemNum : CUS19092070272172
         * custUserNum : CUS19081922275624
         * startTimeStamp : 1570606800000
         * coachUserNum : CUS19092970322059
         * areaName : 舒服堡
         * price : 5
         * endTimeStamp : 1570610400000
         * startTime : 2019-10-09 15:40:00
         * id : 410
         * beforeFace : 0
         * createDate : 2019-09-20 12:20:48
         * address : 上海市杨浦区舒兰路50号2楼
         * afterFace : 0
         * teacherCourseType : 老年人课程
         * userName :
         * custOrderNum : CUS19092031241217
         * pkname : id
         * courseName : Dance Party
         * coachUserName : 钱伟
         * checkIn : 0
         * deleted : 0
         * courseNum : GYM190912828619573
         * areaNum : GYM19091216883274
         * endTime : 2019-10-09 16:40:00
         * checkOut : 0
         * status : 1
         * courseTime : 15:40-16:40
         */

        private int courseType;
        private String updateDate;
        private String userPhone;
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
        private String address;
        private int afterFace;
        private String teacherCourseType;
        private String userName;
        private String custOrderNum;
        private String pkname;
        private String courseName;
        private String coachUserName;
        private int checkIn;
        private int deleted;
        private String courseNum;
        private String areaNum;
        private String endTime;
        private int checkOut;
        private int status;
        private String courseTime;
        private String startStatus;
        private String personNum;

        public int getTeacherCheckIn() {
            return teacherCheckIn;
        }

        public void setTeacherCheckIn(int teacherCheckIn) {
            this.teacherCheckIn = teacherCheckIn;
        }

        private int teacherCheckIn;
        public String getStartStatus() {
            return startStatus;
        }

        public void setStartStatus(String startStatus) {
            this.startStatus = startStatus;
        }



        public String getPersonNum() {
            return personNum;
        }

        public void setPersonNum(String personNum) {
            this.personNum = personNum;
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

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
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

        public String getTeacherCourseType() {
            return teacherCourseType;
        }

        public void setTeacherCourseType(String teacherCourseType) {
            this.teacherCourseType = teacherCourseType;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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
    }

    public static class AlreadyEndCourseBean {
        /**
         * courseType : 2
         * updateDate : 2019-09-20 12:20:48
         * userPhone : 15618081181
         * custOrderItemNum : CUS19092070272171
         * custUserNum : CUS19081922275624
         * startTimeStamp : 1570586400000
         * coachUserNum : CUS19092970322059
         * areaName : 舒服堡
         * price : 5
         * endTimeStamp : 1570590000000
         * startTime : 2019-10-09 10:00:00
         * id : 409
         * beforeFace : 0
         * createDate : 2019-09-20 12:20:48
         * address : 上海市杨浦区舒兰路50号2楼
         * afterFace : 0
         * teacherCourseType : 老年人课程
         * userName :
         * custOrderNum : CUS19092031241217
         * pkname : id
         * courseName : Dance Party
         * coachUserName : 钱伟
         * checkIn : 0
         * deleted : 0
         * courseNum : GYM190912828619572
         * areaNum : GYM19091216883274
         * endTime : 2019-10-09 11:00:00
         * checkOut : 0
         * status : 1
         * courseTime : 10:00-11:00
         */

        private int courseType;
        private String updateDate;
        private String userPhone;
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
        private String address;
        private int afterFace;
        private String teacherCourseType;
        private String userName;
        private String custOrderNum;
        private String pkname;
        private String courseName;
        private String coachUserName;
        private int checkIn;
        private int deleted;
        private String courseNum;
        private String areaNum;
        private String endTime;
        private int checkOut;
        private int status;
        private String courseTime;
        private int sportLog;
        private int teacherCheckIn;

        public int getTeacherCheckIn() {
            return teacherCheckIn;
        }

        public void setTeacherCheckIn(int teacherCheckIn) {
            this.teacherCheckIn = teacherCheckIn;
        }

        public String getStartStatus() {
            return startStatus;
        }

        public void setStartStatus(String startStatus) {
            this.startStatus = startStatus;
        }

        private String startStatus;
        public int getSportLog() {
            return sportLog;
        }

        public void setSportLog(int sportLog) {
            this.sportLog = sportLog;
        }

        public String getPersonNum() {
            return personNum;
        }

        public void setPersonNum(String personNum) {
            this.personNum = personNum;
        }

        private String personNum;

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

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
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

        public String getTeacherCourseType() {
            return teacherCourseType;
        }

        public void setTeacherCourseType(String teacherCourseType) {
            this.teacherCourseType = teacherCourseType;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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
    }
}
