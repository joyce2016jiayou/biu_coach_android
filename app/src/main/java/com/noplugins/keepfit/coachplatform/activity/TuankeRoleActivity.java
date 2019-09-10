package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;

public class TuankeRoleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_tuanke_role);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {

    }
}
