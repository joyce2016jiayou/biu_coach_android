package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;

import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.util.data.StringsHelper;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import okhttp3.RequestBody;

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


    private boolean is_yanzhengma_logon = true;
    protected final String TAG = this.getClass().getSimpleName();//是否输出日志信息


    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_login);
        ButterKnife.bind(this);
        isShowTitle(false);
//        StringsHelper.setEditTextHintSize(edit_phone_number, "请输入手机号", 15);
//        StringsHelper.setEditTextHintSize(edit_password, "请输入密码", 15);
//        edit_phone_number.addTextChangedListener(phone_number_jiaoyan);
//        edit_phone_number.setKeyListener(DigitsKeyListener.getInstance("0123456789"));//设置输入数字
    }

    @Override
    public void doBusiness(Context mContext) {
        yanzhengma_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yanzhengma_tv.getText().toString().equals("密码登录")) {
                    is_yanzhengma_logon = false;
                    yanzhengma_tv.setText("验证码登录");

                    SpannableString s = new SpannableString("请输入密码");//这里输入自己想要的提示文字
                    edit_password.setHint(s);
                    img_password.setImageResource(R.drawable.password_icon);
                    tv_send.setVisibility(View.GONE);

                    forget_password_btn.setVisibility(View.VISIBLE);


                } else {
                    is_yanzhengma_logon = true;
                    yanzhengma_tv.setText("密码登录");

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
                    //Get_YanZhengMa();
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
                login_btn.startLoading();
                login_btn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        login_btn.loadingComplete();
                    }
                }, 1000);


                if (is_yanzhengma_logon) {//如果是验证码登录，则让它设置密码
                    Intent intent = new Intent(LoginActivity.this, SetPasswordActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LoginActivity.this, SelectRoleActivity.class);
                    startActivity(intent);
                }
            }
        });
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

    private void Login() {
      /*  Map<String, String> params = new HashMap<>();
        params.put("password", edit_password.getText().toString());
        params.put("phone", edit_phone_number.getText().toString());
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        Log.e(TAG, "登录参数：" + json_params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);

        subscription = Network.getInstance("登录", getApplicationContext())
                .login(requestBody, new ProgressSubscriberNew<>(LoginEntity.class, new GsonSubscriberOnNextListener<LoginEntity>() {
                    @Override
                    public void on_post_entity(LoginEntity loginEntity, String s) {
                        Log.e(TAG, "登录成功：" + s);
                        if (is_save_number) {//保存密码

                        } else {
//                            Intent intent = new Intent(LoginActivity.this, UserPermissionSelectActivity.class);
//                            startActivity(intent);
//                            finish();
                        }

                        //保存密码
                        if ("".equals(SharedPreferencesHelper.get(getApplicationContext(), Network.login_token, ""))) {
                            SharedPreferencesHelper.put(getApplicationContext(),  Network.login_token, loginEntity.getToken());
                            SharedPreferencesHelper.put(getApplicationContext(), Network.phone_number, edit_phone_number.getText().toString());
                            SharedPreferencesHelper.put(getApplicationContext(), Network.changguan_number, loginEntity.getGymAreaNum());

                        } else {
                            SharedPreferencesHelper.remove(getApplicationContext(),  Network.login_token);
                            SharedPreferencesHelper.put(getApplicationContext(),  Network.login_token, loginEntity.getToken());
                            SharedPreferencesHelper.put(getApplicationContext(), Network.phone_number, edit_phone_number.getText().toString());
                            SharedPreferencesHelper.put(getApplicationContext(), Network.changguan_number, loginEntity.getGymAreaNum());
                        }

                        //type 0  type 1场馆主 2经理  3前台
                        if (loginEntity.getType() == 0) {//没有提交过审核资料
                            SharedPreferencesHelper.put(getApplicationContext(), Network.no_submit_information, "true");

                            Intent intent = new Intent(LoginActivity.this, UserPermissionSelectActivity.class);
                            startActivity(intent);
                            finish();
                        } else {//已经提交过资料
                            Intent intent = new Intent(LoginActivity.this, KeepFitActivity.class);
                            startActivity(intent);
                            finish();
                        }


                    }
                }, new SubscriberOnNextListener<Bean<Object>>() {
                    @Override
                    public void onNext(Bean<Object> result) {
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "登录失败：" + error);
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                }, this, true));*/

    }


    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean is_check) {
            if (is_check) {
                Log.e(TAG, "选中了");
            } else {
                Log.e(TAG, "没选中");

            }
        }
    };


}
