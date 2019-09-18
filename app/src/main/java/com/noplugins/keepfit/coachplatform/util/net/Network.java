package com.noplugins.keepfit.coachplatform.util.net;


import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public static final int DEFAULT_TIMEOUT = 7;
    private static Network mInstance;
    public MyService service;
    public static String token = "";
    //测试服
    private String test_main_url = "http://192.168.1.45:8888/api/cust-service";
    private String main_url = "http://kft.ahcomg.com/api/cust-service";
    private static String MRTHOD_NAME = "";

    public String get_main_url(String str) {
        if (str.equals("test")) {
            return test_main_url + "/custuser/";
        } else {
            return main_url + "/custuser/";
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

    Retrofit retrofit;

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
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)//设置写的超时时间
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)//超时处理
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MINUTES)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(get_main_url("main"))//设置请求网址根部
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(MyService.class);

    }


    private static class TrustAllManager
            implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkServerTrusted(X509Certificate[] certs,
                                       String authType)
                throws CertificateException {
        }

        public void checkClientTrusted(X509Certificate[] certs,
                                       String authType)
                throws CertificateException {
        }
    }

}
