package com.noplugins.keepfit.coachplatform.bean;

public class ChangguanBean {

    /**
     * gymAreaNum : GYM19091204791290
     * updateDate : 2019-09-25 19:28:32
     * address : 浦东新区沪南公路2888号号
     * distance : 14
     * bingdingDate : 2019-09-25 19:28:32
     * pkname : id
     * refuse : null
     * deleted : 0
     * phone : 021-62908785
     * areaName : 威尔土
     * gymBindingNum : null
     * genTeacherNum : GEN23456
     * name : null
     * logo : http://qnimg.ahcomg.com/weiertu1
     * id : 7
     * createDate : 2019-09-25 19:28:32
     * status : 3
     */

    private String gymAreaNum;
    private String updateDate;
    private String address;
    private int distance;
    private String bingdingDate;
    private String pkname;
    private Object refuse;
    private int deleted;
    private String phone;
    private String areaName;
    private String gymBindingNum;
    private String genTeacherNum;
    private Object name;
    private String logo;
    private int id;
    private String createDate;
    private int status;

    public AreaBean getArea() {
        return area;
    }

    public void setArea(AreaBean area) {
        this.area = area;
    }

    private AreaBean area;
    public static class AreaBean {
        /**
         * pkname : id
         * id : 124
         * areaNum : GYM19091236750176
         * areaName : 超级新新
         * address : 上海闵行区七莘路3011号星钻城4楼
         * phone : 021-62908785
         * email : 232324@qq.com
         * longitude : 121.347897
         * latitude : 31.155502
         * haveSwim : 1
         * facility : 1,2,3,5
         * grade : 950
         * clickNum : 106
         * area : 300
         * gymUserNum :
         * type : 1
         * picNum :
         * logo : http://qnimg.ahcomg.com/chaoji1
         * businessStart : 10:00:00
         * businessEnd : 22:00:00
         * taxpayerNum :
         * charge : 0
         * tryout : 7
         * status : 1
         * cardNum : 232424242
         * companyCode : xxx223232
         * companyName : 企业名称
         * legalPerson : 高动
         * remark :
         * deleted : 0
         * createDate : 2019-09-12 10:45:32
         * updateDate : 2019-09-12 10:45:32
         * cost : 10
         * edxtendVar01 :
         * edxtendVar02 :
         * edxtendVar03 :
         * edxtendVar04 :
         * edxtendVar05 :
         * finalGradle : 9.5
         */

        private String pkname;
        private int id;
        private String areaNum;
        private String areaName;
        private String address;
        private String phone;
        private String email;
        private double longitude;
        private double latitude;
        private int haveSwim;
        private String facility;
        private int grade;
        private int clickNum;
        private int area;
        private String gymUserNum;
        private int type;
        private String picNum;
        private String logo;
        private String businessStart;
        private String businessEnd;
        private String taxpayerNum;
        private int charge;
        private int tryout;
        private int status;
        private String cardNum;
        private String companyCode;
        private String companyName;
        private String legalPerson;
        private String remark;
        private int deleted;
        private String createDate;
        private String updateDate;
        private int cost;
        private String edxtendVar01;
        private String edxtendVar02;
        private String edxtendVar03;
        private String edxtendVar04;
        private String edxtendVar05;
        private double finalGradle;

        private String bankName;

        private String bankCardNum;

        private String coachNotify;

        private String province;
        private String city;
        private String district;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getCoachNotify() {
            return coachNotify;
        }

        public void setCoachNotify(String coachNotify) {
            this.coachNotify = coachNotify;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getBankCardNum() {
            return bankCardNum;
        }

        public void setBankCardNum(String bankCardNum) {
            this.bankCardNum = bankCardNum;
        }

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

        public String getAreaNum() {
            return areaNum;
        }

        public void setAreaNum(String areaNum) {
            this.areaNum = areaNum;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getHaveSwim() {
            return haveSwim;
        }

        public void setHaveSwim(int haveSwim) {
            this.haveSwim = haveSwim;
        }

        public String getFacility() {
            return facility;
        }

        public void setFacility(String facility) {
            this.facility = facility;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public int getClickNum() {
            return clickNum;
        }

        public void setClickNum(int clickNum) {
            this.clickNum = clickNum;
        }

        public int getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }

        public String getGymUserNum() {
            return gymUserNum;
        }

        public void setGymUserNum(String gymUserNum) {
            this.gymUserNum = gymUserNum;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPicNum() {
            return picNum;
        }

        public void setPicNum(String picNum) {
            this.picNum = picNum;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getBusinessStart() {
            return businessStart;
        }

        public void setBusinessStart(String businessStart) {
            this.businessStart = businessStart;
        }

        public String getBusinessEnd() {
            return businessEnd;
        }

        public void setBusinessEnd(String businessEnd) {
            this.businessEnd = businessEnd;
        }

        public String getTaxpayerNum() {
            return taxpayerNum;
        }

        public void setTaxpayerNum(String taxpayerNum) {
            this.taxpayerNum = taxpayerNum;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public int getTryout() {
            return tryout;
        }

        public void setTryout(int tryout) {
            this.tryout = tryout;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
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

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public String getEdxtendVar01() {
            return edxtendVar01;
        }

        public void setEdxtendVar01(String edxtendVar01) {
            this.edxtendVar01 = edxtendVar01;
        }

        public String getEdxtendVar02() {
            return edxtendVar02;
        }

        public void setEdxtendVar02(String edxtendVar02) {
            this.edxtendVar02 = edxtendVar02;
        }

        public String getEdxtendVar03() {
            return edxtendVar03;
        }

        public void setEdxtendVar03(String edxtendVar03) {
            this.edxtendVar03 = edxtendVar03;
        }

        public String getEdxtendVar04() {
            return edxtendVar04;
        }

        public void setEdxtendVar04(String edxtendVar04) {
            this.edxtendVar04 = edxtendVar04;
        }

        public String getEdxtendVar05() {
            return edxtendVar05;
        }

        public void setEdxtendVar05(String edxtendVar05) {
            this.edxtendVar05 = edxtendVar05;
        }

        public double getFinalGradle() {
            return finalGradle;
        }

        public void setFinalGradle(double finalGradle) {
            this.finalGradle = finalGradle;
        }
    }

    public String getGymAreaNum() {
        return gymAreaNum;
    }

    public void setGymAreaNum(String gymAreaNum) {
        this.gymAreaNum = gymAreaNum;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getBingdingDate() {
        return bingdingDate;
    }

    public void setBingdingDate(String bingdingDate) {
        this.bingdingDate = bingdingDate;
    }

    public String getPkname() {
        return pkname;
    }

    public void setPkname(String pkname) {
        this.pkname = pkname;
    }

    public Object getRefuse() {
        return refuse;
    }

    public void setRefuse(Object refuse) {
        this.refuse = refuse;
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

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getGymBindingNum() {
        return gymBindingNum;
    }

    public void setGymBindingNum(String gymBindingNum) {
        this.gymBindingNum = gymBindingNum;
    }

    public String getGenTeacherNum() {
        return genTeacherNum;
    }

    public void setGenTeacherNum(String genTeacherNum) {
        this.genTeacherNum = genTeacherNum;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
