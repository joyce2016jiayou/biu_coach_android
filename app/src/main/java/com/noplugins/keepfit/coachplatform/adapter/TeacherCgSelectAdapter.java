package com.noplugins.keepfit.coachplatform.adapter;

import android.widget.CheckBox;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.manager.CgListBean;

import java.util.List;

public class TeacherCgSelectAdapter extends BaseQuickAdapter<CgListBean.AreaListBean, BaseViewHolder> {
    public TeacherCgSelectAdapter(@Nullable List<CgListBean.AreaListBean> data) {
        super(R.layout.item_shouquan_cg,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CgListBean.AreaListBean item) {
        Glide.with(mContext)
                .load(item.getLogo())
                .transform(new RoundedCorners(20))
                .into((ImageView) helper.getView(R.id.iv_cg_logo));
        helper.addOnClickListener(R.id.rl_detail)
                .addOnClickListener(R.id.ck_select);
        ((CheckBox)helper.getView(R.id.ck_select) ).setChecked(false);
        helper.setText(R.id.tv_cg_name,item.getAreaName());
        helper.setText(R.id.tv_cg_ar,"面积:"+item.getArea()+"㎡");
        helper.setText(R.id.tv_juli,item.getDistance()+"km");
        helper.setText(R.id.tv_address,item.getAddress());
    }
}
