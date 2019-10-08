package com.noplugins.keepfit.coachplatform.adapter;

        import android.view.View;
        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import com.chad.library.adapter.base.BaseQuickAdapter;
        import com.chad.library.adapter.base.BaseViewHolder;
        import com.noplugins.keepfit.coachplatform.R;

        import java.util.List;

public class MessageQianbaoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MessageQianbaoAdapter(@Nullable List<String> data) {
        super(R.layout.item_message_qianbao, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_type, "");

        helper.setText(R.id.tv_message_date, "");

        helper.getView(R.id.ll_tx).setVisibility(View.VISIBLE);
        helper.setText(R.id.tv_txje, "");
        helper.setText(R.id.tv_txzh, "");
        helper.setText(R.id.tv_txsj, "");
        helper.setText(R.id.tv_dqzt, "");

        helper.getView(R.id.ll_qb).setVisibility(View.GONE);
        helper.setText(R.id.tv_rzje, "");
        helper.setText(R.id.tv_rzzh, "");
        helper.setText(R.id.tv_rzsj, "");
        helper.setText(R.id.tv_rzkc, "");
        helper.addOnClickListener(R.id.ll_item)
                .addOnClickListener(R.id.tv_look);
    }
}
