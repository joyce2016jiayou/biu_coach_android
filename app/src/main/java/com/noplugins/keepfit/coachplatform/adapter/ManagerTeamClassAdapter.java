package com.noplugins.keepfit.coachplatform.adapter;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.noplugins.keepfit.coachplatform.R;

import java.util.List;

public class ManagerTeamClassAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ManagerTeamClassAdapter(@Nullable List<String> data) {
        super(R.layout.item_manager_team,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_time_day,"2019-10-11");
        helper.setText(R.id.tv_time_week,"周三");
        helper.setText(R.id.tv_time_hour,"8:10-9:30");
        helper.setText(R.id.tv_cg_name,"嘻嘻健身");
        helper.setText(R.id.tv_team_name,"共享单车·单车Style");
        helper.setText(R.id.tv_team_room,"动感单车房");
        helper.setText(R.id.tv_team_price,"¥233");
        //已上架
        if (helper.getLayoutPosition() %2 == 0){
            helper.setText(R.id.tv_team_time,"单次");
            helper.setText(R.id.tv_team_man,"15人");
            helper.setText(R.id.tv_team_date,"45min");

            helper.getView(R.id.tv_team_tips).setVisibility(View.INVISIBLE);
        } else {
            helper.setText(R.id.tv_team_time,"第二周");
            helper.setText(R.id.tv_team_tips,"循环4周：每周2");
            helper.getView(R.id.tv_team_man).setVisibility(View.INVISIBLE);
            helper.getView(R.id.tv_team_date).setVisibility(View.GONE);

            //邀请中

            if (helper.getLayoutPosition() == 3){
                helper.setText(R.id.tv_item,"邀请中");
                helper.getView(R.id.ll_yaoqin).setVisibility(View.VISIBLE);
            }
            //历史
            else  if (helper.getLayoutPosition() == 5){
                helper.setText(R.id.tv_item,"邀请失败");
                helper.getView(R.id.tv_agin).setVisibility(View.VISIBLE);
                helper.getView(R.id.tv_team_date).setVisibility(View.GONE);
            }
        }





        helper.addOnClickListener(R.id.tv_agin);
        helper.addOnClickListener(R.id.tv_jujue);
        helper.addOnClickListener(R.id.tv_jieshou);
        helper.addOnClickListener(R.id.rl_jump);

    }
}
