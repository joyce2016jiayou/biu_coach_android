package com.noplugins.keepfit.coachplatform.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.activity.WriteDailryActivity;
import com.noplugins.keepfit.coachplatform.bean.CheckInformationBean;
import com.noplugins.keepfit.coachplatform.bean.ClassDateBean;
import com.noplugins.keepfit.coachplatform.bean.ScheduleBean;
import com.noplugins.keepfit.coachplatform.fragment.ScheduleFragment;

import java.util.List;

public class YiJieShuTypeAdapter extends BaseAdapter {
    private ScheduleFragment scheduleFragment;
    private LayoutInflater inflater;

    private List<ScheduleBean.AlreadyEndCourseBean> list;

    public YiJieShuTypeAdapter(List<ScheduleBean.AlreadyEndCourseBean> mlist, ScheduleFragment m_scheduleFragment) {
        this.scheduleFragment = m_scheduleFragment;
        this.list = mlist;
        this.inflater = LayoutInflater.from(scheduleFragment.getContext());
    }

    public void setList(List<ScheduleBean.AlreadyEndCourseBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final viewHolder holder;
        if (convertView == null) {
            holder = new viewHolder();
            convertView = inflater.inflate(R.layout.weijieshu_item, null);
            holder.status_tv = convertView.findViewById(R.id.status_tv);
            holder.changguan_name = convertView.findViewById(R.id.changguan_name);
            holder.time_tv = convertView.findViewById(R.id.time_tv);
            holder.class_type = convertView.findViewById(R.id.class_type);
            holder.status_img = convertView.findViewById(R.id.status_img);
            holder.type_icon_tv = convertView.findViewById(R.id.type_icon_tv);
            holder.type_icon_bg = convertView.findViewById(R.id.type_icon_bg);
            holder.people_number_tv = convertView.findViewById(R.id.people_number_tv);
            holder.button_tv = convertView.findViewById(R.id.button_tv);
            holder.button_bg = convertView.findViewById(R.id.button_bg);
            holder.phone_or_name_tv = convertView.findViewById(R.id.phone_or_name_tv);
            holder.phone_img = convertView.findViewById(R.id.phone_img);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        ScheduleBean.AlreadyEndCourseBean alreadyEndCourseBean = list.get(position);

        holder.changguan_name.setText(alreadyEndCourseBean.getAreaName());
        holder.time_tv.setText(alreadyEndCourseBean.getCourseTime());
        holder.class_type.setText(alreadyEndCourseBean.getCourseName());
        holder.status_img.setImageResource(R.drawable.yijieshu);
        //设置是否是团课
        if (alreadyEndCourseBean.getCourseType() == 1) {//团课
            holder.type_icon_tv.setText("团");
            holder.type_icon_bg.setBackgroundResource(R.drawable.tuan_bg);
            holder.phone_or_name_tv.setText(alreadyEndCourseBean.getPersonNum() + "人");
            holder.phone_img.setVisibility(View.GONE);
        } else {//私教
            holder.type_icon_tv.setText("私");
            holder.type_icon_bg.setBackgroundResource(R.drawable.si_bg);
            if (TextUtils.isEmpty(alreadyEndCourseBean.getUserName())) {
                holder.phone_or_name_tv.setText(alreadyEndCourseBean.getCustUserNum());
            } else {
                holder.phone_or_name_tv.setText(alreadyEndCourseBean.getUserName());
            }
            holder.phone_img.setVisibility(View.GONE);
        }
        //判断是否显示日志
        if (alreadyEndCourseBean.getSportLog() == 0) {//没写过
            holder.button_bg.setVisibility(View.VISIBLE);
            holder.button_tv.setText("写日志");
            holder.button_tv.setTextColor(Color.parseColor("#00BABB"));
        } else {//写过
            holder.button_bg.setVisibility(View.GONE);

        }
        holder.button_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(scheduleFragment.getActivity(), WriteDailryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("order_key", alreadyEndCourseBean.getCustOrderItemNum());
                intent.putExtras(bundle);
                scheduleFragment.getActivity().startActivity(intent);
            }
        });
        return convertView;
    }


    private class viewHolder {
        public TextView status_tv, changguan_name, time_tv, class_type, type_icon_tv, people_number_tv, button_tv, phone_or_name_tv;
        public ImageView status_img, phone_img;
        public LinearLayout button_bg, type_icon_bg;
    }
}
