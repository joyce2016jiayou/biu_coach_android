package com.noplugins.keepfit.coachplatform.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huantansheng.easyphotos.EasyPhotos;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.activity.AddZhengshuActivity;
import com.noplugins.keepfit.coachplatform.activity.CheckStatusActivity;
import com.noplugins.keepfit.coachplatform.activity.LoginActivity;
import com.noplugins.keepfit.coachplatform.adapter.MineTagAdapter;
import com.noplugins.keepfit.coachplatform.base.MyApplication;
import com.noplugins.keepfit.coachplatform.bean.CheckInformationBean;
import com.noplugins.keepfit.coachplatform.bean.QiNiuToken;
import com.noplugins.keepfit.coachplatform.bean.TagBean;
import com.noplugins.keepfit.coachplatform.callback.ImageCompressCallBack;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.GlideEngine;
import com.noplugins.keepfit.coachplatform.util.MessageEvent;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.data.FileSizeUtil;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.ui.*;
import com.noplugins.keepfit.coachplatform.util.ui.jiugongge.CCRSortableNinePhotoLayout;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;
import rx.Subscription;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.noplugins.keepfit.coachplatform.activity.AddZhengshuActivity.getCompressJpgFileAbsolutePath;


public class StepTwoFragment extends ViewPagerFragment {
    private View view;
    @BindView(R.id.grid_view)
    GridView grid_view;
    @BindView(R.id.tag_num)
    TextView tag_num;
    @BindView(R.id.snpl_moment_add_photos)
    CCRSortableNinePhotoLayout select_shouke_view;
    @BindView(R.id.select_zhengshu_view)
    CCRSortableNinePhotoLayout select_zhengshu_view;
    @BindView(R.id.submit_btn)
    LoadingButton submit_btn;
    @BindView(R.id.shanchang_class_tag_view)
    GridViewForScrollView shanchang_class_tag_view;
    @BindView(R.id.shanchang_tag_num)
    TextView shanchang_tag_num;
    @BindView(R.id.tag_layout_view)
    LinearLayout tag_layout_view;
    @BindView(R.id.teacher_shanchang_class_layout)
    LinearLayout teacher_shanchang_class_layout;
    @BindView(R.id.zhengshu_layout)
    LinearLayout zhengshu_layout;
    @BindView(R.id.shouke_layout)
    LinearLayout shouke_layout;
    private CheckStatusActivity checkStatusActivity;
    private StepView stepView;
    private int select_zhengshu_max_num = 0;
    private int select_shouke_max_num = 0;
    private List<CheckInformationBean.CoachPicTeachingsBean> shouke_images_select = new ArrayList<>();
    private List<CheckInformationBean.CoachPicCertificatesBean> zhengshu_images_select = new ArrayList<>();
    List<TagBean> shanchang_tagBeans = new ArrayList<>();
    List<TagBean> jineng_tagBeans = new ArrayList<>();
    private NoScrollViewPager viewpager_content;


