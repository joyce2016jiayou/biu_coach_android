package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.noplugins.keepfit.coachplatform.MainActivity;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.adapter.DailyTagAdapter;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.bean.DairyBean;
import com.noplugins.keepfit.coachplatform.bean.GetDailryBean;
import com.noplugins.keepfit.coachplatform.bean.LoginBean;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.BaseUtils;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.ui.GridViewForScrollView;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;
import com.umeng.socialize.media.Base;
import rx.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteDailryActivity extends BaseActivity {
    @BindView(R.id.back_img)
    ImageView back_img;
    @BindView(R.id.grid_view)
    GridView grid_view;
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.cource_name)
    TextView cource_name;
    @BindView(R.id.time_tv)
    TextView time_tv;
    @BindView(R.id.cource_area)
    TextView cource_area;
    @BindView(R.id.ruchang_biaoqing_img)
    ImageView ruchang_biaoqing_img;
    @BindView(R.id.chuchang_biaoqing_img)
    ImageView chuchang_biaoqing_img;
    @BindView(R.id.ruchang_time)
    TextView ruchang_time;
    @BindView(R.id.lichang_time)
    TextView lichang_time;
    @BindView(R.id.shengao_edit)
    EditText shengao_edit;
    @BindView(R.id.tizhong_edit)
    EditText tizhong_edit;
    @BindView(R.id.bmi_edit)
    TextView bmi_edit;
    @BindView(R.id.add_btn)
    LoadingButton add_btn;
    @BindView(R.id.class_content)
    EditText class_content;
    @BindView(R.id.tizhi_edit)
    EditText tizhi_edit;
    @BindView(R.id.yaotunbi_edit)
    EditText yaotunbi_edit;
    @BindView(R.id.daixie_edit)
    EditText daixie_edit;
    String order_key = "";
    List<GetDailryBean.LableListBean> lableListBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initBundle(Bundle parms) {
        if (parms != null) {
            order_key = parms.getString("order_key");
        }
    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_write_dailry);
        ButterKnife.bind(this);
        isShowTitle(false);
        shengao_edit.setInputType(InputType.TYPE_CLASS_NUMBER);
        tizhong_edit.setInputType(InputType.TYPE_CLASS_NUMBER);
        bmi_edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    @Override
    public void doBusiness(Context mContext) {
        init_dailry_resource();

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tizhong_edit.addTextChangedListener(tizhong_textWatcher);
        shengao_edit.addTextChangedListener(tizhong_textWatcher);

        add_btn.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_btn.startLoading();
                submit_resource();
            }
        });

    }

    private void submit_resource() {
        DairyBean dairyBean = new DairyBean();
        dairyBean.setOrdItemNum(order_key);
        dairyBean.setCourseDes(class_content.getText().toString());
        StringBuffer stringBuffer = new StringBuffer();
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < lableListBeans.size(); i++) {
            if (lableListBeans.get(i).isCheck()) {
                ids.add(lableListBeans.get(i).getId() + "");
            }
        }
        for (int i = 0; i < ids.size(); i++) {
            if (i == ids.size() - 1) {
                stringBuffer.append(ids.get(i));
            } else {
                stringBuffer.append(ids.get(i)).append(",");
            }
        }
        Log.e("呵呵", stringBuffer.toString() + "");
        dairyBean.setLabel(stringBuffer.toString());
        dairyBean.setStature(shengao_edit.getText().toString());
        dairyBean.setWeight(tizhong_edit.getText().toString());
        dairyBean.setBodyfat(tizhi_edit.getText().toString());
        dairyBean.setBmi(bmi_edit.getText().toString());
        dairyBean.setWaistratebuttocks(yaotunbi_edit.getText().toString());//腰臀比
        dairyBean.setKcal(daixie_edit.getText().toString());
        Subscription subscription = Network.getInstance("提交详情数据", this)
                .submit_tice(dairyBean,
                        new ProgressSubscriber<>("提交详情数据", new SubscriberOnNextListener<Bean<String>>() {
                            @Override
                            public void onNext(Bean<String> result) {
                                add_btn.loadingComplete();
                                Toast.makeText(getApplicationContext(), "新增日志成功~", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onError(String error) {
                                add_btn.loadingComplete();

                            }
                        }, this, false));
    }

    TextWatcher tizhong_textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (tizhong_edit.getText().length() > 0 && shengao_edit.getText().length() > 0) {
                double height = Double.parseDouble(shengao_edit.getText().toString()) / 100;
                double weight = Double.parseDouble(tizhong_edit.getText().toString());
                double BMI = weight / (height * height);
                bmi_edit.setText(BMI + "");
            }

        }
    };

    private void init_dailry_resource() {
        Map<String, Object> params = new HashMap<>();
        params.put("ordItemNum", order_key);
        Subscription subscription = Network.getInstance("获取详情数据", this)
                .get_trail_detail(params,
                        new ProgressSubscriber<>("获取详情数据", new SubscriberOnNextListener<Bean<GetDailryBean>>() {
                            @Override
                            public void onNext(Bean<GetDailryBean> result) {
                                set_daily_detail(result.getData());
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));

    }

    private void set_daily_detail(GetDailryBean getDailryBean) {
        title_tv.setText(getDailryBean.getUserName() + "的运动日志");
        cource_name.setText(getDailryBean.getCourseName());
        time_tv.setText(getDailryBean.getBiuTime());
        cource_area.setText(getDailryBean.getAreaName());
        //设置入场表情
        int ruchang_img_id = getDailryBean.getBeforeFace();
        if (ruchang_img_id != -1) {//没有表情
            if (ruchang_img_id == 1) {
                ruchang_biaoqing_img.setImageResource(R.drawable.xiaolian1);
            } else if (ruchang_img_id == 2) {
                ruchang_biaoqing_img.setImageResource(R.drawable.xiaolian2);
            } else if (ruchang_img_id == 3) {
                ruchang_biaoqing_img.setImageResource(R.drawable.xiaolian3);
            } else if (ruchang_img_id == 4) {
                ruchang_biaoqing_img.setImageResource(R.drawable.xiaolian4);
            } else if (ruchang_img_id == 5) {
                ruchang_biaoqing_img.setImageResource(R.drawable.xiaolian5);
            }
        }
        ruchang_time.setText(getDailryBean.getCheckIn());
        //设置出场表情
        int chuchang_img_id = getDailryBean.getAfterFace();
        if (chuchang_img_id != -1) {
            if (chuchang_img_id == 1) {
                chuchang_biaoqing_img.setImageResource(R.drawable.xiaolian1);
            } else if (chuchang_img_id == 2) {
                chuchang_biaoqing_img.setImageResource(R.drawable.xiaolian2);
            } else if (chuchang_img_id == 3) {
                chuchang_biaoqing_img.setImageResource(R.drawable.xiaolian3);
            } else if (chuchang_img_id == 4) {
                chuchang_biaoqing_img.setImageResource(R.drawable.xiaolian4);
            } else if (chuchang_img_id == 5) {
                chuchang_biaoqing_img.setImageResource(R.drawable.xiaolian5);
            }
        }


        lichang_time.setText(getDailryBean.getCheckOut());
        //设置标签
        lableListBeans = getDailryBean.getLableList();
        DailyTagAdapter tagAdapter = new DailyTagAdapter(this, lableListBeans);
        grid_view.setAdapter(tagAdapter);
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GetDailryBean.LableListBean labelEntity = lableListBeans.get(i);
                if (labelEntity.isCheck()) {
                    labelEntity.setCheck(false);
                } else {
                    labelEntity.setCheck(true);
                }
                tagAdapter.notifyDataSetChanged();
            }
        });

    }
}
