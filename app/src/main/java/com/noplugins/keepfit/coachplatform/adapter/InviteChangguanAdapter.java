package com.noplugins.keepfit.coachplatform.adapter;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.manager.CgListBean;

import java.util.List;

public class InviteChangguanAdapter extends BaseQuickAdapter<CgListBean.AreaListBean, BaseViewHolder> {
    public InviteChangguanAdapter(@Nullable List<CgListBean.AreaListBean> data) {
        super(R.layout.item_team_info, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CgListBean.AreaListBean item) {
        helper.setText(R.id.pingfen_tv, item.getFinalGradle() + "分");
        Glide.with(mContext)
                .load(item.getLogo())
                .into((ImageView) helper.getView(R.id.iv_cg_logo));
        helper.setText(R.id.tv_cg_name, item.getAreaName());
        helper.setText(R.id.tv_juli, item.getDistance() + "");
        helper.setText(R.id.tv_address, item.getAddress());
    }
}
