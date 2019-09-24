package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.constant.Type;
import com.noplugins.keepfit.coachplatform.MainActivity;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.adapter.TypeAdapter;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.base.MyApplication;
import com.noplugins.keepfit.coachplatform.bean.AddPhotoBean;
import com.noplugins.keepfit.coachplatform.bean.CheckInformationBean;
import com.noplugins.keepfit.coachplatform.bean.LoginBean;
import com.noplugins.keepfit.coachplatform.bean.QiNiuToken;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.GlideEngine;
import com.noplugins.keepfit.coachplatform.util.MessageEvent;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.screen.KeyboardUtils;
import com.noplugins.keepfit.coachplatform.util.ui.MyListView;
import com.noplugins.keepfit.coachplatform.util.ui.ProgressUtil;
import com.noplugins.keepfit.coachplatform.util.ui.jiugongge.CCRSortableNinePhotoLayout;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import com.noplugins.keepfit.coachplatform.util.ui.switchbutton.ToogleButton;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import rx.Subscription;

import java.text.SimpleDateFormat;
import java.util.*;

public class AddZhengshuActivity extends BaseActivity {

    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.add_zhengshu_photos_view)
    CCRSortableNinePhotoLayout add_zhengshu_photos_view;
    @BindView(R.id.select_zhengshu_type)
    RelativeLayout select_zhengshu_type;
    @BindView(R.id.zhengshu_type_tv)
    TextView zhengshu_type_tv;
    @BindView(R.id.select_time_btn)
    RelativeLayout select_time_btn;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.done_btn)
    LinearLayout done_btn;
    @BindView(R.id.continue_add)
    LinearLayout continue_add;
    @BindView(R.id.zhengshu_tv)
    TextView zhengshu_tv;

    private String select_time = "";
    private ProgressUtil progress_upload;
    private SimpleDateFormat sdf;
    private String qiniu_key;
    private int select_zhengshu_max_num = 0;
    private String uptoken = "xxxxxxxxx:xxxxxxx:xxxxxxxxxx";
    private List<CheckInformationBean.CoachPicCertificatesBean> zhengshu_images_select = new ArrayList<>();
    TimePickerView pvCustomTime;
    private UploadManager uploadManager;

    @Override
    public void initBundle(Bundle parms) {

    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_add_zhengshu);
        ButterKnife.bind(this);
        isShowTitle(false);
        /**七牛云**/
        uploadManager = MyApplication.uploadManager;
        sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        qiniu_key = "icon_" + sdf.format(new Date());
        /**七牛云**/
    }

    private void getToken() {
        Map<String, Object> params = new HashMap<>();
        Subscription subscription = Network.getInstance("获取七牛token", this)
                .get_qiniu_token(params,
                        new ProgressSubscriber<>("获取七牛token", new SubscriberOnNextListener<Bean<QiNiuToken>>() {
                            @Override
                            public void onNext(Bean<QiNiuToken> result) {
                                uptoken = result.getData().getToken();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));

    }

    @Override
    public void doBusiness(Context mContext) {
        getToken();//获取七牛云token

        add_zhengshu_photos_view.setDelegate(select_zhengshu);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //选择证书类型
        select_zhengshu_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_type_pop();
            }
        });
        //选择时间
        select_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_time_pop();
            }
        });
        //完成添加
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //对重复图片做校验 AppConstants.SELECT_PHOTO_NUM
                MessageEvent messageEvent = new MessageEvent(AppConstants.UPDATE_SELECT_PHOTO);
                EventBus.getDefault().postSticky(messageEvent);
                finish();
            }
        });
        //继续添加
        continue_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //保存已经选中的图片
                MessageEvent messageEvent = new MessageEvent(AppConstants.UPDATE_SELECT_PHOTO);
                EventBus.getDefault().postSticky(messageEvent);
                //清空页面数据
                zhengshu_type_tv.setText("");
                zhengshu_tv.setText("");
                time_tv.setText("");
                zhengshu_images_select.clear();

                set_zhengshu_view();

            }
        });

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
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                select_time = select_year + "-" + select_month;
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
        //影藏键盘
        if (KeyboardUtils.isSoftVisiable(AddZhengshuActivity.this)) {
            //Log.e("是否显示","显示");
            KeyboardUtils.hideSoftKeyboard(AddZhengshuActivity.this);

        } else {
            //Log.e("是否显示","影藏");
        }
    }

    private void select_type_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.select_type_layout)
                .setBackGroundLevel(1f)//0.5f
                .setAnimationStyle(R.style.top_to_bottom)
                .setWidthAndHeight(select_zhengshu_type.getWidth(),
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        //popupWindow.showAsDropDown(select_zhengshu_type);
        popupWindow.showAsDropDown(select_zhengshu_type, 0, -20);
        /**设置逻辑*/
        View view = popupWindow.getContentView();
        List<String> strings = new ArrayList<>();
        strings.add("CPT体系证书");
        strings.add("技能证书");
        strings.add("急救证书");
        strings.add("其他");
        TypeAdapter typeAdapter = new TypeAdapter(strings, getApplicationContext());
        ListView listView = view.findViewById(R.id.listview);
        listView.setAdapter(typeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                zhengshu_type_tv.setText(strings.get(i));
                popupWindow.dismiss();
            }
        });
        //影藏键盘
        if (KeyboardUtils.isSoftVisiable(AddZhengshuActivity.this)) {
            //Log.e("是否显示","显示");
            KeyboardUtils.hideSoftKeyboard(AddZhengshuActivity.this);

        } else {
            //Log.e("是否显示","影藏");

        }


    }


    CCRSortableNinePhotoLayout.Delegate select_zhengshu = new CCRSortableNinePhotoLayout.Delegate() {

        @Override
        public void onClickAddNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
            if (AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE_TWO >= 2) {
                Toast.makeText(AddZhengshuActivity.this, "只能上传2张图片哦～", Toast.LENGTH_SHORT).show();
            } else if (AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE_TWO < 2) {
                select_zhengshu_max_num = 2 - AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE_TWO;
                EasyPhotos.createAlbum(AddZhengshuActivity.this, true, GlideEngine.getInstance())
                        .setFileProviderAuthority("com.noplugins.keepfit.android.fileprovider")
                        .setPuzzleMenu(false)
                        .setCount(select_zhengshu_max_num)
                        .setOriginalMenu(false, true, null)
                        .start(101);
            }
        }

        @Override
        public void onClickDeleteNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
            add_zhengshu_photos_view.removeItem(position);
            AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE_TWO = AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE_TWO - 1;
        }

        @Override
        public void onClickNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            //相机或相册回调
            if (requestCode == 101) {
                //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                /*ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                for (int i = 0; i < resultPhotos.size(); i++) {
                    Log.e("图片地址", resultPhotos.get(i).path);
                }
                boolean selectedOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false);
                */

                ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
                Log.e("返回的path:",resultPaths.size()+"");
                List<CheckInformationBean.CoachPicCertificatesBean> return_selet = new ArrayList<>();
                for (int i = 0; i < resultPaths.size(); i++) {
                    CheckInformationBean.CoachPicCertificatesBean coachPicCertificatesBean = new CheckInformationBean.CoachPicCertificatesBean();
                    if (i == 0) {
                        coachPicCertificatesBean.setCertType("1");//证书类型,有字典
                        coachPicCertificatesBean.setCertDate(select_time);//证书日期
                        coachPicCertificatesBean.setLocal_img_path(resultPaths.get(i));
                        coachPicCertificatesBean.setCertName(zhengshu_tv.getText().toString());
                        return_selet.add(coachPicCertificatesBean);
                    } else {
                        coachPicCertificatesBean.setLocal_img_path(resultPaths.get(i));
                        return_selet.add(coachPicCertificatesBean);
                    }
                }
                zhengshu_images_select.addAll(return_selet);
                //todo 重复添加第二张的问题

                set_zhengshu_view();
                return;
            }

        } else if (RESULT_CANCELED == resultCode) {
            //Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }

    private void set_zhengshu_view() {
        set_jiugongge();
        if (zhengshu_images_select.size() > 0) {
            //上传七牛,只上传第一张
            if(zhengshu_images_select.get(0).getCertFrontKey()!=null){//只上传前面一张
                return;
            }else{
                Log.e("进来了添加","进来了添加");
                upload_image_work(zhengshu_images_select.get(0));
            }
        }
    }

    /**
     * 设置证书的九宫格
     */
    private void set_jiugongge(){
        List<String> iamges = new ArrayList<>();
        for (CheckInformationBean.CoachPicCertificatesBean coachPicCertificatesBean : zhengshu_images_select) {
            iamges.add(coachPicCertificatesBean.getLocal_img_path());
        }
        add_zhengshu_photos_view.setData(iamges);//设置九宫格
        AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE_TWO = zhengshu_images_select.size();
    }

    private void upload_image_work(CheckInformationBean.CoachPicCertificatesBean coachPicCertificatesBean) {
        progress_upload = new ProgressUtil();
        progress_upload.showProgressDialog(this, "载入中...");
        //上传icon
        uploadManager.put(coachPicCertificatesBean.getLocal_img_path(), qiniu_key, uptoken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            String icon_net_path = key;
                            String headpicPath = "http://upload.qiniup.com/" + key;

                            coachPicCertificatesBean.setCertFrontKey(icon_net_path);
                            AppConstants.SELECT_PHOTO_NUM.add(coachPicCertificatesBean);
                            set_jiugongge();


                            for (int i = 0; i < AppConstants.SELECT_PHOTO_NUM.size(); i++) {
                                CheckInformationBean.CoachPicCertificatesBean obg = AppConstants.SELECT_PHOTO_NUM.get(i);
                                Log.e("上传的key", obg.getCertFrontKey());
                            }

                            Log.e("qiniu", "Upload Success");
                            Log.e("打印key：", icon_net_path);
                            Log.e("返回的地址", headpicPath);
                        } else {
                            Log.e("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        //Log.e("qiniu", key + ",\r\n " + info.path + ",\r\n " + response);
                        progress_upload.dismissProgressDialog();

                    }
                }, new UploadOptions(null, "test-type", true, null, null));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("进来了吗", "进来了");
        AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE_TWO = 0;
        zhengshu_images_select.clear();
    }
}
