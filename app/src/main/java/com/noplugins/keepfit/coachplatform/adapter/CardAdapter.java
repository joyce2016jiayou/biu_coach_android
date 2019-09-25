package com.noplugins.keepfit.coachplatform.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;

import java.util.List;

public class CardAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CardAdapter(@Nullable List<String> data) {
        super(R.layout.item_card,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.ll_item)
        .addOnClickListener(R.id.cb_select);
    }
}
