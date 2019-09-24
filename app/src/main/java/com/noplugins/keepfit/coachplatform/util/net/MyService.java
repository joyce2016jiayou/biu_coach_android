package com.noplugins.keepfit.coachplatform.util.net;


import com.noplugins.keepfit.coachplatform.bean.LoginBean;
import com.noplugins.keepfit.coachplatform.bean.YanZhengMaBean;
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerBean;
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeamBean;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import okhttp3.RequestBody;
import retrofit2.http.*;
import rx.Observable;

import java.util.List;
import java.util.Map;

/**
 * Created by limengtao on 2017/3/17.
 */

public interface MyService {
    /**
     * 获取验证码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("sendCode")
    Observable<Bean<String>> get_yanzhengma(@Body RequestBody json);
    /**
     * 密码登陆
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("passWordLogin")
    Observable<Bean<LoginBean>> password_login(@Body RequestBody json);
    /**
     * 验证验证码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("verifyCode")
    Observable<Bean<YanZhengMaBean>> yanzheng_yanzhengma(@Body RequestBody json);
    /**
     * 设置密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("setPassword")
    Observable<Bean<String>> set_password(@Body RequestBody json);
    /**
     * 提交信息
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("checkData")
    Observable<Bean<String>> submit_information(@Body RequestBody json);

    /**
     * 课程管理
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("courseManager")
    Observable<Bean<ManagerBean>> courseManager(@Body RequestBody json);

    /**
     * 课程管理
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("courseDetail")
    Observable<Bean<ManagerTeamBean>> courseDetail(@Body RequestBody json);

    /**
     * 上下架
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("putaway")
    Observable<Bean<String>> putaway(@Body RequestBody json);

}
