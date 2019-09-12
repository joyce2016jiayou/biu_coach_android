package com.noplugins.keepfit.coachplatform.fragment;


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
import com.noplugins.keepfit.coachplatform.adapter.MineTagAdapter;
import com.noplugins.keepfit.coachplatform.bean.TagEntity;
import com.noplugins.keepfit.coachplatform.global.AppConstants;
import com.noplugins.keepfit.coachplatform.util.GlideEngine;
import com.noplugins.keepfit.coachplatform.util.ui.ViewPagerFragment;
import com.noplugins.keepfit.coachplatform.util.ui.jiugongge.CCRSortableNinePhotoLayout;
import com.noplugins.keepfit.coachplatform.util.ui.pop.CommonPopupWindow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    int select_sum = 0;

    private int select_zhengshu_max_num = 0;
    private int select_shouke_max_num = 0;

    private List<String> shouke_images_select = new ArrayList<>();
    private List<String> zhengshu_images_select = new ArrayList<>();


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
        }
        return view;
    }

    private void initView() {
        //设置标签
        set_tag();
        //设置九宫格图片
        set_zhengshu_view();


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
            select_zhengshu_view.removeItem(position);
            AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE = AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE - 1;
        }

        @Override
        public void onClickNinePhotoItem(CCRSortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {

        }
    };

    /**
     * 删除图片
     */
    private void delete_pop() {
        CommonPopupWindow popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.camera_pop)
                .setBackGroundLevel(0.5f)//0.5f
                .setAnimationStyle(R.style.main_menu_animstyle)
                .setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT)
                .setOutSideTouchable(true).create();
        popupWindow.showAsDropDown(select_zhengshu_view);

        /**设置逻辑*/
        View view = popupWindow.getContentView();


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
                zhengshu_images_select.addAll(resultPaths);
                select_zhengshu_view.setData(zhengshu_images_select);//设置九宫格
                AppConstants.SELECT_ZHENGSHU_IMAGE_SIZE = zhengshu_images_select.size();
                return;
            } else if (requestCode == 102) {
                ArrayList<String> resultPaths = data.getStringArrayListExtra(EasyPhotos.RESULT_PATHS);
                shouke_images_select.addAll(resultPaths);
                select_shouke_view.setData(shouke_images_select);//设置九宫格
                AppConstants.SELECT_SHOUKE_IMAGE_SIZE = shouke_images_select.size();
            }

        } else if (RESULT_CANCELED == resultCode) {
            //Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
        }
    }
}
