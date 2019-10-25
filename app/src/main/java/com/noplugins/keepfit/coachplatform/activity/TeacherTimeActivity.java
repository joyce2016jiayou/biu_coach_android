package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.huantansheng.easyphotos.models.puzzle.Line;
import com.noplugins.keepfit.coachplatform.MainActivity;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.adapter.AddTimeAdapter;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.bean.BusyTimeBean;
import com.noplugins.keepfit.coachplatform.bean.LoginBean;
import com.noplugins.keepfit.coachplatform.bean.ReturnTimeBean;
import com.noplugins.keepfit.coachplatform.bean.SelectTimeBean;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.screen.KeyboardUtils;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import com.noplugins.keepfit.coachplatform.util.ui.switchbutton.ToogleButton;
import com.umeng.socialize.media.Base;
import com.ycuwq.datepicker.time.HourAndMinDialogFragment;
import rx.Subscription;

import java.util.*;

public class TeacherTimeActivity extends BaseActivity {

    @BindView(R.id.switch_button)
    ToogleButton switch_button;
    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.select_time_layout)
    LinearLayout select_time_layout;
    @BindView(R.id.select_start_time)
    TextView select_start_time;
    @BindView(R.id.select_end_time)
    TextView select_end_time;
    @BindView(R.id.recycler_time)
    RecyclerView recycler_time;
    @BindView(R.id.add_time_btn)
    LinearLayout add_time_btn;
    @BindView(R.id.select_date_tv)
    TextView select_date_tv;
    @BindView(R.id.shouke_tv)
    TextView shouke_tv;
    @BindView(R.id.select_date_bg)
    LinearLayout select_date_bg;
    @BindView(R.id.save_btn)
    LinearLayout save_btn;

    private boolean is_open_switch = true;
    private List<ReturnTimeBean.RestTimeBean.DataBean> strings = new ArrayList<>();
    private TimePickerView pvCustomTime;
    private String select_time_str = "";
    private AddTimeAdapter addTimeAdapter;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TeacherTimeActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_time.setLayoutManager(linearLayoutManager);


        add_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (select_date_tv.getText().equals("Y/M/D") && select_start_time.getText().equals("时间") && select_end_time.getText().equals("时间")) {
                    Toast.makeText(TeacherTimeActivity.this, "请设置时间！", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ReturnTimeBean.RestTimeBean.DataBean restTimeBean = new ReturnTimeBean.RestTimeBean.DataBean();
                    if (select_date_tv.getText().equals("Y/M/D")) {
                        restTimeBean.setBegDate(null);
                    } else {
                        restTimeBean.setBegDate(select_time_str);
                    }
                    restTimeBean.setBegTime(select_start_time.getText().toString() + ":00");
                    restTimeBean.setEndTime(select_end_time.getText().toString() + ":00");
                    strings.add(restTimeBean);
                    if (null != addTimeAdapter) {
                        addTimeAdapter.notifyDataSetChanged();
                    } else {
                        addTimeAdapter = new AddTimeAdapter(strings, TeacherTimeActivity.this);
                        recycler_time.setAdapter(addTimeAdapter);
                    }
                }
            }
        });

        switch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_open_switch) {
                    dismiss_pop_window(false);
                } else {
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
        select_time_layout.setOnClickListener(new View.OnClickListener() {
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
                        select_start_time.setText(startHour_str + ":" + startMinute_str);
                        select_end_time.setText(endHour_str + ":" + endMinute_str);
                    }
                });
                hourAndMinDialogFragment.show(getSupportFragmentManager(), "HourAndMinDialogFragment");
            }
        });

        select_date_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_time_pop();

            }
        });

        /**设置授课时间段*/
        set_shouke_time();

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_time();
            }
        });

    }

    private void save_time() {
        Map<String, Object> params = new HashMap<>();
        params.put("teacherNum", SpUtils.getString(getApplicationContext(), AppConstants.SELECT_TEACHER_NUMBER));
        params.put("restTime", strings);
        Subscription subscription = Network.getInstance("设置忙碌时间", this)
                .save_time(params,
                        new ProgressSubscriber<>("设置忙碌时间", new SubscriberOnNextListener<Bean<String>>() {
                            @Override
                            public void onNext(Bean<String> result) {

                            }

                            @Override
                            public void onError(String error) {
                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();

                            }
                        }, this, false));
    }

    /**
     * 选择时间pop
     */
    private void select_time_pop() {
        /**
         * @description 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(TeacherTimeActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Log.e("选择的时间", date.toString());
                int select_year = date.getYear() + 1900;
                String select_month = date.getMonth() + "";
                if (Integer.valueOf(select_month) < 9) {
                    select_month = "0" + (date.getMonth() + 1);
                } else {
                    select_month = "" + (date.getMonth() + 1);
                }
                String select_date = date.getDate() + "";
                if (Integer.valueOf(select_date) < 9) {
                    select_date = "0" + (date.getDate());
                } else {
                    select_date = "" + (date.getDate());
                }
                select_date_tv.setText(select_year + "/" + select_month + "/" + select_date);
                select_time_str = select_year + "-" + select_month + "-" + select_date;
            }
        })
                /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
                /*.animGravity(Gravity.RIGHT)// default is center*/
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time2, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        TextView quxiao_btn = (TextView) v.findViewById(R.id.quxiao_btn);
                        TextView sure_btn = (TextView) v.findViewById(R.id.sure_btn);
                        sure_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        quxiao_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(20)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("  年", "  月", "  日", "  时", "  分", "  秒")
                .setLineSpacingMultiplier(1.5f)
                .setTextXOffset(0, 0, 0, 60, 0, -60)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.parseColor("#00000000"))
                .build();
        pvCustomTime.show();


    }

    private void set_shouke_time() {
        Map<String, Object> params = new HashMap<>();
        params.put("teacherNum", SpUtils.getString(getApplicationContext(), AppConstants.SELECT_TEACHER_NUMBER));
        //params.put("restDate", "");
        params.put("begTime", "07:00:00");
        params.put("endTime", "23:00:00");
        Subscription subscription = Network.getInstance("设置授课时间", this)
                .set_shouke_time(params,
                        new ProgressSubscriber<>("设置授课时间", new SubscriberOnNextListener<Bean<String>>() {
                            @Override
                            public void onNext(Bean<String> result) {
                                get_shouke_time();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));
    }

    private void get_shouke_time() {
        Map<String, Object> params = new HashMap<>();
        params.put("teacherNum", SpUtils.getString(getApplicationContext(), AppConstants.SELECT_TEACHER_NUMBER));
        Subscription subscription = Network.getInstance("获取全部时间", this)
                .get_all_time(params,
                        new ProgressSubscriber<>("获取全部时间", new SubscriberOnNextListener<Bean<ReturnTimeBean>>() {
                            @Override
                            public void onNext(Bean<ReturnTimeBean> result) {
                                String begin_time = result.getData().getWorkTime().getData().getBegTime();

                                String end_time = result.getData().getWorkTime().getData().getEndTime();
                                shouke_tv.setText(begin_time.substring(0, begin_time.length() - 3) + "-" + end_time.substring(0, end_time.length() - 3));
                                strings.addAll(result.getData().getRestTime().getData());
                                addTimeAdapter = new AddTimeAdapter(strings, TeacherTimeActivity.this);
                                recycler_time.setAdapter(addTimeAdapter);

                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));
    }

    private void close_class_time() {
        Map<String, Object> params = new HashMap<>();
        params.put("teacherNum", SpUtils.getString(getApplicationContext(), AppConstants.SELECT_TEACHER_NUMBER));
        Subscription subscription = Network.getInstance("关闭授课时间", this)
                .close_shouke_time(params,
                        new ProgressSubscriber<>("关闭授课时间", new SubscriberOnNextListener<Bean<String>>() {
                            @Override
                            public void onNext(Bean<String> result) {
                                Toast.makeText(getApplicationContext(), "关闭授课时间成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, true));
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
        if (is_open_shouke) {
            tv_title.setText(R.string.tv93);
            tv_content.setText(R.string.tv94);
        } else {
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
                    is_open_switch = true;


                } else {
                    switch_button.toogleOff();
                    is_open_switch = false;
                    close_class_time();

                }


            }
        });
    }
}
