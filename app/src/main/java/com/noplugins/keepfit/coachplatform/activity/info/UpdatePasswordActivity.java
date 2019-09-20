package com.noplugins.keepfit.coachplatform.activity.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.activity.LoginActivity;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.ActivityCollectorUtil;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.data.PwdCheckUtil;
import okhttp3.RequestBody;

import java.util.HashMap;
import java.util.Map;

public class UpdatePasswordActivity extends BaseActivity {
    @BindView(R.id.back_btn)
    ImageView back_btn;

    @BindView(R.id.edit_old_password)
    EditText edit_old_password;
    @BindView(R.id.edit_new_password1)
    EditText edit_new_password1;
    @BindView(R.id.edit_password2)
    EditText edit_password2;
    @BindView(R.id.login_btn)
    TextView login_btn;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_update_password);
        ButterKnife.bind(this);
        isShowTitle(false);
    }


    private void updatePassword(){
        if (!PwdCheckUtil.isLetterDigit(edit_new_password1.getText().toString())) {
            Toast.makeText(this, "密码不符合规则！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(edit_old_password.getText().toString())){
            Toast.makeText(this, "旧密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(edit_new_password1.getText().toString())){
            Toast.makeText(this, "新密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(edit_password2.getText().toString())){
            Toast.makeText(this, "确认密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("oldPassword", edit_old_password.getText().toString());
        params.put("userNum", SpUtils.getString(getApplicationContext(),AppConstants.USER_NAME));
        params.put("password", edit_new_password1.getText().toString());
        Gson gson = new Gson();
        String json_params = gson.toJson(params);
        Log.e(TAG, "修改密码的参数：" + json_params);
        String json = new Gson().toJson(params);//要传递的json
        RequestBody requestBody = RequestBody.create(null, json);

//        subscription = Network.getInstance("修改密码", getApplicationContext())
//
//                .update_my_password(requestBody,new ProgressSubscriberNew<>(String.class, new GsonSubscriberOnNextListener<String>() {
//                    @Override
//                    public void on_post_entity(String s, String message_id) {
//                        Toast.makeText(getApplicationContext(), message_id, Toast.LENGTH_SHORT).show();
//                        toLogin();
//                    }
//                }, new SubscriberOnNextListener<Bean<Object>>() {
//                    @Override
//                    public void onNext(Bean<Object> objectBean) {
//
//                    }
//
//                    @Override
//                    public void onError(String error) {
//                        Log.e(TAG, "修改失败：" + error);
//                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
//                    }
//                }, this, true));
    }
    @Override
    public void doBusiness(Context mContext) {
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
            }
        });
    }

    private void toLogin(){
        Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
        //退出
        SpUtils.putString(getApplicationContext(),AppConstants.TOKEN,"");
        SpUtils.putString(getApplicationContext(),AppConstants.PHONE,"");
        SpUtils.putString(getApplicationContext(),AppConstants.USER_NAME,"");
        startActivity(intent);
        ActivityCollectorUtil.finishAllActivity();

    }
}
