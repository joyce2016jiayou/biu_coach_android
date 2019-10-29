package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    public int into_status=0;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {
        if (parms != null) {
            bundle  = parms;
            int fragment_type = parms.getInt("fragment_type", -1);
            if (fragment_type == 2) {
                viewpager_content.setCurrentItem(1);
            }
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
            viewpager_content.setCurrentItem(3);
        }else if(into_index == 2){
            int status = bundle.getInt("status",-1);
            if(status==1){//审核中
                into_status = 1;
            }else if(status==-1){//已被拒绝
                into_status = -1;
            }
            int step = step_view.getCurrentStep();//设置进度条
            step_view.setCurrentStep((step + 2) % step_view.getStepNum());
            viewpager_content.setCurrentItem(2);
        } else {
            viewpager_content.setCurrentItem(0);
        }

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckStatusActivity.this, SelectRoleActivity.class);
                Bundle bundle  = new Bundle();
                bundle.putInt("back",1);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        show_advice_pop();
    }

    private void show_advice_pop() {
        PopWindowHelper.public_tishi_pop(CheckStatusActivity.this, "温馨提示", "是否退出资料提交？", "取消", "确定", new DialogCallBack() {
            @Override
            public void save() {
                finish();
            }

            @Override
            public void cancel() {

            }
        });
    }
}
