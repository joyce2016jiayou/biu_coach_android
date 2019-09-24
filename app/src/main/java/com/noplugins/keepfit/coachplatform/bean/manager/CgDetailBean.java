package com.noplugins.keepfit.coachplatform.bean.manager;

import java.util.List;

public class CgDetailBean {

    /**
     * picUrl : ["http://qnimg.ahcomg.com/chaoji1","http://qnimg.ahcomg.com/chaoji2","http://qnimg.ahcomg.com/chaoji3"]
     * areaDetail : {"edxtendVar05":"","tryout":7,"updateDate":"2019-09-12 10:45:32","edxtendVar02":"","edxtendVar01":"","edxtendVar04":"","latitude":31.155502,"companyName":"企业名称","edxtendVar03":"","picNum":"","remark":"","type":1,"cardNum":"232424242","areaName":"超级新新","legalPerson":"高动","haveSwim":1,"clickNum":61,"logo":"http://qnimg.ahcomg.com/chaoji1","id":124,"taxpayerNum":"","email":"232324@qq.com","longitude":121.347897,"createDate":"2019-09-12 10:45:32","area":300,"companyCode":"xxx223232","address":"上海闵行区七莘路3011号星钻城4楼","charge":0,"businessStart":"10:00:00","finalGradle":9.5,"pkname":"id","deleted":0,"phone":"021-62908785","grade":950,"areaNum":"GYM19091236750176","gymUserNum":"","businessEnd":"22:00:00","facility":"有氧,无氧,团操,游泳","collect":1,"status":1}
     * nowPrice : 20.0
     * teacherList : [{"lowestPrice":0.03,"teacherName":"张晗","grade":950,"skill":"1,2,3","teacherNum":"GEN56789","serviceDur":830,"logoUrl":"http://qnimg.ahcomg.com/jiaolian3"}]
     * courseList : [{"gymAreaNum":"GYM19091236750176","reason":"","courseType":1,"updateDate":"2019-09-12 13:27:27","comeNum":0,"finalPrice":0.03,"remark":"","type":"2","maxNum":20,"putaway":1,"tips":"预防受伤,根据调查有氧舞蹈较容易受伤的部位:依次为胫骨、脚、背、膝、踝、头,产生有氧舞蹈的受伤的主要原因是运动过度,脚扭伤。韧带拉伤等。","checkStatus":1,"price":3,"loop":false,"startTime":1568973600000,"id":9,"gymPlaceNum":"","createDate":"2019-09-12 13:27:27","teacherName":"钱伟","courseDes":"经验丰富的舞蹈老师 , 形体训练基础 , 中国古典舞基本功训练 , 学习古典舞新天地白珂舞蹈","applyNum":18,"target":1,"imgUrl":"http://qnimg.ahcomg.com/wudao1","difficulty":1,"courseName":"Dance Party","deleted":false,"courseNum":"GYM19091237142493","genTeacherNum":"GEN23456","checkInStatus":0,"suitPerson":"6岁以上","endTime":1568977200000,"classType":5,"checkOutStatus":0,"status":1}]
     */

    private AreaDetailBean areaDetail;
    private double nowPrice;
    private List<String> picUrl;
    private List<TeacherListBean> teacherList;
    private List<CourseListBean> courseList;

    public AreaDetailBean getAreaDetail() {
        return areaDetail;
    }

    public void setAreaDetail(AreaDetailBean areaDetail) {
        this.areaDetail = areaDetail;
    }

    public double getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(double nowPrice) {
        this.nowPrice = nowPrice;
    }

