package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.noplugins.keepfit.coachplatform.MainActivity;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.bean.LoginBean;
import com.noplugins.keepfit.coachplatform.bean.SetPasswordBean;
import com.noplugins.keepfit.coachplatform.bean.TeacherStatusBean;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;
import rx.Subscription;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetPasswordActivity extends BaseActivity {
    @BindView(R.id.tiaoguo_tv)
    TextView tiaoguo_tv;
    @BindView(R.id.sure_btn)
    LoadingButton sure_btn;
    @BindView(R.id.edit_password_number)
    EditText edit_password_number;
    @BindView(R.id.edit_password_again)
    EditText edit_password_again;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_set_password);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        tiaoguo_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetPasswordActivity.this, SelectRoleActivity.class);
                startActivity(intent);
                finish();
            }
        });
        sure_btn.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(edit_password_number.getText().toString())) {
                    Toast.makeText(SetPasswordActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edit_password_again.getText().toString())) {
                    Toast.makeText(SetPasswordActivity.this, "再次输入的密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!edit_password_number.getText().toString().equals(edit_password_again.getText().toString())) {
                    Toast.makeText(SetPasswordActivity.this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String passRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
                    Pattern p = Pattern.compile(passRegex);
                    Matcher m = p.matcher(edit_password_number.getText().toString());
                    boolean b = m.matches();
                    if (!b) {
                        Toast.makeText(SetPasswordActivity.this, "请输入正确的格式！", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        set_password();
                    }

                }

            }
        });

    }

    private void set_password() {
        Map<String, Object> params = new HashMap<>();
        if (null != SpUtils.getString(getApplicationContext(), AppConstants.USER_NAME)) {
            params.put("userNum", SpUtils.getString(getApplicationContext(), AppConstants.USER_NAME));
        }
        params.put("password", edit_password_again.getText().toString());
        Subscription subscription = Network.getInstance("设置密码", this)
                .set_password(params,
                        new ProgressSubscriber<>("设置密码", new SubscriberOnNextListener<Bean<SetPasswordBean>>() {
                            @Override
                            public void onNext(Bean<SetPasswordBean> result) {
                                sure_btn.loadingComplete();

                                //获取教练状态
                                get_teacher_status();
                            }

                            @Override
                            public void onError(String error) {
                                sure_btn.loadingComplete();


                            }
                        }, this, false));
    }

    private void get_teacher_status() {
        Map<String, Object> params = new HashMap<>();
        params.put("userNum", SpUtils.getString(getApplicationContext(), AppConstants.SELECT_TEACHER_NUMBER));
        Subscription subscription = Network.getInstance("获取教练状态", this)
                .get_teacher_status(params,
                        new ProgressSubscriber<>("获取教练状态", new SubscriberOnNextListener<Bean<TeacherStatusBean>>() {
                            @Override
                            public void onNext(Bean<TeacherStatusBean> result) {
                                set_status(result);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));
    }

    private void set_status(Bean<TeacherStatusBean> result) {
        //teacherType 1团课 2私教 3都有
        //pType 私教 1 通过2拒绝3审核中
        //lType 团课 1 通过2拒绝3审核中
        // sign 是否签约上架 1 是 0 否
        if (result.getData().getTeacherType() == -1) {//目前没有身份
            Intent intent = new Intent(SetPasswordActivity.this, SelectRoleActivity.class);
            startActivity(intent);
            finish();
        } else if (result.getData().getTeacherType() == 1) {//团课
            if (result.getData().getLType() == 1) {
                if (result.getData().getSign() == 1) {//已签约
                    Intent intent = new Intent(SetPasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {//未签约
                    if (result.getData().getLType() == 1) {//通过的话就直接签约
                        if (result.getData().getSign() == 1) {//已签约
                            Intent intent = new Intent(SetPasswordActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(SetPasswordActivity.this, CheckStatusActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("into_index", 3);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }

                    } else if (result.getData().getLType() == 3) {//审核中
                        Intent intent = new Intent(SetPasswordActivity.this, CheckStatusActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("into_index", 2);
                        bundle.putInt("status", 1);//审核中
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else if (result.getData().getLType() == 2) {//拒绝
                        Intent intent = new Intent(SetPasswordActivity.this, CheckStatusActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("into_index", 2);
                        bundle.putInt("status", -1);//拒绝
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SetPasswordActivity.this, CheckStatusActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }

        } else if (result.getData().getTeacherType() == 2) {//私教
            if (result.getData().getPType() == 1) {
                if (result.getData().getSign() == 1) {//已签约
                    Intent intent = new Intent(SetPasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {//未签约
                    if (result.getData().getPType() == 1) {//通过的话
                        //再次判断是否签约
                        if (result.getData().getSign() == 1) {//已签约
                            Intent intent = new Intent(SetPasswordActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {//未签约
                            Intent intent = new Intent(SetPasswordActivity.this, CheckStatusActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("into_index", 3);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }

                    } else if (result.getData().getPType() == 3) {//审核中
                        Intent intent = new Intent(SetPasswordActivity.this, CheckStatusActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("into_index", 2);
                        bundle.putInt("status", 1);//审核中
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else if (result.getData().getPType() == 2) {//拒绝
                        Intent intent = new Intent(SetPasswordActivity.this, CheckStatusActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("into_index", 2);
                        bundle.putInt("status", -1);//拒绝
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } else {//没有私教身份
                        Intent intent = new Intent(SetPasswordActivity.this, CheckStatusActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        } else {
            if (result.getData().getSign() == 1) {//已签约
                Intent intent = new Intent(SetPasswordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(SetPasswordActivity.this, CheckStatusActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("into_index", 3);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        }
    }
}
