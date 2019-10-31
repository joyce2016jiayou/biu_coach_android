package com.noplugins.keepfit.coachplatform.util.ui.courcetable;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.SelectDateBean;
import com.noplugins.keepfit.coachplatform.bean.YueKeBean;
import com.noplugins.keepfit.coachplatform.util.data.DateHelper;
import com.noplugins.keepfit.coachplatform.util.screen.ScreenUtilsHelper;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseTableLayoutView extends LinearLayout {
    protected Context mContext;
    private LinearLayout mRootLinearLayout;

    protected List<String> mTimeLabels = new ArrayList<>();
    protected List<SelectDateBean> top_dates = new ArrayList<>();
    /**
     * 列
     */
    protected int mDayCount = 5;
    /**
     * 行
     */
    protected int mTimeCount = 23;
    private int BG_COURSE[] = new int[]{R.drawable.bg_course_table_blue_selector, R.drawable.bg_course_table_green_selector};

    //***********宽高************
    protected int mWidth = 0;
    protected int mDayTableWidth = 0;
    protected int mDayTableHeight = 0;
    protected int mCourseTableHeight = 0;
    protected int mTimeTableWidth = 0;//默认为0，是为了用于判断是否可以自定义
    protected int mTimeTableHeight = mCourseTableHeight;
    protected int mCourseTableWidth = mDayTableWidth;
    //***************颜色**************
    protected int mCourseTextColor = Color.WHITE;
    //***************背景*****************
    protected int mEmptyTableBgRes = R.drawable.bg_course_table_empty_selector;
    protected int mDayTableBgRes = R.drawable.bg_day_table_shape;
    protected int mTimeTableBgRes = mDayTableBgRes;
    private List<YueKeBean.CourseListBean> mCourseList = new ArrayList<>();
    private boolean mIsFirst = true;
    private int mDataTempPosition = 0;
    LinearLayout cource_item_lin, cource_item_bg;
    ObservableScrollView horizontalScrollView, horizontalScrollView_top;
    RecyclerView recyclerView, time_reclerview;
    private boolean is_current_month;

    public CourseTableLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CourseTableLayoutView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        initView(context);
    }


    private void initView(Context context) {
        mRootLinearLayout = new LinearLayout(context);
        mRootLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mRootLinearLayout.setOrientation(LinearLayout.HORIZONTAL);


        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        //设置课程的布局
        View view = View.inflate(context, R.layout.cource_item, null);
        cource_item_lin = view.findViewById(R.id.cource_item_lin);
        cource_item_bg = view.findViewById(R.id.cource_item_bg);

        horizontalScrollView = view.findViewById(R.id.horizontalScrollView);
        horizontalScrollView.setScrollViewListener(scrollViewListener);
        horizontalScrollView_top = view.findViewById(R.id.horizontalScrollView_top);
        horizontalScrollView_top.setScrollViewListener(scrollViewListener);
        //设置右上角的日期
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        //设置左边的时间
        time_reclerview = view.findViewById(R.id.time_reclerview);
        time_reclerview.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        addView(mRootLinearLayout);
        mRootLinearLayout.addView(linearLayout);
        linearLayout.addView(view);
    }

    ObservableScrollView.ScrollViewListener scrollViewListener = new ObservableScrollView.ScrollViewListener() {
        @Override
        public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
            if (scrollView == horizontalScrollView_top) {
                horizontalScrollView.scrollTo(x, y);
            } else if (scrollView == horizontalScrollView) {
                horizontalScrollView_top.scrollTo(x, y);
            }
        }
    };


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int TimeTableWidth = ScreenUtilsHelper.dip2px(mContext, 55);//左边时间刻度的宽度
        mWidth = right - left - TimeTableWidth;
        int itemWidth;
        if (mTimeTableWidth > 0) {
            itemWidth = (mWidth - mTimeTableWidth) / mDayCount;
        } else {
            itemWidth = mWidth / mDayCount;
        }
        //日期的宽高
        mDayTableWidth = itemWidth;
        mDayTableHeight = ScreenUtilsHelper.dip2px(mContext, 79);

        //课程的宽高
        mCourseTableWidth = itemWidth;
        mCourseTableHeight = ScreenUtilsHelper.dip2px(mContext, 79);

        //左边时间刻度的宽高
        mTimeTableWidth = TimeTableWidth;
        mTimeTableHeight = mCourseTableHeight;

        if (mIsFirst) {
            notifyDataSetChanged();
            mIsFirst = false;
        }
    }


    /**
     * 设置左边的时间刻度
     *
     * @param courseTimeLabels
     */
    public void setCourseTimeLabels(List<String> courseTimeLabels) {
        mTimeLabels = courseTimeLabels;
    }

    /**
     * 设置上方的日期刻度
     *
     * @param courseTimeLabels
     */
    public void setTopDateWeeks(List<SelectDateBean> courseTimeLabels, boolean m_is_current_month) {
        top_dates = courseTimeLabels;
        is_current_month = m_is_current_month;

    }


    /**
     * 设置课程资源数据
     *
     * @param list
     */
    public void setData(List<YueKeBean.CourseListBean> list) {
        mCourseList.addAll(list);
        if (!mIsFirst) {
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        initDayLayout();
        cource_item_lin.removeAllViews();
        cource_item_bg.removeAllViews();
        initTimeLayout();
        initCourseLayout();
    }


    /**
     * 设置左边的时间刻度
     */
    private void initTimeLayout() {
        TimeLinLeftAdapter timeLinLeftAdapter = new TimeLinLeftAdapter(mContext, mTimeLabels, mTimeTableWidth);
        time_reclerview.setNestedScrollingEnabled(false);
        time_reclerview.setAdapter(timeLinLeftAdapter);
    }

    /**
     * 设置课程布局
     */
    private void initCourseLayout() {
        for (int day = 0; day < top_dates.size(); day++) {
            /**绘制课程*/
            LinearLayout oneDayLinearLayout = new LinearLayout(mContext);
            oneDayLinearLayout.setOrientation(LinearLayout.VERTICAL);
            oneDayLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(mCourseTableWidth,
                    mCourseTableHeight * (mTimeCount - 6)));//从7点开始
            /**绘制格子*/
            LinearLayout gezi_layout = new LinearLayout(mContext);
            gezi_layout.setOrientation(LinearLayout.VERTICAL);
            gezi_layout.setLayoutParams(new ViewGroup.LayoutParams(mCourseTableWidth,
                    mCourseTableHeight * (mTimeCount - 6)));//从7点开始

            for (int time = 0; time < mTimeCount; time++) {
                Log.e("打印出来的时间", mTimeCount + "");
                Log.e("打印出来的日期", top_dates.get(day).getMonth() + "/" + top_dates.get(day).getDate() + "");

                CourseFlag courseFlag = getCourse(day, time);

                if (!courseFlag.isContinuousCourse) {
                    View cource_view = get_cource_view();
                    View gezi_view = get_gezi_view();
                    if (courseFlag.course == null) {
                        //绘制格子
                        gezi_layout_bg.setBackgroundResource(mEmptyTableBgRes);//设置格子
                        gezi_view.setLayoutParams(new ViewGroup.LayoutParams(mDayTableWidth, mDayTableHeight));

                        //显示空白布局
                        time_tv.setVisibility(INVISIBLE);
                        status_tv.setVisibility(INVISIBLE);
                        cource_teacher.setVisibility(INVISIBLE);
                        class_name.setVisibility(INVISIBLE);
                        cource_view.setLayoutParams(new ViewGroup.LayoutParams(mDayTableWidth, mDayTableHeight));

                    } else {
                        //绘制格子
                        gezi_layout_bg.setBackgroundResource(mEmptyTableBgRes);//设置格子
                        gezi_view.setLayoutParams(new ViewGroup.LayoutParams(mDayTableWidth, mDayTableHeight));

                        //显示数据
                        time_tv.setVisibility(VISIBLE);
                        status_tv.setVisibility(VISIBLE);
                        cource_teacher.setVisibility(VISIBLE);
                        class_name.setVisibility(VISIBLE);
                        //设置高度
                        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mDayTableWidth, (mDayTableHeight * courseFlag.course.getStep()));
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mDayTableWidth, mDayTableHeight);
                        //根据分钟来判断移动的距离
                        int minute = DateHelper.get_minite(courseFlag.course.getStartTimeStamp());
                        DecimalFormat df = new DecimalFormat("0.00");//设置保留位数
                        Double aDouble = Double.valueOf(df.format((float) minute / 60));
                        //Log.e("分钟", aDouble * +mDayTableHeight + "");
                        int move_height = new Double(aDouble * +mDayTableHeight).intValue();
                        //Log.e("移动的距离", move_height + "");
                        layoutParams.setMargins(0, move_height, 0, 0);//设置格子距离上部的位置
                        cource_layout.setLayoutParams(layoutParams);

                        //设置数据
                        class_name.setText(courseFlag.course.getCourseName());
                        cource_teacher.setText(courseFlag.course.getCoachUserName());
                        status_tv.setText(courseFlag.course.getCourseStatus());
                        time_tv.setText(courseFlag.course.getCourseTime());
                        if (courseFlag.course.getCourseType() == 1) {//团课
                            cource_layout.setBackgroundResource(BG_COURSE[1]);
                        } else if (courseFlag.course.getCourseType() == 2) {//私教
                            cource_layout.setBackgroundResource(BG_COURSE[0]);
                        }
                        //cource_layout.setBackgroundResource(BG_COURSE[mDataTempPosition % BG_COURSE.length]);
                        //设置点击事件的监听
                        cource_layout.setOnClickListener(new OnClickCourseListener(courseFlag.course, mDataTempPosition, day, time));
                    }
                    gezi_layout.addView(gezi_view);
                    oneDayLinearLayout.addView(cource_view);
                }


            }
            cource_item_bg.addView(gezi_layout);//设置格子
            cource_item_lin.addView(oneDayLinearLayout);
        }
    }

    TextView time_tv, status_tv, cource_teacher, class_name;
    LinearLayout cource_layout, class_bg;

    private View get_cource_view() {
        View cource_view = View.inflate(mContext, R.layout.cource_item_view, null);
        time_tv = cource_view.findViewById(R.id.time_tv);
        status_tv = cource_view.findViewById(R.id.status_tv);
        cource_teacher = cource_view.findViewById(R.id.cource_teacher);
        class_name = cource_view.findViewById(R.id.class_name);
        cource_layout = cource_view.findViewById(R.id.cource_layout);
        class_bg = cource_view.findViewById(R.id.class_bg);
        return cource_view;
    }

    LinearLayout gezi_layout_bg;

    private View get_gezi_view() {
        View gezi_view = View.inflate(mContext, R.layout.gezi_view, null);
        gezi_layout_bg = gezi_view.findViewById(R.id.gezi_layout_bg);
        return gezi_view;
    }

    /**
     * 设置日期布局
     */
    private void initDayLayout() {
        ResourceAdapter resourceAdapter = new ResourceAdapter(mContext, top_dates, mDayTableWidth);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(resourceAdapter);
        //判断是不是当月，如果是，自动定位到今天
        if (is_current_month) {
            for (int i = 0; i < top_dates.size(); i++) {
                if (top_dates.get(i).getWeek_str().equals("今天")) {
                    int finalI = i + 1;
                    new Handler().postDelayed((new Runnable() {
                        @Override
                        public void run() {
                            horizontalScrollView_top.scrollTo(mDayTableWidth * finalI, 0);//定位到今天
                        }
                    }), 1);
                }
            }
        } else {
            new Handler().postDelayed((new Runnable() {
                @Override
                public void run() {
                    horizontalScrollView_top.scrollTo(0, 0);//定位到最初
                }
            }), 1);
        }

    }

    private CourseFlag getCourse(int day, int time) {
        int courseSize = getListSize(mCourseList);
        CourseFlag courseFlag = new CourseFlag();
        for (int i = 0; i < courseSize; i++) {
            YueKeBean.CourseListBean course = mCourseList.get(i);
            boolean[] find = compareToCourse(course, day, time);
            courseFlag.isContinuousCourse = find[1];
            if (find[0]) {
                mDataTempPosition = i;
                courseFlag.course = course;
                return courseFlag;
            }
        }
        return courseFlag;
    }

    private class CourseFlag {
        public YueKeBean.CourseListBean course = null;
        public boolean isContinuousCourse = false;
    }


    private class OnClickCourseListener implements OnClickListener {
        private YueKeBean.CourseListBean course;
        private int position;
        private int day;
        private int time;

        private OnClickCourseListener(YueKeBean.CourseListBean course, int position, int day, int time) {
            this.course = course;
            this.position = position;
            this.day = day;
            this.time = time;
        }

        @Override
        public void onClick(View view) {
            if (course == null) {
                onClickEmptyCourse(view, day, time);
            } else {
                click_item.onClickCourse(view, mCourseList.get(position), position, day, time);
            }
        }
    }


    private int getListSize(List<YueKeBean.CourseListBean> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }

    /**
     * 判断该课程是否是当前时间的课程
     *
     * @param course
     * @param dayPosition  日期
     * @param timePosition 时间
     * @return 返回boolean 数组长度为2，
     * boolean[0]：true 代表该课程是当前时间点的课程，boolean[1]：true 代表当前时间点是上面的连续课程
     */
    protected boolean[] compareToCourse(YueKeBean.CourseListBean course, int dayPosition, int timePosition) {
        boolean[] result = new boolean[2];
        String date_str = top_dates.get(dayPosition).getMonth() + "/" + top_dates.get(dayPosition).getDate();
        String year = DateHelper.get_year(course.getStartTimeStamp());
        String month = DateHelper.get_month(course.getStartTimeStamp());
        String date = DateHelper.get_date(course.getStartTimeStamp());
        int hour = DateHelper.get_hour(course.getStartTimeStamp());
        int minute = DateHelper.get_minite(course.getStartTimeStamp());
        String item_date = month + "/" + date;
        //Log.e("开始日期", year + month + date + "-" + hour + ":" + minute);
        if (item_date.equals(date_str)) {//如果日期相同
            //Log.e("上方的时间", item_date + "");
            //Log.e("左边的时间", hour + "");
            if ((hour - 6) == (timePosition + 1)) {//定位纵坐标
                result[0] = true;
                result[1] = false;
            } else if (hour < (timePosition + 1) && (hour + 1) > (timePosition + 1)) {
                result[0] = true;
                result[1] = true;
            }
        }
        return result;
    }


    /**
     * 点击空白块监听
     *
     * @param dayPosition
     * @param timePosition
     */
    protected void onClickEmptyCourse(View view, int dayPosition, int timePosition) {

    }

    /**
     * 点击有数据item监听
     */
    Click_item click_item;

    public void set_click_item_listen(Click_item mclick_item) {
        click_item = mclick_item;
    }

    public interface Click_item {
        void onClickCourse(View view, YueKeBean.CourseListBean course, int dataPosition, int dayPosition, int timePosition);
    }
}
