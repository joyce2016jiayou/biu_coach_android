package com.noplugins.keepfit.coachplatform.adapter;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.manager.ManagerTeamBean;

import java.util.List;

public class ManagerTeamClassAdapter extends BaseQuickAdapter<ManagerTeamBean, BaseViewHolder> {
    public ManagerTeamClassAdapter(@Nullable List<ManagerTeamBean> data) {
        super(R.layout.item_manager_team, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ManagerTeamBean item) {
        helper.setText(R.id.tv_time_day, "2019-10-11");
        helper.setText(R.id.tv_time_week, "周三");
        helper.setText(R.id.tv_time_hour, "8:10-9:30");

        helper.setText(R.id.tv_team_name, "共享单车·单车Style");
        helper.setText(R.id.tv_team_room, "动感单车房");

        //已上架

        if (helper.getLayoutPosition() % 2 == 0) {
            helper.setText(R.id.tv_team_time, "单次");
            helper.setText(R.id.tv_team_man, "15人");
            helper.setText(R.id.tv_team_date, "45min");

            helper.getView(R.id.tv_team_tips).setVisibility(View.INVISIBLE);
        } else {
            helper.setText(R.id.tv_team_time, "第二周");
            helper.setText(R.id.tv_team_tips, "循环4周：每周2");
            helper.getView(R.id.tv_team_man).setVisibility(View.INVISIBLE);
            helper.getView(R.id.tv_team_date).setVisibility(View.GONE);
        }
        if (item.getType() == 1) {//已上架
            helper.setText(R.id.tv_cg_name, "嘻嘻健身");
            helper.setText(R.id.tv_team_price, "¥233");
            helper.getView(R.id.tv_team_date_history).setVisibility(View.GONE);
            helper.getView(R.id.tv_team_price).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_cg_name).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_yaoqin).setVisibility(View.GONE);
            helper.getView(R.id.tv_agin).setVisibility(View.GONE);
        } else if (item.getType() == 2){//邀请中
            helper.setText(R.id.tv_item, "邀请中");
            helper.getView(R.id.ll_yaoqin).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_cg_name).setVisibility(View.GONE);
            helper.getView(R.id.tv_team_date_history).setVisibility(View.GONE);
            helper.getView(R.id.tv_team_price).setVisibility(View.GONE);
        } else if (item.getType() == 3) {//历史
            helper.setText(R.id.tv_item, "邀请失败");
            helper.setText(R.id.tv_team_date_history, "45min");
            helper.getView(R.id.tv_team_date_history).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_team_price).setVisibility(View.GONE);
            helper.getView(R.id.tv_agin).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_team_date).setVisibility(View.GONE);
            helper.getView(R.id.tv_cg_name).setVisibility(View.GONE);
            helper.getView(R.id.ll_yaoqin).setVisibility(View.GONE);
        }


        helper.addOnClickListener(R.id.tv_agin);
        helper.addOnClickListener(R.id.tv_jujue);
        helper.addOnClickListener(R.id.tv_jieshou);
        helper.addOnClickListener(R.id.rl_jump);

    }
}
