package com.noplugins.keepfit.coachplatform.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;

import java.util.List;

public class TeamInfoVenueAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TeamInfoVenueAdapter(@Nullable List<String> data) {
        super(R.layout.item_team_info,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

    }
}
