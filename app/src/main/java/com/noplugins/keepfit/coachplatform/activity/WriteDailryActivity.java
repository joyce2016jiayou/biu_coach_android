package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.adapter.DailyTagAdapter;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.util.BaseUtils;
import com.noplugins.keepfit.coachplatform.util.ui.GridViewForScrollView;
import com.umeng.socialize.media.Base;

import java.util.ArrayList;
import java.util.List;

public class WriteDailryActivity extends BaseActivity {
    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.grid_view)
    GridView grid_view;

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
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            strings.add("哈哈" + i);
        }
        DailyTagAdapter tagAdapter = new DailyTagAdapter(this, strings);
        grid_view.setAdapter(tagAdapter);

    }
}
