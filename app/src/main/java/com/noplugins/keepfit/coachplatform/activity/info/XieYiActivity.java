package com.noplugins.keepfit.coachplatform.activity.info;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class XieYiActivity extends BaseActivity {

    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.webView)
    WebView webView;
    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_xie_yi);
        ButterKnife.bind(this);
        isShowTitle(false);

    }

    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webView.loadUrl("http://www.noplugins.com/doc/jiaolian_xieyi.html");
    }





}
