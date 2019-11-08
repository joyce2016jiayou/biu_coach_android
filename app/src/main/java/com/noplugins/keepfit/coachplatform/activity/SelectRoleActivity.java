package com.noplugins.keepfit.coachplatform.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.huantansheng.easyphotos.models.puzzle.Line;
import com.noplugins.keepfit.coachplatform.MainActivity;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.base.BaseActivity;
import com.noplugins.keepfit.coachplatform.bean.TeacherStatusBean;
import com.noplugins.keepfit.coachplatform.callback.DialogCallBack;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.SpUtils;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.ui.PopWindowHelper;
import rx.Subscription;

import java.util.HashMap;
import java.util.Map;

public class SelectRoleActivity extends BaseActivity {

    @BindView(R.id.tuanke_quxiao_btn)
    TextView tuanke_quxiao_btn;
    @BindView(R.id.tuanke_sure_btn)
    TextView tuanke_sure_btn;
    @BindView(R.id.cancel_sijiao_btn)
    TextView cancel_sijiao_btn;
    @BindView(R.id.sijiao_sure_btn)
    TextView sijiao_sure_btn;
    @BindView(R.id.select_tuanke_btn)
    LinearLayout select_tuanke_btn;
    @BindView(R.id.select_sijiao_btn)
    LinearLayout select_sijiao_btn;
    @BindView(R.id.select_role_view)
    RelativeLayout select_role_view;
    @BindView(R.id.select_tuanke_view)
    RelativeLayout select_tuanke_view;
    @BindView(R.id.select_sijiao_view)
    RelativeLayout select_sijiao_view;
    int back_number = 0;

    @Override
    public void initBundle(Bundle parms) {
        if (null != parms) {
            back_number = parms.getInt("back");
        }
    }

    @Override
    public void initView() {
        setContentLayout(R.layout.activity_select_role);
        ButterKnife.bind(this);
        isShowTitle(false);
    }

    @Override
    public void doBusiness(Context mContext) {
        select_tuanke_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_role_view.setVisibility(View.GONE);
                select_tuanke_view.setVisibility(View.VISIBLE);
                select_sijiao_view.setVisibility(View.GONE);
            }
        });
        select_sijiao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_role_view.setVisibility(View.GONE);
                select_tuanke_view.setVisibility(View.GONE);
                select_sijiao_view.setVisibility(View.VISIBLE);
            }
        });
        tuanke_quxiao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_role_view.setVisibility(View.VISIBLE);
                select_tuanke_view.setVisibility(View.GONE);
                select_sijiao_view.setVisibility(View.GONE);
            }
        });
        cancel_sijiao_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_role_view.setVisibility(View.VISIBLE);
                select_tuanke_view.setVisibility(View.GONE);
                select_sijiao_view.setVisibility(View.GONE);
            }
        });

        //选则了私教
        sijiao_sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (back_number == 0) {//正常状态进来的
//                    go_to(true);
//                } else {//返回状态进来的
//                    get_teacher_status(true);
//                }
                get_teacher_status(true);

            }
        });
        //选择了团课
        tuanke_sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (back_number == 0) {
//                    go_to(false);
//                } else {//返回状态进来的
//                    get_teacher_status(false);
//                }
                get_teacher_status(false);

            }
        });
    }

    private void go_to(boolean is_tuanke) {
        Intent intent = new Intent(SelectRoleActivity.this, CheckStatusActivity.class);
        if (is_tuanke) {
            SpUtils.putString(getApplicationContext(), AppConstants.SELECT_TEACHER_TYPE, "1");//团课
        } else {
            SpUtils.putString(getApplicationContext(), AppConstants.SELECT_TEACHER_TYPE, "2");//私教
        }
        startActivity(intent);
        //finish();
    }

    private void get_teacher_status(boolean is_tuanke) {
        Map<String, Object> params = new HashMap<>();
        params.put("userNum", SpUtils.getString(getApplicationContext(), AppConstants.SELECT_TEACHER_NUMBER));
        subscription = Network.getInstance("获取教练状态", this)
                .get_teacher_status(params,
                        new ProgressSubscriber<>("获取教练状态", new SubscriberOnNextListener<Bean<TeacherStatusBean>>() {
                            @Override
                            public void onNext(Bean<TeacherStatusBean> result) {
//                        teacherType 1团课 2私教 3都有
//                        pType 私教 1 通过2拒绝3审核中
//                        lType 团课 1 通过2拒绝3审核中
//                        sign 是否签约上架 1 是 0 否
                                if (is_tuanke) {//团课
                                    if (result.getData().getTeacherType() == 1) {
                                        //获取状态
                                        if (result.getData().getLType() == 1) {//通过的话就直接签约
                                            SpUtils.putString(SelectRoleActivity.this, AppConstants.IS_SUBMIT_TUANKE, "true");

                                            Intent intent = new Intent(SelectRoleActivity.this, CheckStatusActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("into_index", 3);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            //finish();
                                        } else if (result.getData().getLType() == 3) {//审核中
                                            Intent intent = new Intent(SelectRoleActivity.this, CheckStatusActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("into_index", 2);
                                            bundle.putInt("status", 1);//审核中
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            //finish();
                                        } else if (result.getData().getLType() == 2) {//拒绝
                                            Intent intent = new Intent(SelectRoleActivity.this, CheckStatusActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("into_index", 2);
                                            bundle.putInt("status", -1);//拒绝
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            //finish();
                                        }
                                    } else if (result.getData().getTeacherType() == 3) {
                                        if (result.getData().getSign() == 1) {//已签约
                                            Intent intent = new Intent(SelectRoleActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(SelectRoleActivity.this, CheckStatusActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("into_index", 3);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        go_to(true);
                                    }
                                } else {//私教
                                    if (result.getData().getTeacherType() == 2) {
                                        //获取状态
                                        if (result.getData().getPType() == 1) {//通过的话就直接签约
                                            SpUtils.putString(SelectRoleActivity.this, AppConstants.IS_SUBMIT_SIJIAO, "true");
                                            Intent intent = new Intent(SelectRoleActivity.this, CheckStatusActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("into_index", 3);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            //finish();
                                        } else if (result.getData().getPType() == 3) {//审核中
                                            Intent intent = new Intent(SelectRoleActivity.this, CheckStatusActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("into_index", 2);
                                            bundle.putInt("status", 1);//审核中
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            //finish();
                                        } else if (result.getData().getPType() == 2) {//拒绝
                                            Intent intent = new Intent(SelectRoleActivity.this, CheckStatusActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("into_index", 2);
                                            bundle.putInt("status", -1);//拒绝
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            //finish();
                                        }
                                    } else if (result.getData().getTeacherType() == 3) {
                                        if (result.getData().getSign() == 1) {//已签约
                                            Intent intent = new Intent(SelectRoleActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(SelectRoleActivity.this, CheckStatusActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("into_index", 3);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        go_to(false);
                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, this, false));
    }

    @Override
    public void onBackPressed() {
        show_advice_pop();
    }

    private void show_advice_pop() {
        PopWindowHelper.public_tishi_pop(SelectRoleActivity.this, "温馨提示", "是否退出app？", "取消", "确定", new DialogCallBack() {
            @Override
            public void save() {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }

            @Override
            public void cancel() {

            }
        });
    }
}
