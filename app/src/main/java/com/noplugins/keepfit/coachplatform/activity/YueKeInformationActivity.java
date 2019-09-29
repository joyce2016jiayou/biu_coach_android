package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.huantansheng.easyphotos.models.puzzle.Line;
import com.noplugins.keepfit.coachplatform.MainActivity;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.bean.LoginBean;
import com.noplugins.keepfit.coachplatform.bean.SelectDateBean;
import com.noplugins.keepfit.coachplatform.bean.YueKeBean;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.data.DateUtils;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.screen.KeyboardUtils;
import com.noplugins.keepfit.coachplatform.util.ui.courcetable.CourseModel;
import com.noplugins.keepfit.coachplatform.util.ui.courcetable.CourseTableLayoutView;
import com.noplugins.keepfit.coachplatform.util.ui.courcetable.TopDateEntity;
import rx.Subscription;

import java.util.*;

public class YueKeInformationActivity extends BaseActivity {
    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.select_month_btn)
    LinearLayout select_month_btn;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.xiangqing_btn)
    TextView xiangqing_btn;
    @BindView(R.id.yiyuyue_btn)
    LinearLayout yiyuyue_btn;
    @BindView(R.id.yijieshu_btn)
    LinearLayout yijieshu_btn;
    @BindView(R.id.layout_course)
    CourseTableLayoutView mCourseTableTestLayout;

    private TimePickerView pvCustomTime;
    private List<CourseModel> mList = new ArrayList<>();
    private String current_select_year = "";
    private String current_select_month = "";
    private List<YueKeBean.CourseListBean> courseListBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_yue_ke_information);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //选择时间
        select_month_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_time_pop();
            }
        });
        //获取当前的年份跟月份
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        current_select_year = String.valueOf(c.get(Calendar.YEAR));
        current_select_month = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        if (Integer.valueOf(current_select_month) <= 9) {
            time_tv.setText(current_select_year + "年0" + current_select_month + "月");
        } else {
            time_tv.setText(current_select_year + "年" + current_select_month + "月");
        }
        init_class_reource();


    }

    private void init_class_reource() {
        Map<String, Object> params = new HashMap<>();
//        params.put("teacherNum", SpUtils.getString(getApplicationContext(), AppConstants.SELECT_TEACHER_NUMBER));
        if (Integer.valueOf(current_select_month) <= 9) {
            params.put("date", current_select_year + "-0" + Integer.valueOf(current_select_month));
        } else {
            params.put("date", current_select_year + "-" + Integer.valueOf(current_select_month));
        }
        params.put("teacherNum", "GEN23456");

        Subscription subscription = Network.getInstance("约课信息", this)
                .yuekeInformation(params,
                        new ProgressSubscriber<>("约课信息", new SubscriberOnNextListener<Bean<YueKeBean>>() {
                            @Override
                            public void onNext(Bean<YueKeBean> result) {
                                courseListBeans.addAll(result.getData().getCourseList());
                                init_class_date(current_select_year, current_select_month, true);
                            }

                            @Override
                            public void onError(String error) {
//                                for(int i=0;i<2;i++){
//                                    YueKeBean.CourseListBean courseListBean = new YueKeBean.CourseListBean();
//                                    if(i==0){
//                                        courseListBean.setStartTimeStamp(1568973600000L);
//                                    }else{
//                                        courseListBean.setStartTimeStamp(1568912400000L);
//                                    }
//                                    courseListBeans.add(courseListBean);
//                                }
//                                init_class_date(current_select_year, current_select_month, true);

                            }
                        }, this, true));
    }


    private void init_class_date(String select_year, String select_month, boolean is_current_month) {
        //设置左边的时间刻度
        init_left_time();

        //设置上方的日期
        select_month = Integer.valueOf(select_month) - 1 + "";
        init_top_dates(select_year, select_month, is_current_month);

        //设置课程布局
        initCourceTable();
    }


    private void initCourceTable() {
        mCourseTableTestLayout.set_click_item_listen(new CourseTableLayoutView.Click_item() {
            @Override
            public void onClickCourse(View view, YueKeBean.CourseListBean course, int dataPosition, int dayPosition, int timePosition) {
                Log.e("选择了", dataPosition + "");
                if (dataPosition == 0) {
                    Intent intent = new Intent(YueKeInformationActivity.this, ClassDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("is_qiandao", "1");
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else if (dataPosition == 1) {
                    Intent intent = new Intent(YueKeInformationActivity.this, ClassDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("is_qiandao", "2");
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(YueKeInformationActivity.this, ClassDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("is_qiandao", "3");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });


        /*for (int i = 0; i < 2; i++) {//根据上方的日期排序
            CourseModel model = new CourseModel();
            model.setWeek((i + 1));
            model.setName("课程名字" + (i + 1));
            model.setStart((i + 1));
            model.setStep((i + 2));
            model.setClass_status(2);
            if (i == 0) {
                model.setTime_left(10);
                model.setDate_top("9/28");
            } else {
                model.setTime_left(8);
                model.setDate_top("9/30");
            }
            mList.add(model);
        }*/

        mCourseTableTestLayout.setData(courseListBeans);//设置课程布局


    }

    private void init_left_time() {
        List<String> strings = new ArrayList<>();
        for (int i = 6; i < 23; i++) {
            strings.add((i + 1) + ":00");
        }
        mCourseTableTestLayout.setCourseTimeLabels(strings);//设置左边的时间刻度
    }

    private void init_top_dates(String select_year, String select_month, boolean is_current_month) {
        List<SelectDateBean> selectDateBeans = new ArrayList<>(DateUtils.getDayByMonth(Integer.valueOf(select_year), Integer.valueOf(select_month)));
        mCourseTableTestLayout.setTopDateWeeks(selectDateBeans, is_current_month);//设置上面的日期刻度
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            init_class_reource();

        }
    };

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
        pvCustomTime = new TimePickerBuilder(YueKeInformationActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                Log.e("选择的时间", date.toString());
                int select_year = date.getYear() + 1900;
                String select_month = date.getMonth() + "";
                if (Integer.valueOf(select_month) < 9) {
                    select_month = "0" + (Integer.valueOf(select_month) + 1);
                } else {
                    select_month = "" + (Integer.valueOf(select_month) + 1);
                }
                current_select_month = select_month;
                current_select_year = select_year + "";
                Log.e("选择的时间参数打印", current_select_year + "-" + current_select_month);
                time_tv.setText(select_year + "年" + select_month + "月");
                pvCustomTime.dismiss();

//                Thread thread = new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//
//                    }
//                };
//                thread.start();

                handler.sendEmptyMessage(0);



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
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

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
                .setType(new boolean[]{true, true, false, false, false, false})
                .setLabel("  年", "  月", "  日", "  时", "  分", "  秒")
                .setLineSpacingMultiplier(1.5f)
                .setTextXOffset(0, 0, 0, 60, 0, -60)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.parseColor("#00000000"))
                .build();
        pvCustomTime.show();
    }

}
