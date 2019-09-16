package com.noplugins.keepfit.coachplatform.adapter;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;

import java.util.List;

public class ManagerTeacherAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ManagerTeacherAdapter(@Nullable List<String> data) {
        super(R.layout.item_manager_teacher,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_time_day,"2019-10-11");
        helper.setText(R.id.tv_time_week,"周三");
        helper.setText(R.id.tv_time_hour,"8:10-9:30");
        helper.setText(R.id.tv_teacher_class,"课程：拳击-初级拳击格斗");
        helper.setText(R.id.tv_teacher_time,"时长：1h");
        if (helper.getLayoutPosition() %2 == 0){
            helper.setText(R.id.tv_item,"已上架");
            helper.setText(R.id.tv_up_down,"下架");
        } else {
            helper.setText(R.id.tv_item,"已下架");
            helper.setText(R.id.tv_up_down,"上架");
        }

        helper.setText(R.id.tv_sales_num,"已售：23");
        helper.setText(R.id.tv_teacher_price,"¥233");

        helper.addOnClickListener(R.id.tv_up_down);
        helper.addOnClickListener(R.id.tv_edit);
        helper.addOnClickListener(R.id.rl_jump);


    }
}
