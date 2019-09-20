package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.umeng.socialize.media.Base;

public class WriteDailryActivity extends BaseActivity {
    @BindView(R.id.back_img)
    ImageView back_img;
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
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
