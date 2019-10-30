package com.noplugins.keepfit.coachplatform.util.net;


import com.noplugins.keepfit.coachplatform.bean.LoginBean;
import com.noplugins.keepfit.coachplatform.bean.YanZhengMaBean;
import com.noplugins.keepfit.coachplatform.bean.YueKeBean;
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
     * 获取验证码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchTeacherStatus")
    Observable<Bean<TeacherStatusBean>> get_teacher_status(@Body RequestBody json);

    /**
     * 密码登陆
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("passWordLogin")
    Observable<Bean<LoginBean>> password_login(@Body RequestBody json);

    /**
     * 同意协议
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("teacherSign")
    Observable<Bean<Object>> agree_xieyi(@Body RequestBody json);

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
    Observable<Bean<SetPasswordBean>> set_password(@Body RequestBody json);

    /**
     * 提交信息
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("checkData")
    Observable<Bean<Object>> submit_information(@Body RequestBody json);

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
    Observable<Bean<Object>> putaway(@Body RequestBody json);

    /**
     * 团课同意/拒绝
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("agreeCourse")
    Observable<Bean<Object>> agreeCourse(@Body RequestBody json);

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
     * 约课信息
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("moreCourseData")
    Observable<Bean<YueKeBean>> yuekeInformation(@Body RequestBody json);


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
    Observable<Bean<Object>> addTeacherCourse(@Body RequestBody json);

    /**
     * 绑定场馆
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("bindingArea")
    Observable<Bean<Object>> bindingArea(@Body RequestBody json);

    /**
     * 修改私教课程
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("updateCourse")
    Observable<Bean<Object>> updateCourse(@Body RequestBody json);

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
    Observable<Bean<Object>> bindingCoachBank(@Body RequestBody json);

    /**
     * 产品反馈
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("feedBackData")
    Observable<Bean<Object>> feedBackData(@Body RequestBody json);

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
    Observable<Bean<Object>> updateCoachHome(@Body RequestBody json);

    /**
     * 设置授课时间
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("saveCocahTeacherWorkTime")
    Observable<Bean<String>> set_shouke_time(@Body RequestBody json);

    /**
     * 关闭授课时间
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("closedCocahTeacherWorkTime")
    Observable<Bean<String>> close_shouke_time(@Body RequestBody json);

    /**
     * 获取全部时间
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getCocahTeacherSetTime")
    Observable<Bean<ReturnTimeBean>> get_all_time(@Body RequestBody json);

    /**
     * 设置忙碌时间
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("saveCocahTeacherHaveRestTime")
    Observable<Bean<String>> save_time(@Body RequestBody json);

    /**
     * 忘记密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("forgetPassword")
    Observable<Bean<String>> forgetPassword(@Body RequestBody json);

    /**
     * 修改手机号
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("updatePhone")
    Observable<Bean<Object>> updatePhone(@Body RequestBody json);

    /**
     * 设置提现密码
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("settingPayPassword")
    Observable<Bean<Object>> settingPayPassword(@Body RequestBody json);

    /**
     * 教练类型管理
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("getCheckResult")
    Observable<Bean<CheckResultBean>> getCheckResult(@Body RequestBody json);

    /**
     * 消息列表
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("coachMessageList")
    Observable<Bean<MessageListBean>> coachMessageList(@Body RequestBody json);

    /**
     * 读消息
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("readMessage")
    Observable<Bean<String>> readMessage(@Body RequestBody json);

    /**
     * 消息总数
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("messageTotalCount")
    Observable<Bean<MaxMessageEntity>> messageTotalCount(@Body RequestBody json);

    /**
     * 消息总数
     * searchDict
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findSchedule")
    Observable<Bean<ScheduleBean>> get_shouye_date(@Body RequestBody json);

    /**
     * 获取运动日志详情
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("coachSportLog")
    Observable<Bean<GetDailryBean>> get_trail_detail(@Body RequestBody json);

    /**
     * 提交运动数据
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("updateCoachSportLog")
    Observable<Bean<String>> submit_tice(@Body RequestBody json);


    /**
     * 授权场馆
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("myBindingArea")
    Observable<Bean<List<ChangguanBean>>> myBindingArea(@Body RequestBody json);

    /**
     * 解除绑定授权场馆
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("deleteMyBindingArea")
    Observable<Bean<String>> deleteMyBindingArea(@Body RequestBody json);

    /**
     * 教练同意/拒绝绑定
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("agreeBindingArea")
    Observable<Bean<String>> agreeBindingArea(@Body RequestBody json);

    /**
     * 绑定银行卡列表
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("bankList")
    Observable<Bean<List<BankCardBean>>> bankList(@Body RequestBody json);


    /**
     * 教练同意/拒绝绑定
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("appointClassDetail")
    Observable<Bean<ClassDetailBean>> class_detail(@Body RequestBody json);

    /**
     * 提现
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("withdrawDeposit")
    Observable<Bean<String>> withdrawDeposit(@Body RequestBody json);

    /**
     * 数据字典
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("searchDict")
    Observable<Bean<List<DictionaryBean>>> searchDict(@Body RequestBody json);

}
