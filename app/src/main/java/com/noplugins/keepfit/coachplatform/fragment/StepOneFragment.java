package com.noplugins.keepfit.coachplatform.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.activity.CheckStatusActivity;
import com.noplugins.keepfit.coachplatform.bean.JsonBean;
import com.noplugins.keepfit.coachplatform.util.GlideEngine;
import com.noplugins.keepfit.coachplatform.util.data.DateHelper;
import com.noplugins.keepfit.coachplatform.util.net.GetJsonDataUtil;
import com.noplugins.keepfit.coachplatform.util.screen.KeyboardUtils;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;
import com.noplugins.keepfit.coachplatform.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.coachplatform.util.ui.StepView;
import com.noplugins.keepfit.coachplatform.util.ui.ViewPagerFragment;
import com.noplugins.keepfit.coachplatform.util.ui.jiugongge.CCRSortableNinePhotoLayout;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import com.wildma.idcardcamera.camera.IDCardCamera;
import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class StepOneFragment extends ViewPagerFragment {
    private View view;
    @BindView(R.id.xiayibu_btn)
    LoadingButton xiayibu_btn;
    @BindView(R.id.select_sex_btn)
    RelativeLayout select_sex_btn;
    @BindView(R.id.select_address_btn)
    RelativeLayout select_address_btn;
    @BindView(R.id.time_select_btn)
    RelativeLayout time_select_btn;
    @BindView(R.id.sex_tv)
    TextView sex_tv;
    @BindView(R.id.address_tv)
    TextView address_tv;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.select_card_zheng)
    RelativeLayout select_card_zheng;
    @BindView(R.id.select_card_fan)
    RelativeLayout select_card_fan;
    @BindView(R.id.card_zheng_img)
    ImageView card_zheng_img;
    @BindView(R.id.card_zheng_view)
    ImageView card_zheng_view;
    @BindView(R.id.card_fan_img)
    ImageView card_fan_img;
    @BindView(R.id.card_fan_view)
    ImageView card_fan_view;
    @BindView(R.id.user_name_tv)
    EditText user_name_tv;
    @BindView(R.id.card_id_tv)
    EditText card_id_tv;
    @BindView(R.id.phone_tv)
    EditText phone_tv;
    @BindView(R.id.school_tv)
    EditText school_tv;

    OptionsPickerView sex_select_pop, select_city_pop;
    TimePickerView pvCustomTime;
    private ArrayList<String> sexs = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static boolean isLoaded = false;
    private List<String> strings = new ArrayList<>();
    private String select_card_zheng_path = "";
    private String select_card_fan_path = "";
    private StepView stepView;
    private boolean is_set_card_zheng;
    private CheckStatusActivity checkStatusActivity;
    private NoScrollViewPager viewpager_content;
    private String select_time_tv = "";

    public static StepOneFragment homeInstance(String title) {
        StepOneFragment fragment = new StepOneFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_step_one, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
        }
        return view;
    }

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

    private boolean check_value() {
        if (TextUtils.isEmpty(select_card_zheng_path)) {
            Toast.makeText(getActivity(), R.string.tv130, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(select_card_fan_path)) {
            Toast.makeText(getActivity(), R.string.tv131, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(user_name_tv.getText())) {
            Toast.makeText(getActivity(), R.string.tv132, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(card_id_tv.getText())) {
            Toast.makeText(getActivity(), R.string.tv133, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(sex_tv.getText())) {
            Toast.makeText(getActivity(), R.string.tv134, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(phone_tv.getText())) {
            Toast.makeText(getActivity(), R.string.tv135, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(address_tv.getText())) {
            Toast.makeText(getActivity(), R.string.tv136, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(school_tv.getText())) {
            Toast.makeText(getActivity(), R.string.tv138, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(time_tv.getText())) {
            Toast.makeText(getActivity(), R.string.tv139, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }


    private void initView() {
        //解析城市数据
        initDate();

        xiayibu_btn.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (check_value()) {
                    //传递参数
                    checkStatusActivity.select_card_zheng_path = select_card_zheng_path;
                    checkStatusActivity.select_card_fan_path = select_card_fan_path;
                    checkStatusActivity.user_name = user_name_tv.getText().toString();
                    checkStatusActivity.card_id = card_id_tv.getText().toString();
                    checkStatusActivity.sex = sex_tv.getText().toString();
                    checkStatusActivity.phone = phone_tv.getText().toString();
                    checkStatusActivity.city = address_tv.getText().toString();
                    checkStatusActivity.school = school_tv.getText().toString();
                    checkStatusActivity.ruhang_time = select_time_tv;

                    xiayibu_btn.startLoading();
                    xiayibu_btn.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            xiayibu_btn.loadingComplete();
                        }
                    }, 1000);

                    //跳转下一个页面
                    viewpager_content.setCurrentItem(1);
                    int step = stepView.getCurrentStep();//设置进度条
                    stepView.setCurrentStep((step + 1) % stepView.getStepNum());
                }
            }
        });

        //选择性别
        select_sex_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_sex_pop();
            }
        });
        //选择入行时间
        time_select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_time_pop();
            }
        });
        //地址选择
        select_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_address_pop();
            }
        });

        //选择身份证正面
        select_card_zheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_set_card_zheng = true;
                //camera_pop_window();
                IDCardCamera.create(StepOneFragment.this).openCamera(IDCardCamera.TYPE_IDCARD_FRONT);

            }
        });
        select_card_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_set_card_zheng = false;
                //camera_pop_window();
                IDCardCamera.create(StepOneFragment.this).openCamera(IDCardCamera.TYPE_IDCARD_BACK);

            }
        });


    }

    private void camera_pop_window() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.camera_pop)
                .setBackGroundLevel(0.5f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(select_card_zheng);

        /**设置逻辑*/
        View view = popupWindow.getContentView();
        TextView paizhao_btn = view.findViewById(R.id.paizhao_btn);
        TextView xiangce_btn = view.findViewById(R.id.xiangce_btn);
        TextView cancel_btn = view.findViewById(R.id.cancel_btn);
        RelativeLayout pop_bg = view.findViewById(R.id.pop_bg);
        pop_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        paizhao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyPhotos.createCamera(StepOneFragment.this)
                        .setFileProviderAuthority("com.noplugins.keepfit.coachplatform.fileprovider")
                        .start(102);

                popupWindow.dismiss();
            }
        });
        xiangce_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyPhotos.createAlbum(StepOneFragment.this, true, GlideEngine.getInstance())
                        .setFileProviderAuthority("com.noplugins.keepfit.android.fileprovider")
                        .setPuzzleMenu(false)
                        .setCount(1)
                        .setOriginalMenu(false, true, null)
                        .start(102);

                popupWindow.dismiss();
            }
        });

    }

    private void initDate() {
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         */
        String JsonData = new GetJsonDataUtil().getJson(getActivity(), "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }


    private void showPickerView() {
        select_city_pop = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(option2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(option2).size() > 0 ?
                        options3Items.get(options1).get(option2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
                address_tv.setText(opt1tx + opt2tx);

                Log.e("选择的地址", tx);
            }
        })
                .setLayoutRes(R.layout.select_city_pop, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView quxiao_btn = (TextView) v.findViewById(R.id.quxiao_btn);
                        TextView sure_btn = (TextView) v.findViewById(R.id.sure_btn);
                        sure_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                select_city_pop.returnData();
                                select_city_pop.dismiss();
                            }
                        });
                        quxiao_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                select_city_pop.dismiss();
                            }
                        });
                    }
                })
                .setBgColor(Color.parseColor("#00000000"))
                .setDividerColor(Color.parseColor("#00000000"))
                .setContentTextSize(20)
                .setOutSideCancelable(true)
                .setLineSpacingMultiplier(2.0f)
                .build();

        select_city_pop.setPicker(options1Items, options2Items);//二级选择器
        select_city_pop.show();
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        //Toast.makeText(getActivity(), "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initDate();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    //Toast.makeText(getActivity(), "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    //Toast.makeText(getActivity(), "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
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
        pvCustomTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Log.e("选择的时间", date.toString());
                int select_year = date.getYear() + 1900;
                String select_month = date.getMonth() + "";
                if (Integer.valueOf(select_month) < 9) {
                    select_month = "0" + (date.getMonth() + 1);
                } else {
                    select_month = "" + (date.getMonth() + 1);
                }
                select_time_tv = select_year + "-" + select_month;
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
        KeyboardUtils.hideSoftKeyboard(getActivity());
    }

    /**
     * 选择性别pop
     */
    private void select_sex_pop() {
        sexs.clear();
        sexs.add("男");
        sexs.add("女");
        sex_select_pop = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String select_sex = sexs.get(options1);
                sex_tv.setText(select_sex);
                Log.e("选中的性别", select_sex);
            }
        })
                .setLayoutRes(R.layout.sex_select_pop, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView quxiao_btn = (TextView) v.findViewById(R.id.quxiao_btn);
                        quxiao_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sex_select_pop.dismiss();
                            }
                        });
                        TextView sure_btn = (TextView) v.findViewById(R.id.sure_btn);
                        sure_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sex_select_pop.returnData();
                                sex_select_pop.dismiss();
                            }
                        });
                    }
                })
                .isDialog(false)
                .setBgColor(Color.parseColor("#00000000"))
                .setDividerColor(Color.parseColor("#00000000"))
                .setOutSideCancelable(true)
                .build();

        sex_select_pop.setPicker(sexs);//添加数据
        sex_select_pop.show();

        //影藏键盘
        KeyboardUtils.hideSoftKeyboard(getActivity());

    }

    /**
     * 选择城市pop
     */
    private void select_address_pop() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);//加载城市json数据
        if (isLoaded) {
            showPickerView();
        } else {
            //Toast.makeText(getActivity(), "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
        }
        //影藏键盘
        KeyboardUtils.hideSoftKeyboard(getActivity());
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String is_front = bundle.getString("is_front");
            if (is_front.equals("true")) {
                File icon_iamge_file = new File(select_card_zheng_path);
                card_zheng_view.setVisibility(View.GONE);
                card_zheng_img.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(icon_iamge_file).into(card_zheng_img);

            } else {
                File icon_iamge_file_1 = new File(select_card_fan_path);
                card_fan_view.setVisibility(View.GONE);
                card_fan_img.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(icon_iamge_file_1).into(card_fan_img);
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == IDCardCamera.RESULT_CODE) {
            //获取图片路径，显示图片
            String path = IDCardCamera.getImagePath(data);
            Log.e("拍照的地址：", path);
            if (!TextUtils.isEmpty(path)) {
                if (requestCode == IDCardCamera.TYPE_IDCARD_FRONT) { //身份证正面
                    select_card_zheng_path = path;

                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("is_front", "true");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    };
                    thread.start();


                } else if (requestCode == IDCardCamera.TYPE_IDCARD_BACK) {  //身份证反面
                    select_card_fan_path = path;

                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putString("is_front", "false");
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                    };
                    thread.start();


                }
            }
        }

        /*if (RESULT_OK == resultCode) {
            //相机或相册回调
            if (requestCode == 101) {
                //返回对象集合：如果你需要了解图片的宽、高、大小、用户是否选中原图选项等信息，可以用这个
                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
//                for (int i = 0; i < resultPhotos.size(); i++) {
//                    Log.e("图片地址", resultPhotos.get(i).path);
//                }
                //返回图片地址集合：如果你只需要获取图片的地址，可以用这个
                ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
                //返回图片地址集合时如果你需要知道用户选择图片时是否选择了原图选项，用如下方法获取
                boolean selectedOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false);
                strings.addAll(resultPaths);

                return;
            } else {
                ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
                if (resultPaths.size() > 0) {
                    String img_path = resultPaths.get(0);
                    Log.e("选择照片的地址", select_card_zheng_path);

                    if (is_set_card_zheng) {//设置身份证正面
                        select_card_zheng_path = img_path;
                        File icon_iamge_file = new File(select_card_zheng_path);
                        card_zheng_view.setVisibility(View.GONE);
                        card_zheng_img.setVisibility(View.VISIBLE);
                        Glide.with(getActivity()).load(icon_iamge_file).into(card_zheng_img);
                    } else {
                        select_card_fan_path = img_path;
                        File icon_iamge_file = new File(select_card_fan_path);
                        card_fan_view.setVisibility(View.GONE);
                        card_fan_img.setVisibility(View.VISIBLE);
                        Glide.with(getActivity()).load(icon_iamge_file).into(card_fan_img);

                    }

                }

            }


        } else if (RESULT_CANCELED == resultCode) {
            //Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }*/
    }


}
