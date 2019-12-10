package com.noplugins.keepfit.coachplatform.util.net;


import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.noplugins.keepfit.coachplatform.bean.*;
import com.noplugins.keepfit.coachplatform.bean.manager.*;
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeamBean;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.interceptor.LogInterceptor;
import com.orhanobut.logger.Logger;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by shiyujia02 on 2017/8/16.
 */
public class Network {
    public static final int DEFAULT_TIMEOUT = 20;
    private static Network mInstance;
    public MyService service;
    public ChangGuanService changGuanService;
    public UserService userService;

    public static String token = "";
    private static String MRTHOD_NAME = "";
    Gson gson;
    Retrofit retrofit;
    Retrofit get_tag_retrofit;
    Retrofit get_user_retrofit;

    OkHttpClient client;

    public String get_coach_url(String str) {
        if (str.equals("test")) {
            return "http://testapi.noplugins.com/api/coach-service/coachuser/";
        } else {
            return "http://kft.ahcomg.com/api/coach-service/coachuser/";
        }
    }

    public String get_changguang_url(String str) {
        if (str.equals("test")) {
            return "http://testapi.noplugins.com/api/gym-service/";
        } else {
            return "http://kft.ahcomg.com/api/gym-service/";
        }
    }

    public String user_url(String str) {
        if (str.equals("test")) {
            return "http://testapi.noplugins.com/api/cust-service/custuser/";
        } else {
            return "http://kft.ahcomg.com/api/cust-service/custuser/";
        }
    }

    //获取单例
    public static Network getInstance(String method, Context context) {
        MRTHOD_NAME = method;
        if (context != null) {
            if ("".equals(SpUtils.getString(context, AppConstants.TOKEN))) {
                token = "";
                Logger.e(method + "没有添加token");
            } else {
                token = SpUtils.getString(context, AppConstants.TOKEN);
                Logger.e(method + "添加的token:" + token);
            }
            if (mInstance == null) {
                synchronized (Network.class) {
                    mInstance = new Network(method, context);
                }
            }
        }
        return mInstance;
    }


    private Network(String method, Context context) {
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};

