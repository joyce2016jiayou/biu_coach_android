package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.huantansheng.easyphotos.models.puzzle.Line;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.util.screen.KeyboardUtils;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import com.noplugins.keepfit.coachplatform.util.ui.switchbutton.ToogleButton;
import com.umeng.socialize.media.Base;
import com.ycuwq.datepicker.time.HourAndMinDialogFragment;

import java.util.Calendar;
import java.util.Date;

public class TeacherTimeActivity extends BaseActivity {

    @BindView(R.id.switch_button)
    ToogleButton switch_button;
    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.select_time_btn)
    LinearLayout select_time_btn;
    @BindView(R.id.start_time_tv)
    TextView start_time_tv;
    @BindView(R.id.end_time_tv)
    TextView end_time_tv;

    private boolean is_open_switch = true;

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
        switch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_open_switch) {
                    dismiss_pop_window(false);
                }else{
                    dismiss_pop_window(true);
                }
            }
        });
        switch_button.toogleOn();

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        select_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HourAndMinDialogFragment hourAndMinDialogFragment = new HourAndMinDialogFragment();
                hourAndMinDialogFragment.setOnDateChooseListener(new HourAndMinDialogFragment.OnDateChooseListener() {
                    @Override
                    public void onDateChoose(int startHour, int startMinute, int endHour, int endMinute) {
                        String startHour_str = "";
                        if (startHour <= 9) {
                            startHour_str = "0" + startHour;
                        }
                        String startMinute_str = "";
                        if (startMinute <= 9) {
                            startMinute_str = "0" + startMinute;
                        }
                        String endHour_str = "";
                        if (endHour <= 9) {
                            endHour_str = "0" + endHour;
                        }
                        String endMinute_str = "";
                        if (endMinute <= 9) {
                            endMinute_str = "0" + endMinute;
                        }
                        start_time_tv.setText(startHour_str + ":" + startMinute_str);
                        end_time_tv.setText(endHour_str + ":" + endMinute_str);
                    }
                });
                hourAndMinDialogFragment.show(getSupportFragmentManager(), "HourAndMinDialogFragment");
            }
        });
    }

    private void dismiss_pop_window(boolean is_open_shouke) {
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
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);
        if(is_open_shouke){
            tv_title.setText(R.string.tv93);
            tv_content.setText(R.string.tv94);
        }else{

            tv_title.setText(R.string.tv90);
            tv_content.setText(R.string.tv92);


        }
        cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        sure_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                if (is_open_shouke) {
                    switch_button.toogleOn();
                    is_open_switch= true;

                } else {
                    switch_button.toogleOff();
                    is_open_switch = false;

                }


            }
        });
    }
}
