package com.noplugins.keepfit.coachplatform.bean;

import java.util.List;

public class SelectTimeBean {

    /**
     * teacherNum : Gen123
     * restTime : [{"restDate":"2019-09-01","begTime":"09:09:01","endTime":"21:21:01"},{"restDate":"2019-10-01","begTime":"10:09:01","endTime":"23:21:01"}]
     */

    private String teacherNum;
    private List<RestTimeBean> restTime;

    public String getTeacherNum() {
        return teacherNum;
    }

    public void setTeacherNum(String teacherNum) {
        this.teacherNum = teacherNum;
    }

    public List<RestTimeBean> getRestTime() {
        return restTime;
    }

    public void setRestTime(List<RestTimeBean> restTime) {
        this.restTime = restTime;
    }

    public static class RestTimeBean {
        /**
         * restDate : 2019-09-01
         * begTime : 09:09:01
         * endTime : 21:21:01
         */

        private String restDate;
        private String begTime;
        private String endTime;

        public String getRestDate() {
            return restDate;
        }

        public void setRestDate(String restDate) {
            this.restDate = restDate;
        }

        public String getBegTime() {
            return begTime;
        }

        public void setBegTime(String begTime) {
            this.begTime = begTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }


}
