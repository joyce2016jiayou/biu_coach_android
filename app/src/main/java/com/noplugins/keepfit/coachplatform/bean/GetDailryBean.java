package com.noplugins.keepfit.coachplatform.bean;

import java.util.List;

public class GetDailryBean {

    /**
     * courseName : Dance Party
     * biuTime : 18:00-19:00
     * checkIn : 17:07入场
     * afterFace : 2
     * areaName : 超级新新
     * lableList : [{"a":"a","name":"瘦身塑形","id":132,"value":"1","properties":"教练技能","object":"3"},{"a":"a","name":"增重增肌","id":133,"value":"2","properties":"教练技能","object":"3"},{"a":"a","name":"体能训练","id":134,"value":"3","properties":"教练技能","object":"3"},{"a":"a","name":"康复训练","id":135,"value":"4","properties":"教练技能","object":"3"},{"a":"a","name":"体态调整","id":136,"value":"5","properties":"教练技能","object":"3"}]
     * userName : 来来来辣鸡把
     * checkOut : 18:07离场
     * beforeFace : 1
     */

    private String courseName;
    private String biuTime;
    private String checkIn;
    private int afterFace;
    private String areaName;
    private String userName;
    private String checkOut;
    private int beforeFace;
    private List<LableListBean> lableList;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getBiuTime() {
        return biuTime;
    }

    public void setBiuTime(String biuTime) {
        this.biuTime = biuTime;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public int getAfterFace() {
        return afterFace;
    }

    public void setAfterFace(int afterFace) {
        this.afterFace = afterFace;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public int getBeforeFace() {
        return beforeFace;
    }

    public void setBeforeFace(int beforeFace) {
        this.beforeFace = beforeFace;
    }

    public List<LableListBean> getLableList() {
        return lableList;
    }

    public void setLableList(List<LableListBean> lableList) {
        this.lableList = lableList;
    }

    public static class LableListBean {
        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        /**
         * a : a
         * name : 瘦身塑形
         * id : 132
         * value : 1
         * properties : 教练技能
         * object : 3
         */
        private boolean check;
        private String a;
        private String name;
        private int id;
        private String value;
        private String properties;
        private String object;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getProperties() {
            return properties;
        }

        public void setProperties(String properties) {
            this.properties = properties;
        }

        public String getObject() {
            return object;
        }

        public void setObject(String object) {
            this.object = object;
        }
    }
}
