package com.noplugins.keepfit.coachplatform.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.BankCardBean;
import com.noplugins.keepfit.coachplatform.util.HideDataUtil;

import java.util.List;

public class CardAdapter extends BaseQuickAdapter<BankCardBean, BaseViewHolder> {
    public CardAdapter(@Nullable List<BankCardBean> data) {
        super(R.layout.item_card,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BankCardBean item) {
        helper.addOnClickListener(R.id.ll_item)
        .addOnClickListener(R.id.cb_select);
        helper.setText(R.id.tv_bank_name,item.getBankCardName());
        helper.setText(R.id.tv_card_type,"储蓄卡");
        helper.setText(R.id.tv_card_number, HideDataUtil.hideCardNo(item.getBankCardNum()));

    }
}
