package com.noplugins.keepfit.coachplatform.bean;

import java.util.List;

public class AddressBean {

    private List<Province> province;
    private List<City> city;
    private List<Area> area;


    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    public List<Area> getArea() {
        return area;
    }

    public void setArea(List<Area> area) {
        this.area = area;
    }

    public List<Province> getProvince() {
        return province;
    }

    public void setProvince(List<Province> province) {
        this.province = province;
    }

    public class Province{

        /**
         * prvncnm : 湖北省
         * prvnccd : 420000
         */

        private String prvncnm;
        private String prvnccd;
        private boolean isClicks;

        public boolean isClicks() {
            return isClicks;
        }

        public void setClicks(boolean clicks) {
            isClicks = clicks;
        }
        public String getPrvncnm() {
            return prvncnm;
        }

        public void setPrvncnm(String prvncnm) {
            this.prvncnm = prvncnm;
        }

        public String getPrvnccd() {
            return prvnccd;
        }

        public void setPrvnccd(String prvnccd) {
            this.prvnccd = prvnccd;
        }
    }

    public class City {

        /**
         * citycd : 140100
         * citynm : 太原市
         * prvnccd : 140000
         */

        private String citycd;
        private String citynm;
        private String prvnccd;
        private boolean isClicks;

        public boolean isClicks() {
            return isClicks;
        }

        public void setClicks(boolean clicks) {
            isClicks = clicks;
        }

        public String getCitycd() {
            return citycd;
        }

        public void setCitycd(String citycd) {
            this.citycd = citycd;
        }

        public String getCitynm() {
            return citynm;
        }

        public void setCitynm(String citynm) {
            this.citynm = citynm;
        }

        public String getPrvnccd() {
            return prvnccd;
        }

        public void setPrvnccd(String prvnccd) {
            this.prvnccd = prvnccd;
        }
    }

    public class Area {

        /**
         * citycd : 130400
         * distcd : 130402
         * distnm : 邯山区
         * prvnccd : 130000
         */

        private String citycd;
        private String distcd;
        private String distnm;
        private String prvnccd;
        private boolean isClicks;

        public boolean isClicks() {
            return isClicks;
        }

        public void setClicks(boolean clicks) {
            isClicks = clicks;
        }

        public String getCitycd() {
            return citycd;
        }

        public void setCitycd(String citycd) {
            this.citycd = citycd;
        }

        public String getDistcd() {
            return distcd;
        }

        public void setDistcd(String distcd) {
            this.distcd = distcd;
        }

        public String getDistnm() {
            return distnm;
        }

        public void setDistnm(String distnm) {
            this.distnm = distnm;
        }

        public String getPrvnccd() {
            return prvnccd;
        }

        public void setPrvnccd(String prvnccd) {
            this.prvnccd = prvnccd;
        }
    }
}