    public static StepTwoFragment homeInstance(String title) {
        StepTwoFragment fragment = new StepTwoFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 七牛云
     **/
    //指定upToken, 强烈建议从服务端提供get请求获取
    private String uptoken = "xxxxxxxxx:xxxxxxx:xxxxxxxxxx";
    private SimpleDateFormat sdf;
    private String qiniu_key;
    private UploadManager uploadManager;
    private ProgressUtil progress_upload;

    /**
     * 七牛云
     **/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_step_two, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            EventBus.getDefault().register(StepTwoFragment.this);
            /**七牛云**/
            uploadManager = MyApplication.uploadManager;
            sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            qiniu_key = "icon_" + sdf.format(new Date());
            getToken();//获取七牛云token

        }
        return view;
    }

    private void getToken() {
        Map<String, Object> params = new HashMap<>();
        Subscription subscription = Network.getInstance("获取七牛token", getActivity())
                .get_qiniu_token(params,
                        new ProgressSubscriber<>("获取七牛token", new SubscriberOnNextListener<Bean<QiNiuToken>>() {
                            @Override
                            public void onNext(Bean<QiNiuToken> result) {
                                uptoken = result.getData().getToken();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));

    }

    private void initView() {
        if (SpUtils.getString(getActivity(), AppConstants.SELECT_TEACHER_TYPE).equals("1")) {//团课
            teacher_shanchang_class_layout.setVisibility(View.VISIBLE);
            tag_layout_view.setVisibility(View.GONE);
            zhengshu_layout.setVisibility(View.VISIBLE);
            shouke_layout.setVisibility(View.VISIBLE);
            init_tag_resource(6);
        } else if (SpUtils.getString(getActivity(), AppConstants.SELECT_TEACHER_TYPE).equals("2")) {//私教
            teacher_shanchang_class_layout.setVisibility(View.VISIBLE);
            tag_layout_view.setVisibility(View.VISIBLE);
            zhengshu_layout.setVisibility(View.VISIBLE);
            shouke_layout.setVisibility(View.GONE);
            init_tag_resource(7);
            init_jineng_tag_resource(3);
        }
        //设置九宫格图片
        set_zhengshu_view();

        submit_btn.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("哈哈哈啊哈哈", "0");
                submit_btn.startLoading();
                //请求数据提交审核
                check_information_for_net();

                submit_btn.loadingComplete();
            }
        });


    }


    private final static String PHOTO_COMPRESS_JPG_BASEPATH = "/" + "TakePhoto" + "/CompressImgs/";

    public static String getCompressJpgFileAbsolutePath() {
        String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + PHOTO_COMPRESS_JPG_BASEPATH;
        File file = new File(fileBasePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        return fileBasePath;
    }

    private void upload_shouke_images() {
        progress_upload = new ProgressUtil();
        progress_upload.showProgressDialog(getActivity(), "图片上传中...");
        for (int i = 0; i < shouke_images_select.size(); i++) {
            Log.e("哈哈哈啊哈哈", "数据" + shouke_images_select.size() + "");

            CheckInformationBean.CoachPicTeachingsBean teachingsBean = shouke_images_select.get(i);
            int finalI = i;
            String expectKey = UUID.randomUUID().toString();
            Luban.with(getActivity())
                    .load(teachingsBean.getLocal_iamge_path())
                    .ignoreBy(100)
                    .setTargetDir(getCompressJpgFileAbsolutePath())
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    }).setCompressListener(new OnCompressListener() {
                @Override
                public void onStart() {
                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    Log.e("哈哈哈啊哈哈", "2");


                }

                @Override
                public void onSuccess(File file) {
                    Log.e("哈哈哈啊哈哈", "3");

                    // TODO 压缩成功后调用，返回压缩后的图片文件
                    compressCallBack.onSucceed2(file.getAbsolutePath(), teachingsBean, expectKey, finalI);//正面
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("哈哈哈啊哈哈", "4");

                    compressCallBack.onFailure(e.getMessage());
                    // TODO 当压缩过程出现问题时调用
                }
            }).launch();

        }
    }

    ImageCompressCallBack compressCallBack = new ImageCompressCallBack() {
        @Override
        public void onSucceed2(String data, CheckInformationBean.CoachPicTeachingsBean teachingsBean, String expectKey, int position) {
//            Log.e("哈哈哈啊哈哈", "5");
//            Log.e("压缩过的", data);
            File file = new File(data);
            //Log.e("压缩后的大小", FileSizeUtil.getFileOrFilesSize(file.getAbsolutePath(), 2) + "");
            upload_images(data, teachingsBean, expectKey, position);
        }

        @Override
        public void onSucceed(String data, boolean b) {

        }

        @Override
        public void onFailure(String msg) {
            Log.e("压缩失败的", msg);
        }
    };

    private void upload_images(String iamge_path, CheckInformationBean.CoachPicTeachingsBean teachingsBean, String expectKey, int position) {
        uploadManager.put(iamge_path, expectKey, uptoken, new UpCompletionHandler() {
            public void complete(String k, ResponseInfo rinfo, JSONObject response) {
                if (rinfo.isOK()) {
                    Log.e("qiniu", "Upload Success");
                    String icon_net_path = k;
                    teachingsBean.setQiniuKey(icon_net_path);
                    Log.e("获取到的key", "获取到的key:" + k);
                    if (position == shouke_images_select.size() - 1) {//上传完最后一张之后，提交数据
                        progress_upload.dismissProgressDialog();
                        submit_information();
                    }
                } else {
                    Log.e("qiniu", "Upload Fail");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            }
        }, new UploadOptions(null, "test-type", true, null, null));
    }

    /**
     * 提交数据
     */
    private void check_information_for_net() {
        if (SpUtils.getString(getActivity(), AppConstants.SELECT_TEACHER_TYPE).equals("1")) {//团课
            if (shanchang_tagBeans.size() == 0) {//擅长课程
                Toast.makeText(getActivity(), R.string.tv142, Toast.LENGTH_SHORT).show();
                return;
            } else if (shouke_images_select.size() == 0) {//授课不能为空
                Toast.makeText(getActivity(), R.string.tv141, Toast.LENGTH_SHORT).show();
                return;
            } else {
                Log.e("进来了团课", "进来了团课");
                //上传授课图片
                upload_shouke_images();
            }
        } else {//私教
            if (zhengshu_images_select.size() == 0) {
                Toast.makeText(getActivity(), R.string.tv140, Toast.LENGTH_SHORT).show();
                return;
            } else if (jineng_tagBeans.size() == 0) {
                Toast.makeText(getActivity(), R.string.tv143, Toast.LENGTH_SHORT).show();
                return;
            } else if (shanchang_tagBeans.size() == 0) {
                Toast.makeText(getActivity(), R.string.tv142, Toast.LENGTH_SHORT).show();
                return;
            } else {
                Log.e("进来了私教", "进来了私教");
                submit_information();
            }

        }

    }

    /**
     * 擅长课程
     *
     * @param get_type
     */
    private void init_tag_resource(int get_type) {
        Map<String, Object> params = new HashMap<>();
        params.put("object", get_type);
        Subscription subscription = Network.getInstance("擅长课程", getActivity())
                .get_biaoqians(params,
                        new ProgressSubscriber<>("擅长课程", new SubscriberOnNextListener<Bean<List<TagBean>>>() {
                            @Override
                            public void onNext(Bean<List<TagBean>> result) {
                                shanchang_tagBeans.addAll(result.getData());
                                set_shanchang_tag(shanchang_tagBeans);

                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
    }

    private void set_shanchang_tag(List<TagBean> shanchang_tagBeans) {
        MineTagAdapter tagAdapter = new MineTagAdapter(getActivity(), shanchang_tagBeans);
        shanchang_class_tag_view.setAdapter(tagAdapter);
        set_shanchang_select_sum(shanchang_tagBeans);
        shanchang_class_tag_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (shanchang_tagBeans.get(i).isCheck()) {
                    shanchang_tagBeans.get(i).setCheck(false);
                } else {
                    shanchang_tagBeans.get(i).setCheck(true);
                }
                tagAdapter.notifyDataSetChanged();
                set_shanchang_select_sum(shanchang_tagBeans);
            }
        });
    }

    int max_select_sum = 0;

    private void set_tag(List<TagBean> jineng_tagBeans) {
        MineTagAdapter tagAdapter = new MineTagAdapter(getActivity(), jineng_tagBeans);
        grid_view.setAdapter(tagAdapter);
        set_select_sum(jineng_tagBeans);
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (jineng_tagBeans.get(i).isCheck()) {
                    jineng_tagBeans.get(i).setCheck(false);
                    max_select_sum--;
                    tagAdapter.notifyDataSetChanged();
                } else {
                    if (max_select_sum >= 4) {
                        Toast.makeText(getContext(), "最多只能选择4个技能", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        max_select_sum++;
                        jineng_tagBeans.get(i).setCheck(true);
                        tagAdapter.notifyDataSetChanged();
                    }
                }
                tag_num.setText("(" + max_select_sum + "/" + "4)");
            }
        });
    }

    /**
     * 教练技能标签
     *
     * @param get_type
     */
    private void init_jineng_tag_resource(int get_type) {
        Map<String, Object> params = new HashMap<>();
        params.put("object", get_type);
        Subscription subscription = Network.getInstance("教练技能", getActivity())
                .get_biaoqians(params,
                        new ProgressSubscriber<>("教练技能", new SubscriberOnNextListener<Bean<List<TagBean>>>() {
                            @Override
                            public void onNext(Bean<List<TagBean>> result) {
                                jineng_tagBeans.addAll(result.getData());
                                //设置标签
                                set_tag(jineng_tagBeans);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
    }

    private String get_selete_biaoqian(List<TagBean> tagBeans) {
        StringBuffer type_buffer = new StringBuffer();
        for (int i = 0; i < tagBeans.size(); i++) {
            if (tagBeans.get(i).isCheck()) {
                if (i == tagBeans.size() - 1) {
                    type_buffer.append(tagBeans.get(i).getValue());
                } else {
                    type_buffer.append(tagBeans.get(i).getValue()).append(",");
                }
            }

        }
        //Log.e("选择的标签编号", image_buffer.toString() + "");
        return type_buffer.toString();
    }

    private void submit_information() {
        CheckInformationBean checkInformationBean = new CheckInformationBean();
        CheckInformationBean.CoachPicCardBean coachPicCardBean = new CheckInformationBean.CoachPicCardBean();
//        coachPicCardBean.setCardBackKey(checkStatusActivity.select_card_fan_path);
//        coachPicCardBean.setCardFrontKey(checkStatusActivity.select_card_zheng_path);
        coachPicCardBean.setCardBackKey("cardzheng");
        coachPicCardBean.setCardFrontKey("cardfan");
        checkInformationBean.setCoachPicCard(coachPicCardBean);
        CheckInformationBean.CoachUserBean coachUserBean = new CheckInformationBean.CoachUserBean();
        if (SpUtils.getString(getActivity(), AppConstants.SELECT_TEACHER_TYPE).equals("1")) {//团课
            coachUserBean.setTeacherType(1);
        } else if (SpUtils.getString(getActivity(), AppConstants.SELECT_TEACHER_TYPE).equals("2")) {//私教
            coachUserBean.setTeacherType(2);
        }
        coachUserBean.setRealname(checkStatusActivity.user_name);
        coachUserBean.setCard(checkStatusActivity.card_id);
        if (checkStatusActivity.sex.equals("男")) {
            coachUserBean.setSex(1);//性别
        } else {
            coachUserBean.setSex(0);//性别
        }
        coachUserBean.setPhone(checkStatusActivity.phone);
        coachUserBean.setProvince(checkStatusActivity.city);//省份
        coachUserBean.setCity(checkStatusActivity.city);//城市
        coachUserBean.setUniversity(checkStatusActivity.school);
        coachUserBean.setProfessiondate(checkStatusActivity.ruhang_time);
        if (null != SpUtils.getString(getActivity(), AppConstants.SELECT_TEACHER_NUMBER)) {
            coachUserBean.setUserNum(SpUtils.getString(getActivity(), AppConstants.SELECT_TEACHER_NUMBER));//教练编号,登录的时候会返回
        }
        checkInformationBean.setCoachUser(coachUserBean);
        if (shanchang_tagBeans.size() > 0) {
            coachUserBean.setGoodAtSkill(get_selete_biaoqian(shanchang_tagBeans));//擅长课程
        }
        if (jineng_tagBeans.size() > 0) {
            coachUserBean.setSkill(get_selete_biaoqian(jineng_tagBeans));//技能标签
        }
        if (shouke_images_select.size() > 0) {
            checkInformationBean.setCoachPicTeachings(shouke_images_select);
        }
        if (zhengshu_images_select.size() > 0) {
            checkInformationBean.setCoachPicCertificates(zhengshu_images_select);
        }

        Subscription subscription = Network.getInstance("提交审核资料", getActivity())
                .submit_information(checkInformationBean,
                        new ProgressSubscriber<>("提交审核资料", new SubscriberOnNextListener<Bean<Object>>() {
                            @Override
                            public void onNext(Bean<Object> result) {

                                viewpager_content.setCurrentItem(3);
                                int step = stepView.getCurrentStep();//设置进度条
                                stepView.setCurrentStep((step + 2) % stepView.getStepNum());
                            }

                            @Override
                            public void onError(String error) {
                            }
                        }, getActivity(), true));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppConstants.SELECT_PHOTO_NUM.clear();//退出时清除选中的照片数据
        EventBus.getDefault().unregister(StepTwoFragment.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void upadate(MessageEvent messageEvent) {
        if (messageEvent.getMessage().equals(AppConstants.UPDATE_SELECT_PHOTO)) {
            //Logger.e("进来了", "进来了");
            zhengshu_images_select.clear();
            zhengshu_images_select.addAll(AppConstants.SELECT_PHOTO_NUM);
            Log.e("传过来的图片数量", "" + zhengshu_images_select.size());
            List<String> iamge_paths = new ArrayList<>();
            for (CheckInformationBean.CoachPicCertificatesBean coachPicCertificatesBean : zhengshu_images_select) {
                iamge_paths.add(coachPicCertificatesBean.getZheng_local_img_path());
            }
            select_zhengshu_view.setData(iamge_paths);
            AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE = zhengshu_images_select.size();

        }
    }

    private void set_zhengshu_view() {
        //设置拖拽排序控件的代理
        select_shouke_view.setDelegate(select_shouke);
        select_zhengshu_view.setDelegate(select_zhengshu);
    }


    private void set_select_sum(List<TagBean> strings) {
        int select_sum = 0;
        for (int j = 0; j < strings.size(); j++) {
            if (strings.get(j).isCheck()) {
                select_sum++;

            }
        }
        tag_num.setText("(" + select_sum + "/" + "4)");
    }

    private void set_shanchang_select_sum(List<TagBean> strings) {
        int select_sum = 0;
        for (int j = 0; j < strings.size(); j++) {
            if (strings.get(j).isCheck()) {
                select_sum++;
            }
        }
        shanchang_tag_num.setText("(" + select_sum + "/" + strings.size() + ")");
    }

    @Override
    public void fetchData() {
        initView();

    }

    CCRSortableNinePhotoLayout.Delegate select_zhengshu = new CCRSortableNinePhotoLayout.Delegate() {

        @Override
        public void onClickAddNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
            /*if (AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE >= 40) {
                Toast.makeText(getActivity(), "只能上传40张图片哦～", Toast.LENGTH_SHORT).show();
            } else if (AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE < 40) {
                select_zhengshu_max_num = 40 - AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE;
                EasyPhotos.createAlbum(StepTwoFragment.this, true, GlideEngine.getInstance())
                        .setFileProviderAuthority("com.noplugins.keepfit.android.fileprovider")
                        .setPuzzleMenu(false)
                        .setCount(select_zhengshu_max_num)
                        .setOriginalMenu(false, true, null)
                        .start(101);
            }*/
            Intent intent = new Intent(getActivity(), AddZhengshuActivity.class);
            startActivity(intent);
        }

        @Override
        public void onClickDeleteNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
            delete_pop(position);


        }

        @Override
        public void onClickNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {

        }
    };

    /**
     * 删除图片
     *
     * @param position
     */
    private void delete_pop(int position) {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.delete_pop)//我的主页，选择相机camera_pop
                .setBackGroundLevel(0.5f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.MATCH_PARENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(select_zhengshu_view);

        /**设置逻辑*/
        View view = popupWindow.getContentView();
        LinearLayout sure_layout = view.findViewById(R.id.sure_layout);
        sure_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_zhengshu_view.removeItem(position);
                AppConstants.SELECT_PHOTO_NUM.remove(position);
                AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE = AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE - 1;
                popupWindow.dismiss();
            }
        });
        LinearLayout cancel_layout = view.findViewById(R.id.cancel_layout);
        cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        LinearLayout content_layout = view.findViewById(R.id.content_layout);
        content_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }

    CCRSortableNinePhotoLayout.Delegate select_shouke = new CCRSortableNinePhotoLayout.Delegate() {
        @Override
        public void onClickAddNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
            if (AppConstants.SELECT_SHOUKE_IMAGE_SIZE >= 10) {
                Toast.makeText(getActivity(), "只能上传10张图片哦～", Toast.LENGTH_SHORT).show();
            } else if (AppConstants.SELECT_SHOUKE_IMAGE_SIZE < 10) {
                select_shouke_max_num = 10 - AppConstants.SELECT_SHOUKE_IMAGE_SIZE;
                EasyPhotos.createAlbum(StepTwoFragment.this, true, GlideEngine.getInstance())
                        .setFileProviderAuthority("com.noplugins.keepfit.android.fileprovider")
                        .setPuzzleMenu(false)
                        .setCount(select_shouke_max_num)
                        .setOriginalMenu(false, true, null)
                        .start(102);
            }
        }

        @Override
        public void onClickDeleteNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
            select_shouke_view.removeItem(position);
            AppConstants.SELECT_SHOUKE_IMAGE_SIZE = AppConstants.SELECT_SHOUKE_IMAGE_SIZE - 1;
        }

        @Override
        public void onClickNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {

        }
    };

    @Override
    public void onAttach(Context activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof CheckStatusActivity) {
            checkStatusActivity = (CheckStatusActivity) activity;
            stepView = (StepView) checkStatusActivity.findViewById(R.id.step_view);
            viewpager_content = checkStatusActivity.findViewById(R.id.viewpager_content);
        }
    }

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
//                ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
//                zhengshu_images_select.addAll(resultPaths);
//                select_zhengshu_view.setData(zhengshu_images_select);
//                AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE = zhengshu_images_select.size();
                return;
            } else if (requestCode == 102) {
                ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
                for (int i = 0; i < resultPaths.size(); i++) {
                    CheckInformationBean.CoachPicTeachingsBean coachPicTeachingsBean = new CheckInformationBean.CoachPicTeachingsBean();
                    coachPicTeachingsBean.setLocal_iamge_path(resultPaths.get(i));
                    shouke_images_select.add(coachPicTeachingsBean);
                }
                //设置九宫格
                List<String> images = new ArrayList<>();
                for (int i = 0; i < shouke_images_select.size(); i++) {
                    images.add(shouke_images_select.get(i).getLocal_iamge_path());
                }
                select_shouke_view.setData(images);//设置九宫格
                AppConstants.SELECT_SHOUKE_IMAGE_SIZE = shouke_images_select.size();
            }

        } else if (RESULT_CANCELED == resultCode) {
            //Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }
}
