package com.noplugins.keepfit.coachplatform.util.net;


import com.noplugins.keepfit.coachplatform.bean.*;
import com.noplugins.keepfit.coachplatform.bean.manager.CgDetailBean;
import com.noplugins.keepfit.coachplatform.bean.manager.CgListBean;
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

    /**
     * 团课同意/拒绝
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("agreeCourse")
    Observable<Bean<String>> agreeCourse(@Body RequestBody json);

    /**
     * 场馆详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("bindingAreaListDetail")
    Observable<Bean<CgDetailBean>> bindingAreaListDetail(@Body RequestBody json);

    /**
     * 场馆列表
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("bindingAreaList")
    Observable<Bean<CgListBean>> bindingAreaList(@Body RequestBody json);

    /**
     * 我的钱包
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("myBalance")
    Observable<Bean<WalletBean>> myBalance(@Body RequestBody json);

    /**
     * 钱包明细
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("myBalanceList")
    Observable<Bean<BalanceListBean>> myBalanceList(@Body RequestBody json);

    /**
     * 钱包明细详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("myBalanceListDetail")
    Observable<Bean<BalanceListBean.ListBean>> myBalanceListDetail(@Body RequestBody json);

    /**
     * 新增私教课程
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("addTeacherCourse")
    Observable<Bean<String>> addTeacherCourse(@Body RequestBody json);

    /**
     * 绑定场馆
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("bindingArea")
    Observable<Bean<String>> bindingArea(@Body RequestBody json);

    /**
     * 修改私教课程
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("updateCourse")
    Observable<Bean<String>> updateCourse(@Body RequestBody json);

    /**
     * 私教课程管理
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findCourseDetail")
    Observable<Bean<ManagerBean.CourseListBean>> findCourseDetail(@Body RequestBody json);

    /**
     * 绑定银行卡
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("bindingCoachBank")
    Observable<Bean<String>> bindingCoachBank(@Body RequestBody json);

    /**
     * 产品反馈
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("feedBackData")
    Observable<Bean<String>> feedBackData(@Body RequestBody json);

    /**
     * 我的主页
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("coachUserHome")
    Observable<Bean<MineBean>> coachUserHome(@Body RequestBody json);

    /**
     * 个人资料主页
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("personalData")
    Observable<Bean<InformationBean>> personalData(@Body RequestBody json);

    /**
     * 更新
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("updateCoachHome")
    Observable<Bean<String>> updateCoachHome(@Body RequestBody json);


}
