package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.util.screen.KeyboardUtils;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import com.noplugins.keepfit.coachplatform.util.ui.switchbutton.ToogleButton;
import com.umeng.socialize.media.Base;

import java.util.Calendar;
import java.util.Date;

public class TeacherTimeActivity extends BaseActivity {

    @BindView(R.id.switch_button)
    ToogleButton switch_button;
    TimePickerView pvCustomTime;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_teacher_time);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        switch_button.setOnCheckListener(new ToogleButton.OnCheckListener() {
            @Override
            public void onCheck(boolean isCheck) {
                if (isCheck) {

                } else {
                    dismiss_pop_window();
                }
            }
        });
        switch_button.toogleOn();
    }

    private void dismiss_pop_window() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(TeacherTimeActivity.this)
                .setView(R.layout.call_pop)
                .setBackGroundLevel(0.5f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(switch_button);

        /**设置逻辑*/
        View view = popupWindow.getContentView();
        LinearLayout cancel_layout = view.findViewById(R.id.cancel_layout);
        LinearLayout sure_layout = view.findViewById(R.id.sure_layout);

        cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                switch_button.toogleOn();


            }
        });
        sure_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                switch_button.toogleOff();

            }
        });
    }
}
