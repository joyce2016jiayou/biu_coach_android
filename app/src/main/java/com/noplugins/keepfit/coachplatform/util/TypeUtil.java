package com.noplugins.keepfit.coachplatform.util;

public class TypeUtil {

    public static String cgTypeToStr(int type){
        String name = "";
        switch (type){
            case 1:
                name = "综合会所";
                break;
            case 2:
                name = "工作室";
                break;
            case 3:
                name = "主题馆";
                break;
        }
        return name;
    }
}
