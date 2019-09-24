package com.noplugins.keepfit.coachplatform.adapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.BillDetailBean;

import java.util.List;

public class BillDetailAdapter extends BaseQuickAdapter<BillDetailBean, BaseViewHolder> {
    public BillDetailAdapter(@Nullable List<BillDetailBean> data) {
        super(R.layout.item_bill_detail,data);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void convert(@NonNull BaseViewHolder helper, BillDetailBean item) {
        helper.setText(R.id.tv_service_type,"");
        helper.setText(R.id.tv_time,"");
        if (helper.getLayoutPosition()%2 == 0){
            helper.setText(R.id.tv_money,"");
            ((TextView)helper.getView(R.id.tv_money)).setTextColor(R.color.color_6DD400);
            helper.getView(R.id.tv_tips).setVisibility(View.GONE);
        } else {
            helper.setText(R.id.tv_money,"");
            ((TextView)helper.getView(R.id.tv_money)).setTextColor(R.color.color_F5502F);
            helper.getView(R.id.tv_tips).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_tips,"");
        }

        helper.addOnClickListener(R.id.ll_item);


    }
}
