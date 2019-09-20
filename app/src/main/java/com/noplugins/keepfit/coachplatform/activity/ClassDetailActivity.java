package com.noplugins.keepfit.coachplatform.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.models.puzzle.Line;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.util.screen.ScreenUtilsHelper;
import com.noplugins.keepfit.coachplatform.util.ui.erweima.encode.CodeCreator;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class ClassDetailActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.status_tv)
    TextView status_tv;
    @BindView(R.id.yiqiandao_layout)
    LinearLayout yiqiandao_layout;
    @BindView(R.id.yiyueyue_layout)
    LinearLayout yiyueyue_layout;
    @BindView(R.id.top_view)
    RelativeLayout top_view;
    @BindView(R.id.button)
    TextView button;
    @BindView(R.id.call_number_btn)
    LinearLayout call_number_btn;

    public static final int PERMISSION_STORAGE_CODE = 10001;
    public static final String PERMISSION_STORAGE_MSG = "需要电话权限才能拨打电话哦";
    public static final String[] PERMISSION_STORAGE = new String[]{Manifest.permission.CALL_PHONE};
    String is_qiandao = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {
        is_qiandao = parms.getString("is_qiandao");

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_class_detail);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {

        if (is_qiandao.equals("1")) {//显示已签到
            yiqiandao_layout.setVisibility(View.VISIBLE);
            yiyueyue_layout.setVisibility(View.GONE);
            top_view.setVisibility(View.GONE);
        } else if (is_qiandao.equals("2")) {//显示已预约
            status_tv.setText("已预约");
            yiqiandao_layout.setVisibility(View.GONE);
            yiyueyue_layout.setVisibility(View.VISIBLE);
            top_view.setVisibility(View.VISIBLE);
            button.setText("签到");
        } else {//显示已结束
            status_tv.setText("已结束");
            yiqiandao_layout.setVisibility(View.GONE);
            yiyueyue_layout.setVisibility(View.VISIBLE);
            top_view.setVisibility(View.VISIBLE);
            button.setText("写日志");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals("签到")) {
                    camera_pop_window();
                } else if (button.getText().equals("写日志")) {
                    Intent intent = new Intent(ClassDetailActivity.this, WriteDailryActivity.class);
                    startActivity(intent);
                }
            }
        });
        //拨打电话
        call_number_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call_pop();
            }
        });


        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    /**
     * 拨打电话
     */
    private void call_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(ClassDetailActivity.this)
                .setView(R.layout.call_pop)
                .setBackGroundLevel(0.5f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(call_number_btn);

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
                initSimple();
                popupWindow.dismiss();
            }
        });


    }

    @AfterPermissionGranted(PERMISSION_STORAGE_CODE)
    public void initSimple() {
        if (hasStoragePermission(ClassDetailActivity.this)) {
            //有权限
            callPhone("10010");
        } else {
            //申请权限
            EasyPermissions.requestPermissions(ClassDetailActivity.this, PERMISSION_STORAGE_MSG, PERMISSION_STORAGE_CODE, PERMISSION_STORAGE);
        }
    }

    @SuppressLint("MissingPermission")
    public void callPhone(String phoneNum) {
        Intent intent1 = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent1.setData(data);
        startActivity(intent1);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        return EasyPermissions.hasPermissions(context, permissions);
    }

    public static boolean hasStoragePermission(Context context) {
        return hasPermissions(context, PERMISSION_STORAGE);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(ClassDetailActivity.this, perms)) {
            new AppSettingsDialog.Builder(ClassDetailActivity.this)
                    .setTitle("提醒")
                    .setRationale("需要电话权限才能联系客服哦")
                    .build()
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, ClassDetailActivity.this);
    }

    /**
     * 扫码弹框
     */
    private void camera_pop_window() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(ClassDetailActivity.this)
                .setView(R.layout.saoma_layout)
                .setBackGroundLevel(0.5f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(button);

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

        Bitmap bitmap = CodeCreator.createQRImage("哈哈哈哈", ScreenUtilsHelper.dip2px(ClassDetailActivity.this, 200), ScreenUtilsHelper.dip2px(ClassDetailActivity.this, 200), null);
//        erweima_code.setImageBitmap(bitmap);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        Glide.with(ClassDetailActivity.this)
                .load(bytes)
                .centerCrop()
                .into(erweima_img);

    }
}
