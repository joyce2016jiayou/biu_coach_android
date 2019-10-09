package com.noplugins.keepfit.coachplatform.bean;

import java.util.List;

public class ReturnTimeBean {

    /**
     * restTime : {"data":[{"end_date":"2019-09-24","beg_time":"14:29:50","end_time":"17:29:53","beg_date":"2019-09-24"}]}
     * workTime : {"data":{"restDate":null,"endTime":"22:32:49","begTime":"13:32:46"}}
     */

    private RestTimeBean restTime;
    private WorkTimeBean workTime;

    public RestTimeBean getRestTime() {
        return restTime;
    }

    public void setRestTime(RestTimeBean restTime) {
        this.restTime = restTime;
    }

    public WorkTimeBean getWorkTime() {
        return workTime;
    }

    public void setWorkTime(WorkTimeBean workTime) {
        this.workTime = workTime;
    }

    public static class RestTimeBean {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * end_date : 2019-09-24
             * beg_time : 14:29:50
             * end_time : 17:29:53
             * beg_date : 2019-09-24
             */

            private String endDate;
            private String begTime;
            private String endTime;

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
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

            public String getBegDate() {
                return begDate;
            }

            public void setBegDate(String begDate) {
                this.begDate = begDate;
            }

            private String begDate;


        }
    }

    public static class WorkTimeBean {
        /**
         * data : {"restDate":null,"endTime":"22:32:49","begTime":"13:32:46"}
         */

        private DataBeanX data;

        public DataBeanX getData() {
            return data;
        }

        public void setData(DataBeanX data) {
            this.data = data;
        }

        public static class DataBeanX {
            /**
             * restDate : null
             * endTime : 22:32:49
             * begTime : 13:32:46
             */

            private Object restDate;
            private String endTime;
            private String begTime;

            public Object getRestDate() {
                return restDate;
            }

            public void setRestDate(Object restDate) {
                this.restDate = restDate;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getBegTime() {
                return begTime;
            }

            public void setBegTime(String begTime) {
                this.begTime = begTime;
            }
        }
    }
}
