package com.noplugins.keepfit.coachplatform.adapter;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerBean;
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeacherBean;

import java.util.List;

public class ManagerTeacherAdapter extends BaseQuickAdapter<ManagerBean.CourseListBean, BaseViewHolder> {
    public ManagerTeacherAdapter(@Nullable List<ManagerBean.CourseListBean> data) {
        super(R.layout.item_manager_teacher,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ManagerBean.CourseListBean item) {
        helper.setText(R.id.tv_time_day,"创建时间："+item.getCreateDate().split(" ")[0]);
        helper.setText(R.id.tv_teacher_class,"课程："+item.getCourseName());
        helper.setText(R.id.tv_teacher_time,"时长：1h");


        if (item.getSearchType() == 1){
            helper.setText(R.id.tv_item,"已上架");
            helper.setText(R.id.tv_up_down,"下架");
        } else {
            helper.setText(R.id.tv_item,"已下架");
            helper.setText(R.id.tv_up_down,"上架");
        }

        helper.setText(R.id.tv_sales_num,"已售："+item.getBuynum());
        helper.setText(R.id.tv_teacher_price,"¥"+item.getFinalPrice());

        helper.addOnClickListener(R.id.tv_up_down);
        helper.addOnClickListener(R.id.tv_edit);
        helper.addOnClickListener(R.id.rl_jump);

    }
}
