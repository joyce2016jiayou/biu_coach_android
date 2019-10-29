package com.noplugins.keepfit.coachplatform.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.noplugins.keepfit.coachplatform.MainActivity;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.activity.LoginActivity;
import com.noplugins.keepfit.coachplatform.activity.SelectRoleActivity;
import com.noplugins.keepfit.coachplatform.bean.CheckInformationBean;
import com.noplugins.keepfit.coachplatform.bean.LoginBean;
import com.noplugins.keepfit.coachplatform.callback.DialogCallBack;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;
import com.noplugins.keepfit.coachplatform.util.ui.PopWindowHelper;
import com.noplugins.keepfit.coachplatform.util.ui.ViewPagerFragment;
import rx.Subscription;

import java.util.HashMap;
import java.util.Map;


public class StepFourFragement extends ViewPagerFragment {
    private View view;
    @BindView(R.id.submit_btn)
    LoadingButton submit_btn;
    @BindView(R.id.xieyi_check_btn)
    CheckBox xieyi_check_btn;
    @BindView(R.id.webView)
    WebView webView;
    private boolean is_check;

    public static StepFourFragement homeInstance(String title) {
        StepFourFragement fragment = new StepFourFragement();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_step_four_fragement, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
        }
        return view;
    }

    private void initView() {
        webView = (WebView) view.findViewById(R.id.webView);
        //自适应屏幕
        WebSettings webSettings = webView.getSettings();
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        webView.loadUrl("file:///android_asset/hetong.html");

        xieyi_check_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    is_check = true;
                } else {
                    is_check = false;
                }
            }
        });
        submit_btn.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit_btn.startLoading();
                submit_btn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (is_check) {
                            submit_btn.startLoading();
                            agree_xieyi();
                        } else {
                            Toast.makeText(getActivity(), "请先勾选协议", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 2000);

            }
        });

    }

    private void agree_xieyi() {
        Map<String, Object> params = new HashMap<>();
        params.put("userNum", SpUtils.getString(getActivity(), AppConstants.SELECT_TEACHER_NUMBER));
        params.put("sign", "1");
        Subscription subscription = Network.getInstance("同意协议", getActivity())
                .agree_xieyi(params,
                        new ProgressSubscriber<>("同意协议", new SubscriberOnNextListener<Bean<Object>>() {
                            @Override
                            public void onNext(Bean<Object> result) {
                                submit_btn.loadingComplete();
                                Toast.makeText(getActivity(), "恭喜您，签约成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onError(String error) {
                                submit_btn.loadingComplete();
                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

                            }
                        }, getActivity(), false));
    }


    private void show_advice_pop() {
        PopWindowHelper.public_tishi_pop(getActivity(), "温馨提示", "是否退出app？", "取消", "确定", new DialogCallBack() {
            @Override
            public void save() {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }

            @Override
            public void cancel() {

            }
        });
    }


    @Override
    public void fetchData() {

    }
}
