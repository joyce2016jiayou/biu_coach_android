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
import com.noplugins.keepfit.coachplatform.activity.manager.ChaungguanDetailActivity;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.bean.ClassDetailBean;
import com.noplugins.keepfit.coachplatform.bean.YueKeBean;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.screen.ScreenUtilsHelper;
import com.noplugins.keepfit.coachplatform.util.ui.erweima.encode.CodeCreator;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Subscription;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @BindView(R.id.changguan_name_tv)
    TextView changguan_name_tv;
    @BindView(R.id.w_room_name)
    TextView w_room_name;
    @BindView(R.id.w_class_name)
    TextView w_class_name;
    @BindView(R.id.w_class_type)
    TextView w_class_type;
    @BindView(R.id.w_price_tv)
    TextView w_price_tv;
    @BindView(R.id.w_class_time)
    TextView w_class_time;
    @BindView(R.id.w_class_duration_tv)
    TextView w_class_duration_tv;
    @BindView(R.id.w_class_renshu_zhanbi)
    TextView w_class_renshu_zhanbi;
    @BindView(R.id.y_class_name)
    TextView y_class_name;
    @BindView(R.id.y_class_time)
    TextView y_class_time;
    @BindView(R.id.y_class_price)
    TextView y_class_price;
    @BindView(R.id.y_class_duration_time)
    TextView y_class_duration_time;
    @BindView(R.id.y_user_name)
    TextView y_user_name;
    @BindView(R.id.go_to_changguan_detail)
    LinearLayout go_to_changguan_detail;

    public static final int PERMISSION_STORAGE_CODE = 10001;
    public static final String PERMISSION_STORAGE_MSG = "需要电话权限才能拨打电话哦";
    public static final String[] PERMISSION_STORAGE = new String[]{Manifest.permission.CALL_PHONE};
    private String cource_type = "";
    private String courseNum = "";
    private String order_number = "";
    private String user_number = "";
    private String phone_umber = "";
    private String changguan_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {
        cource_type = parms.getString("cource_type");
        courseNum = parms.getString("courseNum");
        order_number = parms.getString("order_number");
        user_number = parms.getString("user_number");
    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_class_detail);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText().equals("签到")) {
                    camera_pop_window();
                } else if (button.getText().equals("写日志")) {
                    Intent intent = new Intent(ClassDetailActivity.this, WriteDailryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("order_key", order_number);
                    intent.putExtras(bundle);
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

        initDate();
        go_to_changguan_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ClassDetailActivity.this, ChaungguanDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("cgNum",changguan_id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initDate() {
        Map<String, Object> params = new HashMap<>();
        params.put("courseNum", courseNum);
        params.put("custUserNum", user_number);
        Subscription subscription = Network.getInstance("约课信息详情", this)
                .class_detail(params,
                        new ProgressSubscriber<>("约课信息详情", new SubscriberOnNextListener<Bean<ClassDetailBean>>() {
                            @Override
                            public void onNext(Bean<ClassDetailBean> result) {
                                set_value(result.getData());
                            }

                            @Override
                            public void onError(String error) {
                            }
                        }, this, true));
    }

    private void set_value(ClassDetailBean data) {
        changguan_name_tv.setText(data.getAreaName());
        if (cource_type.equals("1")) {//团课
            yiqiandao_layout.setVisibility(View.VISIBLE);
            yiyueyue_layout.setVisibility(View.GONE);
            top_view.setVisibility(View.GONE);
            //设置数据
            status_tv.setText(data.getCheckInStatus());
            w_room_name.setText(data.getCourseHome());
            w_class_name.setText(data.getCourseName());
            w_class_type.setText(data.getClassType());
            w_price_tv.setText("¥" + data.getFinalPrice());
            w_class_time.setText(data.getTime());
            w_class_duration_tv.setText(data.getMin());
            w_class_renshu_zhanbi.setText(data.getPerson());
        } else {//私教
            yiqiandao_layout.setVisibility(View.GONE);
            yiyueyue_layout.setVisibility(View.VISIBLE);
            top_view.setVisibility(View.VISIBLE);
            button.setText("签到");
            //判断是否写过日志
            if (data.getSportLog() == 0) {//没写过日志
                yiqiandao_layout.setVisibility(View.GONE);
                yiyueyue_layout.setVisibility(View.VISIBLE);
                top_view.setVisibility(View.VISIBLE);
                button.setText("写日志");
            } else {
                yiqiandao_layout.setVisibility(View.GONE);
                yiyueyue_layout.setVisibility(View.VISIBLE);
                top_view.setVisibility(View.GONE);
            }
            //设置数据
            y_class_name.setText(data.getCourseName());
            y_class_time.setText(data.getTime());
            y_class_price.setText("¥" + data.getFinalPrice());
            y_class_duration_time.setText(data.getMin());
            y_user_name.setText(data.getNickName());
            //判断是否签到
            status_tv.setText(data.getCheckInStatus());
            phone_umber = data.getPhone();
            changguan_id = data.getAreaNum();
        }

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
        TextView phone_number_tv = view.findViewById(R.id.phone_number_tv);
        phone_number_tv.setText(phone_umber);
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
            callPhone(phone_umber);
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

        Bitmap bitmap = CodeCreator.createQRImage(order_number, ScreenUtilsHelper.dip2px(ClassDetailActivity.this, 200), ScreenUtilsHelper.dip2px(ClassDetailActivity.this, 200), null);
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
