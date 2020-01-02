package com.noplugins.keepfit.coachplatform.adapter;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        helper.setText(R.id.pingfen_tv,item.getFinalGradle()+"分");


//        helper.setText(R.id.tv_type, "【" + item.getMessageTitle() + "】");
//        helper.setText(R.id.tv_message_date, item.getMessageTime());
//        helper.setText(R.id.tv_content, item.getMessageCon());
//        if (item.getDeleted() == 1) {
//            helper.getView(R.id.iv_red_point).setVisibility(View.VISIBLE);
//        } else {
//            helper.getView(R.id.iv_red_point).setVisibility(View.GONE);
//        }
//        helper.addOnClickListener(R.id.ll_item)
//                .addOnClickListener(R.id.tv_look);
    }
}
