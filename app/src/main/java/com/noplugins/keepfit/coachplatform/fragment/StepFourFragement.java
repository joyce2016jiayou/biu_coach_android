package com.noplugins.keepfit.coachplatform.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.noplugins.keepfit.coachplatform.MainActivity;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;
import com.noplugins.keepfit.coachplatform.util.ui.ViewPagerFragment;


public class StepFourFragement extends ViewPagerFragment {
    private View view;
    @BindView(R.id.submit_btn)
    LoadingButton submit_btn;

    public static StepFourFragement homeInstance(String title) {
        StepFourFragement fragment = new StepFourFragement();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_step_four_fragement, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
        }
        return view;
    }

    private void initView() {
        submit_btn.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit_btn.startLoading();
                submit_btn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        submit_btn.loadingComplete();
                        Toast.makeText(getActivity(), "恭喜您，签约成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }, 2000);

            }
        });
    }


    @Override
    public void fetchData() {

    }
}
