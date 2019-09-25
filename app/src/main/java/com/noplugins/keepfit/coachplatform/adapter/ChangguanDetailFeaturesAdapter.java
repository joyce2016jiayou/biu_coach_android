package com.noplugins.keepfit.coachplatform.adapter;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;

import java.util.ArrayList;

public class ChangguanDetailFeaturesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ChangguanDetailFeaturesAdapter(@Nullable ArrayList<String> data) {
        super(R.layout.item_find_changguan_features, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

        ((TextView) helper.getView(R.id.tv_tag)).setText(item);

        helper.addOnClickListener(R.id.tv_tag);
//        Glide.with(context).load(item.getAreaLogo()).into((ImageView)helper.getView(R.id.iv_coupon_logo));
    }

}
