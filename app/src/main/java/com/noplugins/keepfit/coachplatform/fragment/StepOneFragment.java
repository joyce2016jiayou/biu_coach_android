package com.noplugins.keepfit.coachplatform.fragment;

import android.annotation.SuppressLint;
import android.app.ListActivity;
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
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
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
import com.noplugins.keepfit.coachplatform.bean.*;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.GlideEngine;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.data.DateHelper;
import com.noplugins.keepfit.coachplatform.util.data.IDCardValidate;
import com.noplugins.keepfit.coachplatform.util.data.StringsHelper;
import com.noplugins.keepfit.coachplatform.util.net.GetJsonDataUtil;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.screen.KeyboardUtils;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;
import com.noplugins.keepfit.coachplatform.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.coachplatform.util.ui.StepView;
import com.noplugins.keepfit.coachplatform.util.ui.ViewPagerFragment;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import org.json.JSONArray;
import rx.Subscription;

import java.io.File;
import java.util.*;

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
    TextView school_tv;
    @BindView(R.id.select_school_btn)
    RelativeLayout select_school_btn;

    OptionsPickerView sex_select_pop, select_city_pop, select_xueli_pop;
    TimePickerView pvCustomTime;
    private ArrayList<String> sexs = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<String> options2Items = new ArrayList<>();
    private ArrayList<JsonBean.CityBean> options2Items_codes = new ArrayList<>();
    private ArrayList<String> options3Items = new ArrayList<>();
    private ArrayList<JsonBean.QuBean> options3Items_codes = new ArrayList<>();
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
    List<ZiDIanBean> xuelis = new ArrayList<>();
    private String select_school_str = "";
    public TextView top_title_tv;
    private String select_sheng_code = "";
    private String select_shi_code = "";
    private String select_qu_code = "";

    private int select_province_position = 0;
    private int select_city_position = 0;

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
            top_title_tv = checkStatusActivity.findViewById(R.id.top_title_tv);
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
        } else if (!IDCardValidate.validate_effective(card_id_tv.getText().toString())) {
            Toast.makeText(getActivity(), R.string.tv152, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(sex_tv.getText())) {
            Toast.makeText(getActivity(), R.string.tv134, Toast.LENGTH_SHORT).show();
            return false;
        } else if (TextUtils.isEmpty(phone_tv.getText())) {
            Toast.makeText(getActivity(), R.string.tv135, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!StringsHelper.isMobileOne(phone_tv.getText().toString())) {
            Toast.makeText(getActivity(), "电话号码格式不正确！", Toast.LENGTH_SHORT).show();
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
        //显示缓存好的手机号
        phone_tv.setText(SpUtils.getString(getActivity(), AppConstants.PHONE));
        //获取最高学历字典
        get_zuigao_xueli();
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
                    checkStatusActivity.school = select_school_str;
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
                    checkStatusActivity.select_index = 1;
                    top_title_tv.setText("教练入驻");
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
                initCityDate(true);
            }
        });

        //选择身份证正面
        select_card_zheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_set_card_zheng = true;
                camera_pop_window();
                //IDCardCamera.create(StepOneFragment.this).openCamera(IDCardCamera.TYPE_IDCARD_FRONT);
            }
        });
        select_card_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_set_card_zheng = false;
                camera_pop_window();
                //IDCardCamera.create(StepOneFragment.this).openCamera(IDCardCamera.TYPE_IDCARD_BACK);
            }
        });
        //选择最高学历
        select_school_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_xueli_pop();
            }
        });


    }

    private void initCityDate(boolean is_refresh_start) {
        Map<String, Object> params = new HashMap<>();
        params.put("province", "1");
        Subscription subscription = Network.getInstance("获取省", getActivity())
                .get_province(params,
                        new ProgressSubscriber<>("获取省", new SubscriberOnNextListener<Bean<CityCode>>() {
                            @Override
                            public void onNext(Bean<CityCode> result) {
                                for (int i = 0; i < result.getData().getProvince().size(); i++) {
                                    JsonBean jsonBean = new JsonBean();
                                    jsonBean.setName(result.getData().getProvince().get(i).getPrvncnm());
                                    jsonBean.setName_code(result.getData().getProvince().get(i).getPrvnccd());
                                    options1Items.add(jsonBean);
                                }
                                select_sheng_code = options1Items.get(0).getName_code();
                                initCityDate2(select_sheng_code, is_refresh_start);

                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
    }

    private void initCityDate2(String select_sheng_code, boolean is_refresh_start) {
        if (options2Items_codes.size() > 0) {
            options2Items_codes.clear();
        }
        if (options2Items.size() > 0) {
            options2Items.clear();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("prvnccd", select_sheng_code);
        Subscription subscription = Network.getInstance("获取市", getActivity())
                .get_city(params,
                        new ProgressSubscriber<>("获取市", new SubscriberOnNextListener<Bean<GetCityCode>>() {
                            @Override
                            public void onNext(Bean<GetCityCode> result) {
                                for (int i = 0; i < result.getData().getCity().size(); i++) {
                                    JsonBean.CityBean jsonBean = new JsonBean.CityBean();
                                    jsonBean.setName(result.getData().getCity().get(i).getCitynm());
                                    jsonBean.setName_code(result.getData().getCity().get(i).getCitycd());
                                    options2Items_codes.add(jsonBean);
                                    options2Items.add(result.getData().getCity().get(i).getCitynm());
                                }
                                select_shi_code = options2Items_codes.get(0).getName_code();
                                select_city_position = 0;
                                if (null != select_city_pop) {
                                    select_city_pop.setSelectOptions(select_province_position, select_city_position);
                                    select_city_pop.setNPicker(options1Items, options2Items, options3Items);//二级选择器
                                }
                                initCityDate3(select_shi_code, is_refresh_start);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
    }

    private void get_zuigao_xueli() {
        Map<String, Object> params = new HashMap<>();
        params.put("object", 10);
        Subscription subscription = Network.getInstance("选择最高学历", getActivity())
                .get_zidian(params,
                        new ProgressSubscriber<>("选择最高学历", new SubscriberOnNextListener<Bean<List<ZiDIanBean>>>() {
                            @Override
                            public void onNext(Bean<List<ZiDIanBean>> result) {
                                xuelis = result.getData();
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
    }

    private void select_xueli_pop() {
        List<String> xuelis_str = new ArrayList<>();
        for (int i = 0; i < xuelis.size(); i++) {
            xuelis_str.add(xuelis.get(i).getName());
        }
        select_xueli_pop = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String select_sex = xuelis.get(options1).getName();
                select_school_str = xuelis.get(options1).getValue() + "";
                school_tv.setText(select_sex);
                Log.e("选中的学历", select_school_str);
            }
        })
                .setLayoutRes(R.layout.sex_select_pop, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView quxiao_btn = (TextView) v.findViewById(R.id.quxiao_btn);
                        quxiao_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                select_xueli_pop.dismiss();
                            }
                        });
                        TextView sure_btn = (TextView) v.findViewById(R.id.sure_btn);
                        sure_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                select_xueli_pop.returnData();
                                select_xueli_pop.dismiss();
                            }
                        });
                    }
                })
                .isDialog(false)
                .setBgColor(Color.parseColor("#00000000"))
                .setDividerColor(Color.parseColor("#00000000"))
                .setOutSideCancelable(true)
                .build();

        select_xueli_pop.setPicker(xuelis_str);//添加数据
        select_xueli_pop.show();

        //影藏键盘
        KeyboardUtils.hideSoftKeyboard(getActivity());
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


    private void showPickerView() {
        select_city_pop = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.size() > 0 ?
                        options2Items.get(option2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.size() > 0
                        && options3Items.size() > 0 ?
                        options3Items.get(options3) : "";


                String tx = opt1tx + opt2tx + opt3tx;
                if (opt1tx.equals(opt2tx)) {
                    address_tv.setText(opt1tx + opt3tx);

                } else {
                    address_tv.setText(opt1tx + opt2tx + opt3tx);

                }

                Log.e("选择的地址", tx);
                Log.e("选择的地址编码", select_sheng_code + "-" + select_shi_code + "-" + select_qu_code);

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
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1) {
                        select_province_position = options1;
                        select_sheng_code = options1Items.get(options1).getName_code();
                        initCityDate2(select_sheng_code, false);


                    }

                    @Override
                    public void onOptionsSelectChanged2(int options2) {
                        select_city_position = options2;
                        select_shi_code = options2Items_codes.get(options2).getName_code();
                        initCityDate3(select_shi_code, false);
                    }

                    @Override
                    public void onOptionsSelectChanged3(int options3) {
                        select_qu_code = options3Items_codes.get(options3).getName_code();
                    }

                    @Override
                    public void onOptionsSelectChanged3(int options1, int options2, int options3) {

                    }

                })
                .build();
        select_city_pop.setNPicker(options1Items, options2Items, options3Items);//二级选择器
        select_city_pop.show();
    }

    private void initCityDate3(String select_shi_code, boolean is_refresh_start) {
        if (options3Items.size() > 0) {
            options3Items.clear();
        }
        if (options3Items_codes.size() > 0) {
            options3Items_codes.clear();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("citycd", select_shi_code);
        Subscription subscription = Network.getInstance("获取区", getActivity())
                .get_qu(params,
                        new ProgressSubscriber<>("获取区", new SubscriberOnNextListener<Bean<GetQuCode>>() {
                            @Override
                            public void onNext(Bean<GetQuCode> result) {

                                for (int i = 0; i < result.getData().getArea().size(); i++) {
                                    JsonBean.QuBean jsonBean = new JsonBean.QuBean();
                                    jsonBean.setName(result.getData().getArea().get(i).getDistnm());
                                    jsonBean.setName_code(result.getData().getArea().get(i).getDistcd());
                                    options3Items.add(result.getData().getArea().get(i).getDistnm());
                                    options3Items_codes.add(jsonBean);
                                }
                                if (null != select_city_pop) {
                                    select_city_pop.setSelectOptions(select_province_position, select_city_position);
                                    select_city_pop.setNPicker(options1Items, options2Items, options3Items);//二级选择器
                                }
                                if (is_refresh_start) {//如果是打开pop
                                    select_address_pop();
                                }


                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
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
        startDate.set(1980, 1, 1);
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
        showPickerView();
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

        /*if (resultCode == IDCardCamera.RESULT_CODE) {
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
        }*/
        if (RESULT_OK == resultCode) {
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
        }
    }


}
