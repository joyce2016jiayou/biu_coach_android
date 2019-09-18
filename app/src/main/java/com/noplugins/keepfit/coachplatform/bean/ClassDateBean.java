package com.noplugins.keepfit.coachplatform.bean;

import java.util.ArrayList;
import java.util.List;

public class ClassDateBean {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    private List<String> weijieshu_list = new ArrayList<>();

    public List<String> getWeijieshu_list() {
        return weijieshu_list;
    }

    public void setWeijieshu_list(List<String> weijieshu_list) {
        this.weijieshu_list = weijieshu_list;
    }

    public List<String> getYijieshu_list() {
        return yijieshu_list;
    }

    public void setYijieshu_list(List<String> yijieshu_list) {
        this.yijieshu_list = yijieshu_list;
    }

    private List<String> yijieshu_list = new ArrayList<>();

}
