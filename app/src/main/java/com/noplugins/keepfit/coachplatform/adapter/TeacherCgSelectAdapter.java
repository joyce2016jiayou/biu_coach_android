package com.noplugins.keepfit.coachplatform.adapter;

import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.util.GlideRoundTransform;

import java.util.List;

public class TeacherCgSelectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TeacherCgSelectAdapter(@Nullable List<String> data) {
        super(R.layout.item_shouquan_cg,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        Glide.with(mContext)
                .load("https://goss.veer.com/creative/vcg/veer/800water/veer-130614654.jpg")
                .transform(new CenterCrop(mContext),new GlideRoundTransform(mContext,8))
                .into((ImageView) helper.getView(R.id.iv_cg_logo));
        helper.addOnClickListener(R.id.rl_detail)
                .addOnClickListener(R.id.ck_select);
        helper.setText(R.id.tv_cg_name,"12345场馆");
        helper.setText(R.id.tv_cg_ar,"面积:"+777);
        helper.setText(R.id.tv_juli,"230"+"m");
        helper.setText(R.id.tv_address,"火星");
    }
}
