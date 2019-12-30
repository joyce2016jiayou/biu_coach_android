package com.noplugins.keepfit.coachplatform.global;

import com.noplugins.keepfit.coachplatform.bean.AddPhotoBean;
import com.noplugins.keepfit.coachplatform.bean.CheckInformationBean;
import com.noplugins.keepfit.coachplatform.bean.SelectDateBean;

import java.util.ArrayList;
import java.util.List;

public class AppConstants {
    public static final String FIRST_OPEN = "first_open";
    public static final String FIRST_QUANYI = "first_quanyi";
    public static final String FIRST_PASSWPRD = "first_password";
    public static final String TOKEN = "token";
    public static final String PHONE = "phone";
    public static final String USER_NAME = "userNum";
    public static final String NAME = "userName";
    public static final String TEACHER_TYPE = "teacher_type";//判断是否审核过
    public static final String SELECT_TEACHER_TYPE = "select_teacher_type";//教练类型
    public static final String SELECT_TEACHER_NUMBER = "select_teacher_number";//教练编号

    public static final String IS_TX = "istixian";
    public static final String RIGHT = "right";
    public static final String LOGO = "logo";
    public static final String LOCATION = "location";
    public static final String LAT = "currentLat";//维度
    public static final String LON = "currentLon";//精度
    public static String IS_SET_ALIAS = "is_set_alias";
    public static int SELECT_ZHENGSHU_IMAGE_SIZE = 0;//上证书图片的数量
    public static int SELECT_SHOUKE_IMAGE_SIZE = 0;//上传授课图片的数量
    public static int SELECT_ZHENGSHU_IMAGE_SIZE_TWO = 0;//上证书图片的数量
    public static List<CheckInformationBean.CoachPicCertificatesBean> SELECT_PHOTO_NUM = new ArrayList<>();
    public static String UPDATE_SELECT_PHOTO="update_select_photo";
    public static List<SelectDateBean> selectDateBeans = new ArrayList<>();//加载当前日期后两周
    public static final String IS_SUBMIT_TUANKE="is_submit_tuanke";
    public static final String IS_SUBMIT_SIJIAO="is_submit_sijiao";
    public static int ADD_CLASS_SELECT_IMAGES_SIZE = 0;


    //团课同意
    public static String TEAM_YQ_AGREE = "team_yq_agree";
    //团课拒绝
    public static String TEAM_YQ_REFUSE = "team_yq_refuse";


}
