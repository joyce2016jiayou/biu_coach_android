package com.noplugins.keepfit.coachplatform.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.EasyPhotos;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.bean.ClassDateBean;
import com.noplugins.keepfit.coachplatform.bean.ScheduleBean;
import com.noplugins.keepfit.coachplatform.fragment.ScheduleFragment;
import com.noplugins.keepfit.coachplatform.fragment.StepOneFragment;
import com.noplugins.keepfit.coachplatform.util.BaseUtils;
import com.noplugins.keepfit.coachplatform.util.GlideEngine;
import com.noplugins.keepfit.coachplatform.util.screen.ScreenUtilsHelper;
import com.noplugins.keepfit.coachplatform.util.ui.erweima.encode.CodeCreator;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import com.umeng.socialize.media.Base;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class WeijieshuTypeAdapter extends BaseAdapter implements EasyPermissions.PermissionCallbacks {
    private ScheduleFragment scheduleFragment;
    private LayoutInflater inflater;
    public static final int PERMISSION_STORAGE_CODE = 10001;
    public static final String PERMISSION_STORAGE_MSG = "需要电话权限才能联系客服哦";
    public static final String[] PERMISSION_STORAGE = new String[]{Manifest.permission.CALL_PHONE};

    private List<ScheduleBean.NoEndCourseBean> list;

    public WeijieshuTypeAdapter(List<ScheduleBean.NoEndCourseBean> mlist, ScheduleFragment m_scheduleFragment) {
        this.scheduleFragment = m_scheduleFragment;
        this.list = mlist;
        this.inflater = LayoutInflater.from(scheduleFragment.getContext());
    }

    public void setList(List<ScheduleBean.NoEndCourseBean> list) {
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
            holder.changguan_name = convertView.findViewById(R.id.changguan_name);
            holder.time_tv = convertView.findViewById(R.id.time_tv);
            holder.class_type = convertView.findViewById(R.id.class_type);
            holder.status_img = convertView.findViewById(R.id.status_img);
            holder.type_icon_tv = convertView.findViewById(R.id.type_icon_tv);
            holder.type_icon_bg = convertView.findViewById(R.id.type_icon_bg);
            holder.phone_or_name_tv = convertView.findViewById(R.id.phone_or_name_tv);
            holder.button_tv = convertView.findViewById(R.id.button_tv);
            holder.button_bg = convertView.findViewById(R.id.button_bg);
            holder.phone_img = convertView.findViewById(R.id.phone_img);
            holder.base_layout = convertView.findViewById(R.id.base_layout);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        ScheduleBean.NoEndCourseBean noEndCourseBean = list.get(position);

        holder.changguan_name.setText(noEndCourseBean.getAreaName());
        holder.time_tv.setText(noEndCourseBean.getCourseTime());
        holder.class_type.setText(noEndCourseBean.getCourseName());

        if(null!=noEndCourseBean.getStartStatus()){
            if(noEndCourseBean.getStartStatus().equals("未开始")){
                holder.status_img.setImageResource(R.drawable.weikaishi_icon);
            }else if(noEndCourseBean.getStartStatus().equals("进行中")){
                holder.status_img.setImageResource(R.drawable.jingxingzhong_icon);
            }else if(noEndCourseBean.getStartStatus().equals("已结束")){
                holder.status_img.setImageResource(R.drawable.yijieshu);
            }else  if(noEndCourseBean.getStartStatus().equals("已取消")){
                holder.status_img.setImageResource(R.drawable.yiquxiao_icon);
            }
        }

        //设置是否签到
        if (noEndCourseBean.getCheckIn() == 1) {//已签到
            holder.button_tv.setText("已签");
            holder.button_tv.setTextColor(scheduleFragment.getResources().getColor(R.color.color_929292));
        } else {//未签到
            holder.button_tv.setText("签到");
            holder.button_tv.setTextColor(scheduleFragment.getResources().getColor(R.color.color_lan));
            //点击签到
            holder.button_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(BaseUtils.isFastClick()){
                        if(holder.button_tv.getText().equals("签到")){
                            camera_pop_window(holder.base_layout,noEndCourseBean.getCustOrderItemNum());
                            //设置已签到的状态
//                        holder.button_tv.setText("已签");
//                        holder.button_tv.setTextColor(scheduleFragment.getResources().getColor(R.color.color_929292));
                        }
                    }

                }
            });
        }

        //设置是否是团课
        if (noEndCourseBean.getCourseType() == 1) {//团课
            holder.type_icon_tv.setText("团");
            holder.type_icon_bg.setBackgroundResource(R.drawable.tuan_bg);
            holder.phone_or_name_tv.setText(noEndCourseBean.getPersonNum() + "人");
            holder.phone_img.setVisibility(View.GONE);
        } else {//私教
            holder.type_icon_tv.setText("私");
            holder.type_icon_bg.setBackgroundResource(R.drawable.si_bg);
            if(TextUtils.isEmpty(noEndCourseBean.getUserName())){
                holder.phone_or_name_tv.setText(noEndCourseBean.getCustUserNum());
            }else{
                holder.phone_or_name_tv.setText(noEndCourseBean.getUserName());
            }
            holder.phone_img.setVisibility(View.VISIBLE);
        }

        //点击打电话
        holder.phone_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call_pop(holder.phone_img, noEndCourseBean.getUserPhone());
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

        cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        sure_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BaseUtils.isFastClick()){
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

    public static boolean hasPermissions(Context context, String... permissions) {
        return EasyPermissions.hasPermissions(context, permissions);
    }

    public static boolean hasStoragePermission(Context context) {
        return hasPermissions(context, PERMISSION_STORAGE);
    }

    /**
     * 扫码弹框
     *
     * @param button_bg
     */
    private void camera_pop_window(LinearLayout button_bg,String num) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(scheduleFragment.getContext())
                .setView(R.layout.saoma_layout)
                .setBackGroundLevel(0.5f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(button_bg);

        /**设置逻辑*/
        View view = popupWindow.getContentView();
        ImageView dismiss_img = view.findViewById(R.id.dismiss_img);
        dismiss_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        ImageView erweima_img = view.findViewById(R.id.erweima_img);

        String number = num+":2";
        Bitmap bitmap = CodeCreator.createQRImage(number, ScreenUtilsHelper.dip2px(scheduleFragment.getContext(), 200), ScreenUtilsHelper.dip2px(scheduleFragment.getContext(), 200), null);
//        erweima_code.setImageBitmap(bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        Glide.with(scheduleFragment.getContext())
                .load(bytes)
                .centerCrop()
                .into(erweima_img);

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(scheduleFragment, perms)) {
            new AppSettingsDialog.Builder(scheduleFragment)
                    .setTitle("提醒")
                    .setRationale("需要电话权限才能联系客服哦")
                    .build()
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, scheduleFragment);
    }


    private class viewHolder {
        public TextView changguan_name, time_tv, class_type, type_icon_tv, phone_or_name_tv, button_tv;
        public ImageView status_img, phone_img;
        public LinearLayout button_bg, type_icon_bg,base_layout;
    }


}
