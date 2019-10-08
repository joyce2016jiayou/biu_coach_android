package com.noplugins.keepfit.coachplatform.adapter;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.MessageBean;

import java.util.List;

public class MessageAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {
    public MessageAdapter(@Nullable List<MessageBean> data) {
        super(R.layout.item_message,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageBean item) {
        helper.setText(R.id.tv_type,"【"+item.getMessageTitle()+"】");
        helper.setText(R.id.tv_message_date,item.getMessageTime());
        helper.setText(R.id.tv_content,item.getMessageCon());
        if (item.getDeleted() == 1){
            helper.getView(R.id.iv_red_point).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.iv_red_point).setVisibility(View.GONE);
        }
        helper.addOnClickListener(R.id.ll_item)
                .addOnClickListener(R.id.tv_look);
    }
}
