package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.*;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;

import com.noplugins.keepfit.coachplatform.MainActivity;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.bean.LoginBean;
import com.noplugins.keepfit.coachplatform.bean.YanZhengMaBean;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.data.SharedPreferencesHelper;
import com.noplugins.keepfit.coachplatform.util.data.StringsHelper;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import okhttp3.RequestBody;
import rx.Subscription;

import java.util.HashMap;
import java.util.Map;

/**
 * login --
 */
public class LoginActivity extends BaseActivity {


    @BindView(R.id.tv_user_protocol)
    TextView tv_user_protocol;
    @BindView(R.id.yanzhengma_tv)
    TextView yanzhengma_tv;
    @BindView(R.id.edit_password)
    TextView edit_password;
    @BindView(R.id.img_password)
    ImageView img_password;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.forget_password_btn)
    TextView forget_password_btn;
    @BindView(R.id.edit_phone_number)
    EditText edit_phone_number;
    @BindView(R.id.iv_delete_edit)
    ImageView iv_delete_edit;
    @BindView(R.id.login_btn)
    LoadingButton login_btn;
    @BindView(R.id.xieyi_check_btn)
    CheckBox xieyi_check_btn;


    private static boolean is_yanzhengma_logon = true;
    protected final String TAG = this.getClass().getSimpleName();//是否输出日志信息
    private String message_id = "";
    private boolean is_check_fuwu = false;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_login);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        yanzhengma_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yanzhengma_tv.getText().toString().equals("密码登录")) {
                    Log.e("登录方式", "密码登录");
                    is_yanzhengma_logon = false;
                    yanzhengma_tv.setText("验证码登录");
                    edit_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    edit_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)}); //最大输入长度

                    SpannableString s = new SpannableString("请输入密码");//这里输入自己想要的提示文字
                    edit_password.setHint(s);
                    img_password.setImageResource(R.drawable.password_icon);
                    tv_send.setVisibility(View.GONE);

                    forget_password_btn.setVisibility(View.VISIBLE);


                } else {
                    Log.e("登录方式", "验证码登录");

                    is_yanzhengma_logon = true;
                    yanzhengma_tv.setText("密码登录");
                    edit_password.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edit_password.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)}); //最大输入长度

                    SpannableString s = new SpannableString("请输入验证码");//这里输入自己想要的提示文字
                    edit_password.setHint(s);
                    img_password.setImageResource(R.drawable.yanzhengma_icon);
                    tv_send.setVisibility(View.VISIBLE);

                    forget_password_btn.setVisibility(View.GONE);


                }
            }
        });
        //发送验证码
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edit_phone_number.getText())) {
                    Toast.makeText(getApplicationContext(), "电话号码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!StringsHelper.isMobileOne(edit_phone_number.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "电话号码格式不正确！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    tv_send.setEnabled(false);//设置不可点击，等待60秒过后可以点击
                    timer.start();
                    //获取验证码接口
                    Get_YanZhengMa();
                }
            }
        });
        edit_phone_number.addTextChangedListener(textWatcher);

        iv_delete_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_phone_number.setText("");
            }
        });
        tv_user_protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xieyi_pop();
            }
        });
        login_btn.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!is_check_fuwu) {
                    Toast.makeText(LoginActivity.this, "请先勾选用户协议！", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(edit_phone_number.getText())) {
                    Toast.makeText(getApplicationContext(), "电话号码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!StringsHelper.isMobileOne(edit_phone_number.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "电话号码格式不正确！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edit_password.getText())) {
                    if (is_yanzhengma_logon) {
                        Toast.makeText(getApplicationContext(), "验证码不能为空！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "密码不能为空！", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                {
                    login_btn.startLoading();
                    if (is_yanzhengma_logon) {//如果是验证码登录，则让它设置密码
                        yanzheng_yanzhengma();
                    } else {
                        password_login();
                    }
                }


            }
        });
        xieyi_check_btn.setOnCheckedChangeListener(onCheckedChangeListener);

        //忘记密码
        forget_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void yanzheng_yanzhengma() {
        Map<String, Object> params = new HashMap<>();
        params.put("messageId", message_id);
        params.put("code", edit_password.getText().toString());
        params.put("phone", edit_phone_number.getText().toString());
        Subscription subscription = Network.getInstance("验证验证码和登录", this)
                .yanzheng_yanzhengma(params,
                        new ProgressSubscriber<>("验证验证码和登录", new SubscriberOnNextListener<Bean<YanZhengMaBean>>() {
                            @Override
                            public void onNext(Bean<YanZhengMaBean> result) {
                                login_btn.loadingComplete();
                                if (result.getData().getHavePassword() == 0) {//没有设置过密码
                                    Intent intent = new Intent(LoginActivity.this, SetPasswordActivity.class);
                                    startActivity(intent);
                                } else {//设置过密码
                                    Intent intent = new Intent(LoginActivity.this, SelectRoleActivity.class);
                                    startActivity(intent);
                                }
                                save_resource(result.getData().getToken(),
                                        result.getData().getUserNum(),
                                        result.getData().getTeacherType(),
                                        result.getData().getUserNum());


                            }

                            @Override
                            public void onError(String error) {
                                login_btn.loadingComplete();

                            }
                        }, this, false));
    }

    private void password_login() {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", edit_phone_number.getText().toString());
        params.put("password", edit_password.getText().toString());

        Subscription subscription = Network.getInstance("密码登录", this)
                .password_login(params,
                        new ProgressSubscriber<>("密码登录", new SubscriberOnNextListener<Bean<LoginBean>>() {
                            @Override
                            public void onNext(Bean<LoginBean> result) {
                                login_btn.loadingComplete();

                                save_resource(result.getData().getToken(),
                                        result.getData().getUserNum(),
                                        result.getData().getTeacherType(),
                                        result.getData().getUserNum());
                                if (null != SpUtils.getString(getApplicationContext(), AppConstants.TEACHER_TYPE)) {
                                    if (SpUtils.getString(getApplicationContext(), AppConstants.TEACHER_TYPE).length() > 0) {//已经审核过了
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {//未审核
                                        Intent intent = new Intent(LoginActivity.this, SelectRoleActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {
                                login_btn.loadingComplete();

                            }
                        }, this, false));
    }

    private void Get_YanZhengMa() {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", edit_phone_number.getText().toString());
        Subscription subscription = Network.getInstance("获取验证码", this)
                .get_yanzhengma(params,
                        new ProgressSubscriber<>("获取验证码", new SubscriberOnNextListener<Bean<String>>() {
                            @Override
                            public void onNext(Bean<String> result) {
                                message_id = result.getData();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));
    }

    private void xieyi_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.xieyi_pop_layout)
                .setBackGroundLevel(1)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(tv_user_protocol);

        /**设置逻辑*/
        View view = popupWindow.getContentView();
        TextView agree_btn = view.findViewById(R.id.agree_btn);
        agree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                is_check_fuwu = true;
                xieyi_check_btn.setChecked(true);
            }
        });
        TextView no_agree_btn = view.findViewById(R.id.no_agree_btn);
        no_agree_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private void save_resource(String token, String user_number, String teacher_type, String teacher_number) {
        SpUtils.putString(getApplicationContext(), AppConstants.TOKEN, token);
        SpUtils.putString(getApplicationContext(), AppConstants.USER_NAME, user_number);
        SpUtils.putString(getApplicationContext(), AppConstants.PHONE, edit_phone_number.getText().toString());
        SpUtils.putString(getApplicationContext(), AppConstants.TEACHER_TYPE, teacher_type);
        SpUtils.putString(getApplicationContext(), AppConstants.SELECT_TEACHER_NUMBER, teacher_number);
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0) {
                iv_delete_edit.setVisibility(View.VISIBLE);
            } else {
                iv_delete_edit.setVisibility(View.GONE);

            }
        }
    };

    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tv_send.setTextColor(Color.parseColor("#7B7B7B"));
            tv_send.setText("重新发送(" + millisUntilFinished / 1000 + "s)");

        }

        @Override
        public void onFinish() {
            tv_send.setTextColor(Color.parseColor("#292C31"));
            tv_send.setText("重新发送");
            tv_send.setEnabled(true);
        }
    };


    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean is_check) {
            if (is_check) {
                Log.e(TAG, "选中了");
                is_check_fuwu = true;
            } else {
                Log.e(TAG, "没选中");
                is_check_fuwu = false;
            }
        }
    };


}
