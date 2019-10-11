package com.noplugins.keepfit.coachplatform.util.net;

import com.noplugins.keepfit.coachplatform.bean.AddressBean;
import com.noplugins.keepfit.coachplatform.bean.QiNiuToken;
import com.noplugins.keepfit.coachplatform.bean.TagBean;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

import java.util.List;

public interface UserService {
    /**
     * 获取地址
     *
     * @return
     */
    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST("findAllCity")
    Observable<Bean<AddressBean>> findAllCity(@Body RequestBody json);
}
