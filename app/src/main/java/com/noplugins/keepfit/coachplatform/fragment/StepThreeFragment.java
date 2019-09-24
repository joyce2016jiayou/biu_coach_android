package com.noplugins.keepfit.coachplatform.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.activity.CheckStatusActivity;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;
import com.noplugins.keepfit.coachplatform.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.coachplatform.util.ui.StepView;
import com.noplugins.keepfit.coachplatform.util.ui.ViewPagerFragment;
import org.greenrobot.eventbus.EventBus;


public class StepThreeFragment extends ViewPagerFragment {
    private View view;
    @BindView(R.id.return_submit)
    LoadingButton return_submit;
    @BindView(R.id.success_layout)
    LinearLayout success_layout;
    @BindView(R.id.fail_layout)
    LinearLayout fail_layout;


    private CheckStatusActivity checkStatusActivity;
    private StepView stepView;
    private NoScrollViewPager viewpager_content;

    public static StepThreeFragment homeInstance(String title) {
        StepThreeFragment fragment = new StepThreeFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_step_three, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
        }
        return view;
    }

    private void initView() {
        success_layout.setVisibility(View.VISIBLE);
        fail_layout.setVisibility(View.GONE);
        return_submit.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return_submit.startLoading();
                return_submit.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        return_submit.loadingComplete();
                        //跳转到提交页
//                        viewpager_content.setCurrentItem(0);
//                        int step = stepView.getCurrentStep();//设置进度条
//                        stepView.setCurrentStep(Math.max((step - 2) % stepView.getStepNum(), 0));
                        //跳转到下一个页面
                        viewpager_content.setCurrentItem(3);
                        int step = stepView.getCurrentStep();//设置进度条
                        stepView.setCurrentStep(Math.max((step + 1) % stepView.getStepNum(), 0));

                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof CheckStatusActivity) {
            checkStatusActivity = (CheckStatusActivity) activity;
            stepView = (StepView) checkStatusActivity.findViewById(R.id.step_view);
            viewpager_content = checkStatusActivity.findViewById(R.id.viewpager_content);
        }
    }

    @Override
    public void fetchData() {

    }
}
