package com.noplugins.keepfit.coachplatform.bean;

public class MessageBean {

    /**
     * pkname : id
     * id : 1
     * messageNum : GYM222
     * coachUserNum : GEN23456
     * gymAreaNum : GYM19091216883274
     * messageCon : 测试消息
     * messageType : 1
     * readStatus : 0
     * status : null
     * messageTitle : 场馆申请
     * messageTime : 2019-09-27 16:56:01
     * createDate : 2019-09-27 16:56:01
     * updateDate : 2019-09-27 16:56:01
     * remark :
     * deleted : 0
     */

    private String pkname;
    private int id;
    private String messageNum;
    private String coachUserNum;
    private String gymAreaNum;
    private String messageCon;
    private int messageType;
    private int readStatus;
    private Object status;
    private String messageTitle;
    private String messageTime;
    private String createDate;
    private String updateDate;
    private String remark;
    private int deleted;
    private double finalwithdrawMoney;
    private double finalwithdrawBalance;

    public String getPkname() {
        return pkname;
    }

    public void setPkname(String pkname) {
        this.pkname = pkname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(String messageNum) {
        this.messageNum = messageNum;
    }

    public String getCoachUserNum() {
        return coachUserNum;
    }

    public void setCoachUserNum(String coachUserNum) {
        this.coachUserNum = coachUserNum;
    }

    public String getGymAreaNum() {
        return gymAreaNum;
    }

    public void setGymAreaNum(String gymAreaNum) {
        this.gymAreaNum = gymAreaNum;
    }

    public String getMessageCon() {
        return messageCon;
    }

    public void setMessageCon(String messageCon) {
        this.messageCon = messageCon;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}