    public List<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<String> picUrl) {
        this.picUrl = picUrl;
    }

    public List<TeacherListBean> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherListBean> teacherList) {
        this.teacherList = teacherList;
    }

    public List<CourseListBean> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseListBean> courseList) {
        this.courseList = courseList;
    }

    public static class AreaDetailBean {
        /**
         * edxtendVar05 :
         * tryout : 7
         * updateDate : 2019-09-12 10:45:32
         * edxtendVar02 :
         * edxtendVar01 :
         * edxtendVar04 :
         * latitude : 31.155502
         * companyName : 企业名称
         * edxtendVar03 :
         * picNum :
         * remark :
         * type : 1
         * cardNum : 232424242
         * areaName : 超级新新
         * legalPerson : 高动
         * haveSwim : 1
         * clickNum : 61
         * logo : http://qnimg.ahcomg.com/chaoji1
         * id : 124
         * taxpayerNum :
         * email : 232324@qq.com
         * longitude : 121.347897
         * createDate : 2019-09-12 10:45:32
         * area : 300
         * companyCode : xxx223232
         * address : 上海闵行区七莘路3011号星钻城4楼
         * charge : 0
         * businessStart : 10:00:00
         * finalGradle : 9.5
         * pkname : id
         * deleted : 0
         * phone : 021-62908785
         * grade : 950
         * areaNum : GYM19091236750176
         * gymUserNum :
         * businessEnd : 22:00:00
         * facility : 有氧,无氧,团操,游泳
         * collect : 1
         * status : 1
         */

        private String edxtendVar05;
        private int tryout;
        private String updateDate;
        private String edxtendVar02;
        private String edxtendVar01;
        private String edxtendVar04;
        private double latitude;
        private String companyName;
        private String edxtendVar03;
        private String picNum;
        private String remark;
        private int type;
        private String cardNum;
        private String areaName;
        private String legalPerson;
        private int haveSwim;
        private int clickNum;
        private String logo;
        private int id;
        private String taxpayerNum;
        private String email;
        private double longitude;
        private String createDate;
        private int area;
        private String companyCode;
        private String address;
        private int charge;
        private String businessStart;
        private double finalGradle;
        private String pkname;
        private int deleted;
        private String phone;
        private int grade;
        private String areaNum;
        private String gymUserNum;
        private String businessEnd;
        private String facility;
        private int collect;
        private int status;

        public String getEdxtendVar05() {
            return edxtendVar05;
        }

        public void setEdxtendVar05(String edxtendVar05) {
            this.edxtendVar05 = edxtendVar05;
        }

        public int getTryout() {
            return tryout;
        }

        public void setTryout(int tryout) {
            this.tryout = tryout;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getEdxtendVar02() {
            return edxtendVar02;
        }

        public void setEdxtendVar02(String edxtendVar02) {
            this.edxtendVar02 = edxtendVar02;
        }

        public String getEdxtendVar01() {
            return edxtendVar01;
        }

        public void setEdxtendVar01(String edxtendVar01) {
            this.edxtendVar01 = edxtendVar01;
        }

        public String getEdxtendVar04() {
            return edxtendVar04;
        }

        public void setEdxtendVar04(String edxtendVar04) {
            this.edxtendVar04 = edxtendVar04;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getEdxtendVar03() {
            return edxtendVar03;
        }

        public void setEdxtendVar03(String edxtendVar03) {
            this.edxtendVar03 = edxtendVar03;
        }

        public String getPicNum() {
            return picNum;
        }

        public void setPicNum(String picNum) {
            this.picNum = picNum;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
        }

        public int getHaveSwim() {
            return haveSwim;
        }

        public void setHaveSwim(int haveSwim) {
            this.haveSwim = haveSwim;
        }

        public int getClickNum() {
            return clickNum;
        }

        public void setClickNum(int clickNum) {
            this.clickNum = clickNum;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTaxpayerNum() {
            return taxpayerNum;
        }

        public void setTaxpayerNum(String taxpayerNum) {
            this.taxpayerNum = taxpayerNum;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public String getBusinessStart() {
            return businessStart;
        }

        public void setBusinessStart(String businessStart) {
            this.businessStart = businessStart;
        }

        public double getFinalGradle() {
            return finalGradle;
        }

        public void setFinalGradle(double finalGradle) {
            this.finalGradle = finalGradle;
        }

        public String getPkname() {
            return pkname;
        }

        public void setPkname(String pkname) {
            this.pkname = pkname;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getAreaNum() {
            return areaNum;
        }

        public void setAreaNum(String areaNum) {
            this.areaNum = areaNum;
        }

        public String getGymUserNum() {
            return gymUserNum;
        }

        public void setGymUserNum(String gymUserNum) {
            this.gymUserNum = gymUserNum;
        }

        public String getBusinessEnd() {
            return businessEnd;
        }

        public void setBusinessEnd(String businessEnd) {
            this.businessEnd = businessEnd;
        }

        public String getFacility() {
            return facility;
        }

        public void setFacility(String facility) {
            this.facility = facility;
        }

        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class TeacherListBean {
        /**
         * lowestPrice : 0.03
         * teacherName : 张晗
         * grade : 950
         * skill : 1,2,3
         * teacherNum : GEN56789
         * serviceDur : 830
         * logoUrl : http://qnimg.ahcomg.com/jiaolian3
         */

        private double lowestPrice;
        private String teacherName;
        private int grade;
        private String skill;
        private String teacherNum;
        private int serviceDur;
        private String logoUrl;

        public double getLowestPrice() {
            return lowestPrice;
        }

        public void setLowestPrice(double lowestPrice) {
            this.lowestPrice = lowestPrice;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        public String getTeacherNum() {
            return teacherNum;
        }

        public void setTeacherNum(String teacherNum) {
            this.teacherNum = teacherNum;
        }

        public int getServiceDur() {
            return serviceDur;
        }

        public void setServiceDur(int serviceDur) {
            this.serviceDur = serviceDur;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }
    }

    public static class CourseListBean {
        /**
         * gymAreaNum : GYM19091236750176
         * reason :
         * courseType : 1
         * updateDate : 2019-09-12 13:27:27
         * comeNum : 0
         * finalPrice : 0.03
         * remark :
         * type : 2
         * maxNum : 20
         * putaway : 1
         * tips : 预防受伤,根据调查有氧舞蹈较容易受伤的部位:依次为胫骨、脚、背、膝、踝、头,产生有氧舞蹈的受伤的主要原因是运动过度,脚扭伤。韧带拉伤等。
         * checkStatus : 1
         * price : 3
         * loop : false
         * startTime : 1568973600000
         * id : 9
         * gymPlaceNum :
         * createDate : 2019-09-12 13:27:27
         * teacherName : 钱伟
         * courseDes : 经验丰富的舞蹈老师 , 形体训练基础 , 中国古典舞基本功训练 , 学习古典舞新天地白珂舞蹈
         * applyNum : 18
         * target : 1
         * imgUrl : http://qnimg.ahcomg.com/wudao1
         * difficulty : 1
         * courseName : Dance Party
         * deleted : false
         * courseNum : GYM19091237142493
         * genTeacherNum : GEN23456
         * checkInStatus : 0
         * suitPerson : 6岁以上
         * endTime : 1568977200000
         * classType : 5
         * checkOutStatus : 0
         * status : 1
         */

        private String gymAreaNum;
        private String reason;
        private int courseType;
        private String updateDate;
        private int comeNum;
        private double finalPrice;
        private String remark;
        private String type;
        private int maxNum;
        private int putaway;
        private String tips;
        private int checkStatus;
        private int price;
        private boolean loop;
        private long startTime;
        private int id;
        private String gymPlaceNum;
        private String createDate;
        private String teacherName;
        private String courseDes;
        private int applyNum;
        private int target;
        private String imgUrl;
        private int difficulty;
        private String courseName;
        private boolean deleted;
        private String courseNum;
        private String genTeacherNum;
        private int checkInStatus;
        private String suitPerson;
        private long endTime;
        private int classType;
        private int checkOutStatus;
        private int status;

        public String getGymAreaNum() {
            return gymAreaNum;
        }

        public void setGymAreaNum(String gymAreaNum) {
            this.gymAreaNum = gymAreaNum;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
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

        public int getComeNum() {
            return comeNum;
        }

        public void setComeNum(int comeNum) {
            this.comeNum = comeNum;
        }

        public double getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(double finalPrice) {
            this.finalPrice = finalPrice;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getMaxNum() {
            return maxNum;
        }

        public void setMaxNum(int maxNum) {
            this.maxNum = maxNum;
        }

        public int getPutaway() {
            return putaway;
        }

        public void setPutaway(int putaway) {
            this.putaway = putaway;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public int getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(int checkStatus) {
            this.checkStatus = checkStatus;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public boolean isLoop() {
            return loop;
        }

        public void setLoop(boolean loop) {
            this.loop = loop;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGymPlaceNum() {
            return gymPlaceNum;
        }

        public void setGymPlaceNum(String gymPlaceNum) {
            this.gymPlaceNum = gymPlaceNum;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getCourseDes() {
            return courseDes;
        }

        public void setCourseDes(String courseDes) {
            this.courseDes = courseDes;
        }

        public int getApplyNum() {
            return applyNum;
        }

        public void setApplyNum(int applyNum) {
            this.applyNum = applyNum;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public boolean isDeleted() {
            return deleted;
        }

        public void setDeleted(boolean deleted) {
            this.deleted = deleted;
        }

        public String getCourseNum() {
            return courseNum;
        }

        public void setCourseNum(String courseNum) {
            this.courseNum = courseNum;
        }

        public String getGenTeacherNum() {
            return genTeacherNum;
        }

        public void setGenTeacherNum(String genTeacherNum) {
            this.genTeacherNum = genTeacherNum;
        }

        public int getCheckInStatus() {
            return checkInStatus;
        }

        public void setCheckInStatus(int checkInStatus) {
            this.checkInStatus = checkInStatus;
        }

        public String getSuitPerson() {
            return suitPerson;
        }

        public void setSuitPerson(String suitPerson) {
            this.suitPerson = suitPerson;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getClassType() {
            return classType;
        }

        public void setClassType(int classType) {
            this.classType = classType;
        }

        public int getCheckOutStatus() {
            return checkOutStatus;
        }

        public void setCheckOutStatus(int checkOutStatus) {
            this.checkOutStatus = checkOutStatus;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
