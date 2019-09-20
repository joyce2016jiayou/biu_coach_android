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
import com.huantansheng.easyphotos.models.puzzle.Line;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.activity.TeacherTimeActivity;
import com.noplugins.keepfit.coachplatform.activity.YueKeInformationActivity;
import com.noplugins.keepfit.coachplatform.adapter.ClassAdapter;
import com.noplugins.keepfit.coachplatform.adapter.DateSelectAdapter;
import com.noplugins.keepfit.coachplatform.bean.ClassDateBean;
import com.noplugins.keepfit.coachplatform.bean.SelectDateBean;
import com.noplugins.keepfit.coachplatform.util.data.DateUtils;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;


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
        //初始化日期数据
        init_date_resoure();
        //初始化课程数控
        init_class_date_resource();
        touxiang_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        teacher_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TeacherTimeActivity.class);
                startActivity(intent);
            }
        });
        more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), YueKeInformationActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init_class_date_resource() {
        LinearLayoutManager class_linearLayoutManager = new LinearLayoutManager(getActivity());
        class_recycler_view.setLayoutManager(class_linearLayoutManager);
        List<ClassDateBean> selectDateBeans = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                ClassDateBean selectDateBean = new ClassDateBean();
                selectDateBean.setType("未结束");
                List<String> strings = new ArrayList<>();
                for (int k = 0; k < 2; k++) {
                    strings.add("场馆" + k);
                }
                selectDateBean.setWeijieshu_list(strings);
                selectDateBeans.add(selectDateBean);
            } else {
                ClassDateBean selectDateBean = new ClassDateBean();
                selectDateBean.setType("已结束");
                List<String> strings = new ArrayList<>();
                for (int k = 0; k < 3; k++) {
                    strings.add("场馆" + k);
                }
                selectDateBean.setYijieshu_list(strings);
                selectDateBeans.add(selectDateBean);
            }
        }

        ClassAdapter classAdapter = new ClassAdapter(selectDateBeans, ScheduleFragment.this);
        class_recycler_view.setAdapter(classAdapter);
        classAdapter.setOnItemClickListener(new ClassAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

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

    private void init_date_resoure() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycler_view.setLayoutManager(linearLayoutManager);
        List<SelectDateBean> selectDateBeans = new ArrayList<>(DateUtils.getmoredate());
        DateSelectAdapter dateSelectAdapter = new DateSelectAdapter(selectDateBeans, getActivity());
        recycler_view.setAdapter(dateSelectAdapter);
        dateSelectAdapter.setOnItemClickListener(new DateSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < selectDateBeans.size(); i++) {
                    selectDateBeans.get(i).setIs_check(false);
                }
                if (!selectDateBeans.get(position).isIs_check()) {
                    selectDateBeans.get(position).setIs_check(true);
                }
                dateSelectAdapter.notifyDataSetChanged();
            }
        });

    }


}
