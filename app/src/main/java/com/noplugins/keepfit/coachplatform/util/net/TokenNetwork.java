package com.noplugins.keepfit.coachplatform.util.net;


import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.interceptor.LogInterceptor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by shiyujia02 on 2017/8/16.
 */
public class TokenNetwork {
    public static final int DEFAULT_TIMEOUT = 7;
    private static TokenNetwork mInstance;
    public MyService service;
    public static String token = "";
    //测试服
    private String main_url = "http://192.168.1.205:8888/api/gym-service/";

//    public static String main_url = "http://kft.ahcomg.com/api/gym-service/";

    public static String place_number = "GYM19072138381319";

    //获取单例
    public static TokenNetwork getInstance(String method, Context context) {
        if (context != null) {
            if ("".equals(SpUtils.getString(context, AppConstants.TOKEN))) {
                token = "";
                Log.e("没有添加token", token);
            } else {
                token = SpUtils.getString(context, AppConstants.TOKEN);
                Log.e("添加的头部的token", token);
            }
            if (mInstance == null) {
                synchronized (TokenNetwork.class) {
                    mInstance = new TokenNetwork(method, context);
                }
            }
        }
        return mInstance;
    }

    Retrofit retrofit;

    private TokenNetwork(String method, Context context) {

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
                .baseUrl(main_url)//设置请求网址根部
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        service = retrofit.create(MyService.class);

    }

    public static void trustAllHttpsCertificates()
            throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[1];
        trustAllCerts[0] = new TrustAllManager();
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(
                sc.getSocketFactory());
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
