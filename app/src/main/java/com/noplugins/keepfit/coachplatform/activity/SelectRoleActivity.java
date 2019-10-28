package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huantansheng.easyphotos.models.puzzle.Line;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.callback.DialogCallBack;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.ui.PopWindowHelper;

public class SelectRoleActivity extends BaseActivity {

    @BindView(R.id.tuanke_quxiao_btn)
    TextView tuanke_quxiao_btn;
    @BindView(R.id.tuanke_sure_btn)
    TextView tuanke_sure_btn;
    @BindView(R.id.cancel_sijiao_btn)
    TextView cancel_sijiao_btn;
    @BindView(R.id.sijiao_sure_btn)
    TextView sijiao_sure_btn;
    @BindView(R.id.select_tuanke_btn)
    LinearLayout select_tuanke_btn;
    @BindView(R.id.select_sijiao_btn)
    LinearLayout select_sijiao_btn;
    @BindView(R.id.select_role_view)
    RelativeLayout select_role_view;
    @BindView(R.id.select_tuanke_view)
    RelativeLayout select_tuanke_view;
    @BindView(R.id.select_sijiao_view)
    RelativeLayout select_sijiao_view;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_select_role);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        select_tuanke_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_role_view.setVisibility(View.GONE);
                select_tuanke_view.setVisibility(View.VISIBLE);
                select_sijiao_view.setVisibility(View.GONE);
            }
        });
        select_sijiao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_role_view.setVisibility(View.GONE);
                select_tuanke_view.setVisibility(View.GONE);
                select_sijiao_view.setVisibility(View.VISIBLE);
            }
        });
        tuanke_quxiao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_role_view.setVisibility(View.VISIBLE);
                select_tuanke_view.setVisibility(View.GONE);
                select_sijiao_view.setVisibility(View.GONE);
            }
        });
        cancel_sijiao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_role_view.setVisibility(View.VISIBLE);
                select_tuanke_view.setVisibility(View.GONE);
                select_sijiao_view.setVisibility(View.GONE);
            }
        });

        //选则了私教
        sijiao_sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRoleActivity.this, CheckStatusActivity.class);
                startActivity(intent);
                SpUtils.putString(getApplicationContext(), AppConstants.SELECT_TEACHER_TYPE, "1");//团课
            }
        });
        //选择了团课
        tuanke_sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectRoleActivity.this, CheckStatusActivity.class);
                startActivity(intent);
                SpUtils.putString(getApplicationContext(), AppConstants.SELECT_TEACHER_TYPE, "2");//私教
            }
        });
    }

    @Override
    public void onBackPressed() {
        show_advice_pop();
    }

    private void show_advice_pop() {
        PopWindowHelper.public_tishi_pop(SelectRoleActivity.this, "温馨提示", "是否退出app？", "取消", "确定", new DialogCallBack() {
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
}
