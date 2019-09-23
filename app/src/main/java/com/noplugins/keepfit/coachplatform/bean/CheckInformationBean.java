package com.noplugins.keepfit.coachplatform.bean;

import java.util.List;

public class CheckInformationBean {

    /**
     * coachPicCard : {"cardFrontKey":"card1","cardBackKey":"card2"}
     * coachUser : {"teacherType":1,"realname":"李四","card":"21313141","sex":1,"phone":"15611111111","province":"安徽省","city":"合肥","university":"上海海事大学","professiondate":"2015-09","goodAtSkill":"1,2,3","skill":"1,2,3","userNum":"CUS19091292977313"}
     * coachPicTeachings : [{"qiniuKey":"key1"},{"qiniuKey":"key2"}]
     * coachPicCertificates : [{"certType":"1","certDate":"2019-09","certBackKey":"key1","certFrontKey":"key2","certName":"name1"},{"certType":"2","certDate":"2019-09","certBackKey":"key3","certFrontKey":"key4","certName":"name2"}]
     */

    private CoachPicCardBean coachPicCard;
    private CoachUserBean coachUser;
    private List<CoachPicTeachingsBean> coachPicTeachings;
    private List<CoachPicCertificatesBean> coachPicCertificates;

    public CoachPicCardBean getCoachPicCard() {
        return coachPicCard;
    }

    public void setCoachPicCard(CoachPicCardBean coachPicCard) {
        this.coachPicCard = coachPicCard;
    }

    public CoachUserBean getCoachUser() {
        return coachUser;
    }

    public void setCoachUser(CoachUserBean coachUser) {
        this.coachUser = coachUser;
    }

    public List<CoachPicTeachingsBean> getCoachPicTeachings() {
        return coachPicTeachings;
    }

    public void setCoachPicTeachings(List<CoachPicTeachingsBean> coachPicTeachings) {
        this.coachPicTeachings = coachPicTeachings;
    }

    public List<CoachPicCertificatesBean> getCoachPicCertificates() {
        return coachPicCertificates;
    }

    public void setCoachPicCertificates(List<CoachPicCertificatesBean> coachPicCertificates) {
        this.coachPicCertificates = coachPicCertificates;
    }

    public static class CoachPicCardBean {
        /**
         * cardFrontKey : card1
         * cardBackKey : card2
         */

        private String cardFrontKey;
        private String cardBackKey;

        public String getCardFrontKey() {
            return cardFrontKey;
        }

        public void setCardFrontKey(String cardFrontKey) {
            this.cardFrontKey = cardFrontKey;
        }

        public String getCardBackKey() {
            return cardBackKey;
        }

        public void setCardBackKey(String cardBackKey) {
            this.cardBackKey = cardBackKey;
        }
    }

    public static class CoachUserBean {
        /**
         * teacherType : 1
         * realname : 李四
         * card : 21313141
         * sex : 1
         * phone : 15611111111
         * province : 安徽省
         * city : 合肥
         * university : 上海海事大学
         * professiondate : 2015-09
         * goodAtSkill : 1,2,3
         * skill : 1,2,3
         * userNum : CUS19091292977313
         */

        private int teacherType;
        private String realname;
        private String card;
        private int sex;
        private String phone;
        private String province;
        private String city;
        private String university;
        private String professiondate;
        private String goodAtSkill;
        private String skill;
        private String userNum;

        public int getTeacherType() {
            return teacherType;
        }

        public void setTeacherType(int teacherType) {
            this.teacherType = teacherType;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

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

        public String getUniversity() {
            return university;
        }

        public void setUniversity(String university) {
            this.university = university;
        }

        public String getProfessiondate() {
            return professiondate;
        }

        public void setProfessiondate(String professiondate) {
            this.professiondate = professiondate;
        }

        public String getGoodAtSkill() {
            return goodAtSkill;
        }

        public void setGoodAtSkill(String goodAtSkill) {
            this.goodAtSkill = goodAtSkill;
        }

        public String getSkill() {
            return skill;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        public String getUserNum() {
            return userNum;
        }

        public void setUserNum(String userNum) {
            this.userNum = userNum;
        }
    }

    public static class CoachPicTeachingsBean {


        /**
         * qiniuKey : key1
         */

        private String qiniuKey;

        public String getQiniuKey() {
            return qiniuKey;
        }

        public void setQiniuKey(String qiniuKey) {
            this.qiniuKey = qiniuKey;
        }
    }

    public static class CoachPicCertificatesBean {
        /**
         * certType : 1
         * certDate : 2019-09
         * certBackKey : key1
         * certFrontKey : key2
         * certName : name1
         */

        private String certType;
        private String certDate;
        private String certBackKey;
        private String certFrontKey;
        private String certName;

        public String getCertType() {
            return certType;
        }

        public void setCertType(String certType) {
            this.certType = certType;
        }

        public String getCertDate() {
            return certDate;
        }

        public void setCertDate(String certDate) {
            this.certDate = certDate;
        }

        public String getCertBackKey() {
            return certBackKey;
        }

        public void setCertBackKey(String certBackKey) {
            this.certBackKey = certBackKey;
        }

        public String getCertFrontKey() {
            return certFrontKey;
        }

        public void setCertFrontKey(String certFrontKey) {
            this.certFrontKey = certFrontKey;
        }

        public String getCertName() {
            return certName;
        }

        public void setCertName(String certName) {
            this.certName = certName;
        }
    }
}
