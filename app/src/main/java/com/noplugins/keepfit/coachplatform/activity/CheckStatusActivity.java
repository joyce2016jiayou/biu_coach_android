package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.adapter.ContentPagerAdapterMy;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.callback.DialogCallBack;
import com.noplugins.keepfit.coachplatform.fragment.StepFourFragement;
import com.noplugins.keepfit.coachplatform.fragment.StepOneFragment;
import com.noplugins.keepfit.coachplatform.fragment.StepThreeFragment;
import com.noplugins.keepfit.coachplatform.fragment.StepTwoFragment;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;
import com.noplugins.keepfit.coachplatform.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.coachplatform.util.ui.PopWindowHelper;
import com.noplugins.keepfit.coachplatform.util.ui.StepView;
import com.umeng.socialize.media.Base;

import java.util.ArrayList;
import java.util.List;

public class CheckStatusActivity extends BaseActivity {
    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.step_view)
    StepView step_view;
    @BindView(R.id.viewpager_content)
    NoScrollViewPager viewpager_content;
    @BindView(R.id.top_title_tv)
    TextView top_title_tv;

    public String select_card_zheng_path = "";
    public String select_card_fan_path = "";
    public String user_name = "";
    public String card_id = "";
    public String sex = "";
    public String phone = "";
    public String city = "";
    public String school = "";
    public String ruhang_time = "";
    public List<Fragment> tabFragments = new ArrayList<>();
    int into_index = 0;
    public int into_status = 0;
    Bundle bundle;
    public int fragment_type = -1;
    public int select_index = 0;
    public boolean is_upload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {
        if (parms != null) {
            bundle = parms;
            fragment_type = parms.getInt("fragment_type", -1);
            Log.d("fragment_type", "fragment_type:" + fragment_type);
            into_index = parms.getInt("into_index", -1);

        }
    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_check_status);
        ButterKnife.bind(this);
        isShowTitle(false);

    }

    @Override
    public void doBusiness(Context mContext) {
        String[] titles = new String[]{"基础资料", "专业信息", "提交审核", "签约上架"};
        step_view.setTitles(titles);
        /*//设置前进
        int step = stepView.getCurrentStep();
        //设置进度
        stepView.setCurrentStep(Math.max((step - 1) % stepView.getStepNum(), 0));*/
      /*  //设置后退
      int step = stepView.getCurrentStep();
        //设置进度
        stepView.setCurrentStep((step + 1) % stepView.getStepNum());*/

        //设置视图
        tabFragments.add(StepOneFragment.homeInstance("第一页"));
        tabFragments.add(StepTwoFragment.homeInstance("第二页"));
        tabFragments.add(StepThreeFragment.homeInstance("第三页"));
        tabFragments.add(StepFourFragement.homeInstance("第四页"));

        ContentPagerAdapterMy contentAdapter = new ContentPagerAdapterMy(getSupportFragmentManager(), tabFragments);
        viewpager_content.setAdapter(contentAdapter);
        if (into_index == 3) {//未签合同
            int step = step_view.getCurrentStep();//设置进度条
            step_view.setCurrentStep((step + 3) % step_view.getStepNum());
            select_index = 3;
            top_title_tv.setText("签约上架");
            viewpager_content.setCurrentItem(select_index);
        } else if (into_index == 2) {
            int status = bundle.getInt("status", -1);
            if (status == 1) {//审核中
                into_status = 1;
            } else if (status == -1) {//已被拒绝
                into_status = -1;
            }
            int step = step_view.getCurrentStep();//设置进度条
            step_view.setCurrentStep((step + 2) % step_view.getStepNum());
            select_index = 2;
            top_title_tv.setText("等待审核");
            viewpager_content.setCurrentItem(select_index);
        } else {
            select_index = 0;
            top_title_tv.setText("教练入驻");
            viewpager_content.setCurrentItem(select_index);
        }
        if (fragment_type == 2 || fragment_type == 1) {
            int step = step_view.getCurrentStep();
            step_view.setCurrentStep((step + 1) % step_view.getStepNum());
            select_index = 1;
            top_title_tv.setText("教练入驻");
            viewpager_content.setCurrentItem(select_index);

        }
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select_index == 0) {
                    go_to_last_step("教练入驻", 0);
                } else if (select_index == 1) {
                    go_to_last_step("教练入驻", 1);
                } else if (select_index == 2) {
                    //go_to_last_step("教练入驻", 2);

                    if (is_upload) {//已经提交过了
                        Intent intent = new Intent(CheckStatusActivity.this, SelectRoleActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else if (select_index == 3) {
                    go_to_last_step("等待审核", 3);
                }
            }
        });
    }

    private void go_to_last_step(String title, int mselect_index) {
        if (mselect_index == 0) {
            top_title_tv.setText(title);
            show_advice_pop();
        } else {
            top_title_tv.setText(title);
            Log.e("就算会计分录斯柯达", mselect_index + "");
            viewpager_content.setCurrentItem(mselect_index - 1);
            int step = step_view.getCurrentStep();
            Log.e("进度条", step + "");
            step_view.setCurrentStep(Math.max((step - 1) % step_view.getStepNum(), 0));
            select_index = select_index - 1;

        }

    }

    @Override
    public void onBackPressed() {
        if (select_index == 0) {
            go_to_last_step("教练入驻", 0);
        } else if (select_index == 1) {
            go_to_last_step("教练入驻", 1);
        } else if (select_index == 2) {
            if (is_upload) {//已经提交过了
                Intent intent = new Intent(CheckStatusActivity.this, SelectRoleActivity.class);
                startActivity(intent);
                finish();
            }
        } else if (select_index == 3) {
            go_to_last_step("等待审核", 3);
        }
    }

    private void show_advice_pop() {
        PopWindowHelper.public_tishi_pop(CheckStatusActivity.this, "温馨提示", "是否退出资料提交？", "取消", "确定", new DialogCallBack() {
            @Override
            public void save() {
                if (fragment_type == 2 || fragment_type == 1) {
                    finish();
                    return;
                }
                Intent intent = new Intent(CheckStatusActivity.this, SelectRoleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("back", 1);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

            @Override
            public void cancel() {

            }
        });
    }
}
