package com.noplugins.keepfit.coachplatform.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.activity.*;
import com.noplugins.keepfit.coachplatform.adapter.ClassAdapter;
import com.noplugins.keepfit.coachplatform.adapter.DateSelectAdapter;
import com.noplugins.keepfit.coachplatform.bean.ClassDateBean;
import com.noplugins.keepfit.coachplatform.bean.ScheduleBean;
import com.noplugins.keepfit.coachplatform.bean.SelectDateBean;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.BaseUtils;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.data.DateUtils;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.ui.toast.SuperCustomToast;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscription;

import java.util.*;


public class ScheduleFragment extends Fragment {
    private View view;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.class_recycler_view)
    RecyclerView class_recycler_view;
    //    @BindView(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;
    @BindView(R.id.touxiang_image)
    CircleImageView touxiang_image;
    @BindView(R.id.teacher_time_btn)
    LinearLayout teacher_time_btn;
    @BindView(R.id.more_btn)
    LinearLayout more_btn;
    @BindView(R.id.ll_class_manager)
    LinearLayout ll_class_manager;
    @BindView(R.id.shouke_cg)
    LinearLayout shouke_cg;
    List<ClassDateBean> classDateBeans = new ArrayList<>();
    List<SelectDateBean> selectDateBeans = new ArrayList<>();
    private String select_date = "";

    public static ScheduleFragment getInstance(String title) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_schedule, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
        }
        return view;
    }


    private void initView() {
        if (classDateBeans.size() > 0) {
            classDateBeans.clear();
        }
        if (AppConstants.selectDateBeans.size() > 0) {
            selectDateBeans.addAll(AppConstants.selectDateBeans);
        } else {
            selectDateBeans.addAll(DateUtils.getmoredate());
        }
        String current_date_str = selectDateBeans.get(0).getCurrent_date();
        select_date = current_date_str;


        init_class_date_resource(select_date);
        //初始化日期数据
        init_date_resoure();


        touxiang_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        teacher_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseUtils.isFastClick()) {
                    Intent intent = new Intent(getActivity(), TeacherTimeActivity.class);
                    startActivity(intent);
                }

            }
        });
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseUtils.isFastClick()) {
                    Intent intent = new Intent(getActivity(), YueKeInformationActivity.class);
                    startActivity(intent);
                }

            }
        });
        ll_class_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseUtils.isFastClick()) {
                    Intent intent = new Intent(getActivity(), ClassManagerActivity.class);
                    startActivity(intent);
                }
            }
        });

        shouke_cg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseUtils.isFastClick()) {
                    if (SpUtils.getString(getActivity(), AppConstants.TEACHER_TYPE).equals("1")) {
                        SuperCustomToast.getInstance(getActivity())
                                .show("暂无私教身份");
                        return;
                    }
                    Intent intent = new Intent(getActivity(), ShoukeCgActivity.class);
                    startActivity(intent);
                }
            }
        });


//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
//            }
//        });
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.finishLoadMore(1000/*,false*/);//传入false表示加载失败
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void init_class_date_resource(String select_date) {
        Map<String, Object> params = new HashMap<>();
        params.put("teacherNum", SpUtils.getString(getActivity(), AppConstants.SELECT_TEACHER_NUMBER));
        params.put("date", select_date);
        Subscription subscription = Network.getInstance("首页接口", getActivity())
                .get_shouye_date(params,
                        new ProgressSubscriber<>("首页接口", new SubscriberOnNextListener<Bean<ScheduleBean>>() {
                            @Override
                            public void onNext(Bean<ScheduleBean> result) {
                                if(classDateBeans.size()>0){
                                    classDateBeans.clear();
                                }
                                for (int i = 0; i < 2; i++) {
                                    if (i == 0) {
                                        ClassDateBean selectDateBean = new ClassDateBean();
                                        selectDateBean.setType("未结束");
                                        selectDateBean.setYijieshu_list(result.getData().getAlreadyEndCourse());
                                        selectDateBean.setWeijieshu_list(result.getData().getNoEndCourse());
                                        classDateBeans.add(selectDateBean);
                                    } else {
                                        ClassDateBean selectDateBean = new ClassDateBean();
                                        selectDateBean.setType("已结束");
                                        selectDateBean.setYijieshu_list(result.getData().getAlreadyEndCourse());
                                        selectDateBean.setWeijieshu_list(result.getData().getNoEndCourse());
                                        classDateBeans.add(selectDateBean);
                                    }
                                }
                                LinearLayoutManager class_linearLayoutManager = new LinearLayoutManager(getActivity());
                                class_recycler_view.setLayoutManager(class_linearLayoutManager);
                                if (classDateBeans.get(0).getYijieshu_list().size() == 0 && classDateBeans.get(0).getWeijieshu_list().size() == 0) {
                                    classDateBeans.clear();
                                }
                                ClassAdapter classAdapter = new ClassAdapter(classDateBeans, ScheduleFragment.this);
                                class_recycler_view.setAdapter(classAdapter);
                                classAdapter.setOnItemClickListener(new ClassAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {

                                    }
                                });

                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), true));


    }

    private void init_date_resoure() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(linearLayoutManager);
        DateSelectAdapter dateSelectAdapter = new DateSelectAdapter(selectDateBeans, getActivity());
        recycler_view.setAdapter(dateSelectAdapter);
        dateSelectAdapter.setOnItemClickListener(new DateSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (BaseUtils.isFastClick()) {
                    for (int i = 0; i < selectDateBeans.size(); i++) {
                        selectDateBeans.get(i).setIs_check(false);
                    }
                    if (!selectDateBeans.get(position).isIs_check()) {
                        selectDateBeans.get(position).setIs_check(true);
                    }

                    dateSelectAdapter.notifyDataSetChanged();
                    if (classDateBeans.size() > 0) {
                        classDateBeans.clear();
                    }
                    select_date = selectDateBeans.get(position).getCurrent_date();
                    init_class_date_resource(select_date);
                }
            }
        });

    }


}
