package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.noplugins.keepfit.coachplatform.MainActivity;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.adapter.DailyTagAdapter;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.bean.LoginBean;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.BaseUtils;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.ui.GridViewForScrollView;
import com.umeng.socialize.media.Base;
import rx.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteDailryActivity extends BaseActivity {
    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.grid_view)
    GridView grid_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_write_dailry);
        ButterKnife.bind(this);
        isShowTitle(false);

    }

    @Override
    public void doBusiness(Context mContext) {
        init_dailry_resource();


        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            strings.add("哈哈" + i);
        }
        DailyTagAdapter tagAdapter = new DailyTagAdapter(this, strings);
        grid_view.setAdapter(tagAdapter);

    }

    private void init_dailry_resource() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("userNum", SpUtils.getString(getApplicationContext(),AppConstants.SELECT_TEACHER_NUMBER));
//        params.put("type");
//        params.put("password", edit_password.getText().toString());
//
//        Subscription subscription = Network.getInstance("密码登录", this)
//                .password_login(params,
//                        new ProgressSubscriber<>("密码登录", new SubscriberOnNextListener<Bean<LoginBean>>() {
//                            @Override
//                            public void onNext(Bean<LoginBean> result) {
//
//                            }
//
//                            @Override
//                            public void onError(String error) {
//
//                            }
//                        }, this, false));
    }
}
