package com.noplugins.keepfit.coachplatform.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;
import com.noplugins.keepfit.coachplatform.util.ui.ViewPagerFragment;

import java.util.ArrayList;


public class StepOneFragment extends ViewPagerFragment {
    private View view;
    @BindView(R.id.xiayibu_btn)
    LoadingButton xiayibu_btn;
    @BindView(R.id.select_sex_btn)
    RelativeLayout select_sex_btn;
    @BindView(R.id.select_address_btn)
    RelativeLayout select_address_btn;
    @BindView(R.id.time_select_btn)
    RelativeLayout time_select_btn;
    OptionsPickerView sex_select_pop;
    private ArrayList<String> sexs = new ArrayList<>();

    public static StepOneFragment homeInstance(String title) {
        StepOneFragment fragment = new StepOneFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_step_one, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();

        }
        return view;
    }

    private void initView() {
        xiayibu_btn.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xiayibu_btn.startLoading();
                xiayibu_btn.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xiayibu_btn.loadingComplete();
                    }
                }, 1000);

            }
        });

        select_sex_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_sex_pop();


            }
        });

    }

    private void select_sex_pop() {
        sexs.clear();
        sexs.add("男");
        sexs.add("女");
        sex_select_pop = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String select_sex = sexs.get(options1);
                Log.e("选中的性别", select_sex);
            }
        })
                .setLayoutRes(R.layout.sex_select_pop, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView quxiao_btn = (TextView) v.findViewById(R.id.quxiao_btn);
                        quxiao_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sex_select_pop.dismiss();
                            }
                        });
                        TextView sure_btn = (TextView) v.findViewById(R.id.sure_btn);
                        sure_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sex_select_pop.returnData();
                                sex_select_pop.dismiss();
                            }
                        });
                    }
                })
                .isDialog(false)
                .setBgColor(Color.parseColor("#00000000"))
                .setDividerColor(Color.parseColor("#00000000"))
                .setOutSideCancelable(true)
                .build();

        sex_select_pop.setPicker(sexs);//添加数据
        sex_select_pop.show();
    }


}
