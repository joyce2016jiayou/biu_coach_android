package com.noplugins.keepfit.coachplatform.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.manager.CgListBean;

import java.util.List;

public class ShoukeCgAdapter extends BaseQuickAdapter<CgListBean.AreaListBean, BaseViewHolder> {
    public ShoukeCgAdapter(@Nullable List<CgListBean.AreaListBean> data) {
        super(R.layout.item_shouke_cg,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CgListBean.AreaListBean item) {
        
    }
}
