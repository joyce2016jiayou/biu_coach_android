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

public class TeamDetail9ImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TeamDetail9ImgAdapter(@Nullable List<String> data) {
        super(R.layout.item_team_9img,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        Glide.with(mContext)
                .load(item)
                .transform(new CenterCrop(mContext),new GlideRoundTransform(mContext,8))
                .into((ImageView) helper.getView(R.id.iv_img));
    }
}