        // Install the all-trusting trust manager
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory())//去掉okhttp https证书验证
                .addInterceptor(new LogInterceptor(method))//添加日志拦截器
                .addInterceptor(new Interceptor() {//添加token
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("token", token)
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)//超时处理
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(get_coach_url("test"))//设置请求网址根部
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        get_tag_retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(get_changguang_url("test"))//设置请求网址根部
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        get_user_retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(user_url("test"))//设置请求网址根部
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(MyService.class);

        changGuanService = get_tag_retrofit.create(ChangGuanService.class);
        userService = get_user_retrofit.create(UserService.class);

    }

    public Subscription update_version(Map<String, Object> params, Subscriber<Bean<VersionEntity>> subscriber) {
        return service.update_version(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private RequestBody retuen_json_params(Map<String, Object> params) {
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        Logger.e(MRTHOD_NAME + "->请求参数打印：->" + json_params);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return requestBody;
    }

    private RequestBody retuen_json_object(Object params) {
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        String json = new Gson().toJson(params);//要传递的json
        Logger.e(MRTHOD_NAME + "->请求参数打印：->" + json_params);
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
        return requestBody;
    }

    /**
     * 获取省
     *
     * @param subscriber
     * @return
     */
    public Subscription get_province(Map<String, Object> params, Subscriber<Bean<CityCode>> subscriber) {
        return userService.get_province(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取市
     *
     * @param subscriber
     * @return
     */
    public Subscription get_city(Map<String, Object> params, Subscriber<Bean<GetCityCode>> subscriber) {
        return userService.get_city(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 获取区
     *
     * @param subscriber
     * @return
     */
    public Subscription get_qu(Map<String, Object> params, Subscriber<Bean<GetQuCode>> subscriber) {
        return userService.get_qu(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription get_yanzhengma(Map<String, Object> params, Subscriber<Bean<String>> subscriber) {
        return service.get_yanzhengma(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription get_teacher_status(Map<String, Object> params, Subscriber<Bean<TeacherStatusBean>> subscriber) {
        return service.get_teacher_status(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription get_zidian(Map<String, Object> params, Subscriber<Bean<List<ZiDIanBean>>> subscriber) {
        return changGuanService.get_zidian(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription password_login(Map<String, Object> params, Subscriber<Bean<LoginBean>> subscriber) {
        return service.password_login(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription agree_xieyi(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.agree_xieyi(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription yanzheng_yanzhengma(Map<String, Object> params, Subscriber<Bean<YanZhengMaBean>> subscriber) {
        return service.yanzheng_yanzhengma(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription set_password(Map<String, Object> params, Subscriber<Bean<SetPasswordBean>> subscriber) {
        return service.set_password(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription submit_information(CheckInformationBean checkInformationBean, Subscriber<Bean<Object>> subscriber) {
        return service.submit_information(retuen_json_object(checkInformationBean))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription courseManager(Map<String, Object> params, Subscriber<Bean<ManagerBean>> subscriber) {
        return service.courseManager(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription courseDetail(Map<String, Object> params, Subscriber<Bean<ManagerTeamBean>> subscriber) {
        return service.courseDetail(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public Subscription putaway(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.putaway(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription get_biaoqians(Map<String, Object> params, Subscriber<Bean<List<TagBean>>> subscriber) {
        return changGuanService.get_biaoqians(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription get_qiniu_token(Map<String, Object> params, Subscriber<Bean<QiNiuToken>> subscriber) {
        return changGuanService.get_qiniu_token(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription agreeCourse(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.agreeCourse(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription bindingAreaListDetail(Map<String, Object> params, Subscriber<Bean<CgDetailBean>> subscriber) {
        return service.bindingAreaListDetail(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription bindingAreaList(Map<String, Object> params, Subscriber<Bean<CgListBean>> subscriber) {
        return service.bindingAreaList(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription yuekeInformation(Map<String, Object> params, Subscriber<Bean<YueKeBean>> subscriber) {
        return service.yuekeInformation(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription myBalance(Map<String, Object> params, Subscriber<Bean<WalletBean>> subscriber) {
        return service.myBalance(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription myBalanceList(Map<String, Object> params, Subscriber<Bean<BalanceListBean>> subscriber) {
        return service.myBalanceList(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription myBalanceListDetail(Map<String, Object> params, Subscriber<Bean<BalanceListBean.ListBean>> subscriber) {
        return service.myBalanceListDetail(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription addTeacherCourse(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.addTeacherCourse(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription bindingArea(BindingListBean params, Subscriber<Bean<Object>> subscriber) {
        return service.bindingArea(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription updateCourse(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.updateCourse(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription findCourseDetail(Map<String, Object> params, Subscriber<Bean<ManagerBean.CourseListBean>> subscriber) {
        return service.findCourseDetail(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription bindingCoachBank(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.bindingCoachBank(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription feedBackData(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.feedBackData(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription coachUserHome(Map<String, Object> params, Subscriber<Bean<MineBean>> subscriber) {
        return service.coachUserHome(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription personalData(Map<String, Object> params, Subscriber<Bean<InformationBean>> subscriber) {
        return service.personalData(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription updateCoachHome(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.updateCoachHome(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription forgetPassword(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.forgetPassword(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription updatePhone(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.updatePhone(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription settingPayPassword(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.settingPayPassword(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public Subscription get_all_time(Map<String, Object> params, Subscriber<Bean<ReturnTimeBean>> subscriber) {
        return service.get_all_time(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription set_shouke_time(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.set_shouke_time(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription close_shouke_time(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.close_shouke_time(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription save_time(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.save_time(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription coachMessageList(Map<String, Object> params, Subscriber<Bean<MessageListBean>> subscriber) {
        return service.coachMessageList(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription readMessage(Map<String, Object> params, Subscriber<Bean<String>> subscriber) {
        return service.readMessage(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription messageTotalCount(Map<String, Object> params, Subscriber<Bean<MaxMessageEntity>> subscriber) {
        return service.messageTotalCount(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription get_shouye_date(Map<String, Object> params, Subscriber<Bean<ScheduleBean>> subscriber) {
        return service.get_shouye_date(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription get_trail_detail(Map<String, Object> params, Subscriber<Bean<GetDailryBean>> subscriber) {
        return service.get_trail_detail(retuen_json_params(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription submit_tice(DairyBean params, Subscriber<Bean<Object>> subscriber) {
        return service.submit_tice(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription getCheckResult(Map<String, Object> params, Subscriber<Bean<CheckResultBean>> subscriber) {
        return service.getCheckResult(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //userService
    public Subscription findAllCity(Map<String, Object> params, Subscriber<Bean<AddressBean>> subscriber) {
        return userService.findAllCity(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription myBindingArea(Map<String, Object> params, Subscriber<Bean<List<ChangguanBean>>> subscriber) {
        return service.myBindingArea(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription deleteMyBindingArea(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.deleteMyBindingArea(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription agreeBindingArea(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.agreeBindingArea(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription bankList(Map<String, Object> params, Subscriber<Bean<List<BankCardBean>>> subscriber) {
        return service.bankList(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public Subscription class_detail(Map<String, Object> params, Subscriber<Bean<ClassDetailBean>> subscriber) {
        return service.class_detail(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription withdrawDeposit(Map<String, Object> params, Subscriber<Bean<Object>> subscriber) {
        return service.withdrawDeposit(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public Subscription searchDict(Map<String, Object> params, Subscriber<Bean<List<DictionaryBean>>> subscriber) {
        return service.searchDict(retuen_json_object(params))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
