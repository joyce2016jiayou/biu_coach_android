package com.noplugins.keepfit.coachplatform.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.noplugins.keepfit.coachplatform.R;
import com.noplugins.keepfit.coachplatform.activity.AddZhengshuActivity;
import com.noplugins.keepfit.coachplatform.activity.CheckStatusActivity;
import com.noplugins.keepfit.coachplatform.adapter.MineTagAdapter;
import com.noplugins.keepfit.coachplatform.bean.AddPhotoBean;
import com.noplugins.keepfit.coachplatform.bean.CheckInformationBean;
import com.noplugins.keepfit.coachplatform.bean.TagEntity;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.GlideEngine;
import com.noplugins.keepfit.coachplatform.util.MessageEvent;
import com.noplugins.keepfit.coachplatform.util.net.Network;
import com.noplugins.keepfit.coachplatform.util.net.entity.Bean;
import com.noplugins.keepfit.coachplatform.util.net.progress.ProgressSubscriber;
import com.noplugins.keepfit.coachplatform.util.net.progress.SubscriberOnNextListener;
import com.noplugins.keepfit.coachplatform.util.ui.LoadingButton;
import com.noplugins.keepfit.coachplatform.util.ui.NoScrollViewPager;
import com.noplugins.keepfit.coachplatform.util.ui.StepView;
import com.noplugins.keepfit.coachplatform.util.ui.ViewPagerFragment;
import com.noplugins.keepfit.coachplatform.util.ui.jiugongge.CCRSortableNinePhotoLayout;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;
import com.orhanobut.logger.Logger;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import rx.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


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
    private CheckStatusActivity checkStatusActivity;
    private StepView stepView;

    int select_sum = 0;

    private int select_zhengshu_max_num = 0;
    private int select_shouke_max_num = 0;

    private List<CheckInformationBean.CoachPicTeachingsBean> shouke_images_select = new ArrayList<>();
    private List<CheckInformationBean.CoachPicCertificatesBean> zhengshu_images_select = new ArrayList<>();
    private NoScrollViewPager viewpager_content;


    public static StepTwoFragment homeInstance(String title) {
        StepTwoFragment fragment = new StepTwoFragment();
        Bundle args = new Bundle();
        args.putString("home_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_step_two, container, false);
            ButterKnife.bind(this, view);//绑定黄牛刀
            initView();
            EventBus.getDefault().register(StepTwoFragment.this);
        }
        return view;
    }

    private void initView() {
        //设置标签
        set_tag();
        //设置九宫格图片
        set_zhengshu_view();
        submit_btn.setBtnOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zhengshu_images_select.size() == 0) {
                    Toast.makeText(getActivity(), R.string.tv140, Toast.LENGTH_SHORT).show();
                    return;
                } else if (shouke_images_select.size() == 0) {
                    Toast.makeText(getActivity(), R.string.tv141, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    submit_btn.startLoading();
                    submit_btn.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            submit_information();

                            submit_btn.loadingComplete();
                            viewpager_content.setCurrentItem(2);
                            int step = stepView.getCurrentStep();//设置进度条
                            stepView.setCurrentStep((step + 1) % stepView.getStepNum());


                        }
                    }, 2000);
                }


            }
        });


    }

    private void submit_information() {
        CheckInformationBean checkInformationBean = new CheckInformationBean();
        CheckInformationBean.CoachPicCardBean coachPicCardBean = new CheckInformationBean.CoachPicCardBean();
        coachPicCardBean.setCardBackKey(checkStatusActivity.select_card_fan_path);
        coachPicCardBean.setCardFrontKey(checkStatusActivity.select_card_zheng_path);
        checkInformationBean.setCoachPicCard(coachPicCardBean);

        CheckInformationBean.CoachUserBean coachUserBean = new CheckInformationBean.CoachUserBean();
        coachUserBean.setTeacherType(1);//教练类型
        coachUserBean.setRealname(checkStatusActivity.user_name);
        coachUserBean.setCard(checkStatusActivity.card_id);
        coachUserBean.setSex(1);//性别
        coachUserBean.setPhone(checkStatusActivity.phone);
        coachUserBean.setProvince(checkStatusActivity.city);//省份
        coachUserBean.setCity(checkStatusActivity.city);//城市
        coachUserBean.setUniversity(checkStatusActivity.school);
        coachUserBean.setProfessiondate(checkStatusActivity.ruhang_time);
        coachUserBean.setGoodAtSkill("1,2,3");//擅长课程
        coachUserBean.setGoodAtSkill("1,2,3");//技能标签
        coachUserBean.setUserNum("123");//教练编号
        checkInformationBean.setCoachUser(coachUserBean);

        checkInformationBean.setCoachPicTeachings(shouke_images_select);

        checkInformationBean.setCoachPicCertificates(zhengshu_images_select);

        Subscription subscription = Network.getInstance("提交审核资料", getActivity())
                .submit_information(checkInformationBean,
                        new ProgressSubscriber<>("提交审核资料", new SubscriberOnNextListener<Bean<String>>() {
                            @Override
                            public void onNext(Bean<String> result) {

                            }

                            @Override
                            public void onError(String error) {

                            }
                        }, getActivity(), false));
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
            List<String> iamge_paths = new ArrayList<>();
            for (CheckInformationBean.CoachPicCertificatesBean coachPicCertificatesBean : zhengshu_images_select) {
                iamge_paths.add(coachPicCertificatesBean.getCertFrontKey());
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

    private void set_tag() {
        List<TagEntity> strings = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setTag("标签" + (i + 1));
            strings.add(tagEntity);
        }
        MineTagAdapter tagAdapter = new MineTagAdapter(getActivity(), strings);
        grid_view.setAdapter(tagAdapter);
        set_select_sum(strings);
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (strings.get(i).isCheck()) {
                    strings.get(i).setCheck(false);
                } else {
                    strings.get(i).setCheck(true);
                }
                tagAdapter.notifyDataSetChanged();
                set_select_sum(strings);
            }
        });
    }

    private void set_select_sum(List<TagEntity> strings) {
        select_sum = 0;
        for (int j = 0; j < strings.size(); j++) {
            if (strings.get(j).isCheck()) {
                select_sum++;
            }
        }
        tag_num.setText("(" + select_sum + "/" + strings.size() + ")");
    }


    @Override
    public void fetchData() {

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
                    coachPicTeachingsBean.setQiniuKey(resultPaths.get(i));
                    shouke_images_select.add(coachPicTeachingsBean);
                }
                select_shouke_view.setData(resultPaths);//设置九宫格
                AppConstants.SELECT_SHOUKE_IMAGE_SIZE = shouke_images_select.size();
            }

        } else if (RESULT_CANCELED == resultCode) {
            //Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }
}
