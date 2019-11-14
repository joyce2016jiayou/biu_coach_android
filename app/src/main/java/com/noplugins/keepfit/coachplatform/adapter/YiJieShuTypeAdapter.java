package com.noplugins.keepfit.coachplatform.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.activity.ClassDetailActivity;
import com.noplugins.keepfit.coachplatform.activity.WriteDailryActivity;
import com.noplugins.keepfit.coachplatform.bean.CheckInformationBean;
import com.noplugins.keepfit.coachplatform.bean.ClassDateBean;
import com.noplugins.keepfit.coachplatform.bean.ScheduleBean;
import com.noplugins.keepfit.coachplatform.fragment.ScheduleFragment;
import com.noplugins.keepfit.coachplatform.util.BaseUtils;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import java.util.List;

public class YiJieShuTypeAdapter extends BaseAdapter {
    private ScheduleFragment scheduleFragment;
    private LayoutInflater inflater;

    private List<ScheduleBean.AlreadyEndCourseBean> list;
    public static final int PERMISSION_STORAGE_CODE = 10001;
    public static final String PERMISSION_STORAGE_MSG = "需要电话权限才能联系客服哦";
    public static final String[] PERMISSION_STORAGE = new String[]{Manifest.permission.CALL_PHONE};

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
            holder.base_layout = convertView.findViewById(R.id.base_layout);
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
            holder.phone_img.setVisibility(View.VISIBLE);
        }
        //判断是否显示日志
        if (alreadyEndCourseBean.getSprotLog() == 0) {//没写过
            holder.button_bg.setVisibility(View.VISIBLE);
            holder.button_tv.setText("写日志");
            holder.button_tv.setTextColor(Color.parseColor("#00BABB"));
        } else {//写过
            Log.e("圣诞节疯狂了坚实的", "防守打法发送到");
            holder.button_bg.setVisibility(View.GONE);

        }
        holder.button_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alreadyEndCourseBean.getCheckIn() == 0) {//没签到
                    Toast.makeText(scheduleFragment.getActivity(), "该用户尚未签到哦~", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(scheduleFragment.getActivity(), WriteDailryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("order_key", alreadyEndCourseBean.getCustOrderItemNum());
                    intent.putExtras(bundle);
                    scheduleFragment.getActivity().startActivity(intent);
                }
            }
        });
        holder.base_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alreadyEndCourseBean.getCourseType() == 1) {//团课
                    Intent intent = new Intent(scheduleFragment.getActivity(), ClassDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("cource_type", alreadyEndCourseBean.getCourseType());
                    bundle.putString("courseNum", alreadyEndCourseBean.getCourseNum());
                    bundle.putString("order_number", alreadyEndCourseBean.getCustOrderItemNum());
                    bundle.putString("user_number", alreadyEndCourseBean.getCustUserNum());
                    intent.putExtras(bundle);
                    scheduleFragment.startActivity(intent);
                } else if (alreadyEndCourseBean.getCourseType() == 2) {
                    Intent intent = new Intent(scheduleFragment.getActivity(), ClassDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("cource_type", alreadyEndCourseBean.getCourseType());
                    bundle.putString("courseNum", alreadyEndCourseBean.getCourseNum());
                    bundle.putString("order_number", alreadyEndCourseBean.getCustOrderItemNum());
                    bundle.putString("user_number", alreadyEndCourseBean.getCustUserNum());
                    intent.putExtras(bundle);
                    scheduleFragment.startActivity(intent);
                }
            }
        });
        holder.phone_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call_pop(holder.phone_img, alreadyEndCourseBean.getUserPhone());

            }
        });
        return convertView;
    }

    private void call_pop(ImageView phone_img, String phone_number) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(scheduleFragment.getContext())
                .setView(R.layout.call_pop)
                .setBackGroundLevel(0.5f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(phone_img);

        /**设置逻辑*/
        View view = popupWindow.getContentView();
        LinearLayout cancel_layout = view.findViewById(R.id.cancel_layout);
        LinearLayout sure_layout = view.findViewById(R.id.sure_layout);
        TextView phone_number_tv = view.findViewById(R.id.phone_number_tv);
        phone_number_tv.setText(phone_number);
        cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        sure_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BaseUtils.isFastClick()) {
                    initSimple(phone_number);
                    popupWindow.dismiss();
                }
            }
        });
    }

    @AfterPermissionGranted(PERMISSION_STORAGE_CODE)
    public void initSimple(String user_phone) {
        if (hasStoragePermission(scheduleFragment.getContext())) {
            //有权限
            callPhone(user_phone);
        } else {
            //申请权限
            EasyPermissions.requestPermissions(scheduleFragment, PERMISSION_STORAGE_MSG, PERMISSION_STORAGE_CODE, PERMISSION_STORAGE);
        }
    }

    public void callPhone(String phoneNum) {
        Intent intent1 = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent1.setData(data);
        scheduleFragment.startActivity(intent1);
    }

    public static boolean hasStoragePermission(Context context) {
        return hasPermissions(context, PERMISSION_STORAGE);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        return EasyPermissions.hasPermissions(context, permissions);
    }


    private class viewHolder {
        public TextView status_tv, changguan_name, time_tv, class_type, type_icon_tv, people_number_tv, button_tv, phone_or_name_tv;
        public ImageView status_img, phone_img;
        public LinearLayout button_bg, type_icon_bg, base_layout;
    }
}
