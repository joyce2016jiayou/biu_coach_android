package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
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
import com.noplugins.keepfit.coachplatform.util.ui.courcetable.CourseModel;
import com.noplugins.keepfit.coachplatform.util.ui.courcetable.CourseTableLayoutView;
import com.noplugins.keepfit.coachplatform.util.ui.courcetable.TopDateEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    TimePickerView pvCustomTime;
    List<CourseModel> mList;


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

        initCourceTable();

    }

    private void initCourceTable() {
        mList = getData();
        mCourseTableTestLayout.set_click_item_listen(new CourseTableLayoutView.Click_item() {
            @Override
            public void onClickCourse(View view, CourseModel course, int dataPosition, int dayPosition, int timePosition) {
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

        mCourseTableTestLayout.setData(mList);//设置课程布局

        mCourseTableTestLayout.setCourseTimeLabels(getTimeLabels());//设置左边的时间刻度

        mCourseTableTestLayout.setTopDateWeeks(getDateWeeks());//设置上面的日期刻度
    }

    private List<String> getTimeLabels() {
        List<String> strings = new ArrayList<>();
        for (int i = 6; i < 23; i++) {
            strings.add((i + 1) + ":00");
        }
        return strings;
    }

    private List<TopDateEntity> getDateWeeks() {
        List<TopDateEntity> topDateEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TopDateEntity topDateEntity = new TopDateEntity();
            topDateEntity.setDate_str("07/" + i);
            topDateEntity.setWeek_str("星期" + i);
            topDateEntities.add(topDateEntity);
        }
        return topDateEntities;
    }

    private CourseModel getCourseModel(int week, String name, int start, int step, int status) {
        CourseModel model = new CourseModel();
        model.setWeek(week);//日期的position对应
        model.setName(name);
        model.setStart(start);//开始绘制的高度
        model.setStep(step);
        model.setClass_status(status);
        return model;
    }

    private List<CourseModel> getData() {
        mList = new ArrayList<>();
        mList.add(getCourseModel(1, "数学", 1, 5, 1));
        mList.add(getCourseModel(1, "语文", 4, 2, 1));

        mList.add(getCourseModel(2, "生物", 2, 2, 2));
        mList.add(getCourseModel(2, "地理", 5, 3, 2));

        mList.add(getCourseModel(3, "化学", 3, 1, 1));
        mList.add(getCourseModel(3, "物理", 5, 1, 2));

        mList.add(getCourseModel(4, "数学", 4, 1, 2));
        mList.add(getCourseModel(4, "数学", 6, 2, 1));
        return mList;
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
        pvCustomTime = new TimePickerBuilder(YueKeInformationActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Log.e("选择的时间", date.toString());
                int select_year = date.getYear() + 1900;
                String select_month = date.getMonth() + "";
                if (Integer.valueOf(select_month) <= 9) {
                    select_month = "0" + (date.getMonth() + 1);
                } else {
                    select_month = "" + (date.getMonth() + 1);
                }
                time_tv.setText(select_year + "年" + select_month + "月");
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
