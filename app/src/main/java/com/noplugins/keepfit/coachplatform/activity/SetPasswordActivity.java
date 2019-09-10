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
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;

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
                }
                {
                    String passRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
                    Pattern p = Pattern.compile(passRegex);
                    Matcher m = p.matcher(edit_password_number.getText().toString());
                    boolean b = m.matches();
                    if (!b) {
                        Toast.makeText(SetPasswordActivity.this, "请输入正确的格式！", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        sure_btn.startLoading();
                        sure_btn.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sure_btn.loadingComplete();
                            }
                        }, 1000);

                        Intent intent = new Intent(SetPasswordActivity.this, SelectRoleActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }

            }
        });

    }
}
