package com.noplugins.keepfit.coachplatform.util.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.*;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import com.noplugins.keepfit.coachplatform.R;

public class BxDialog extends AlertDialog {

    private static final String TAG = "BxDialog";

    private Context mContext;
    private int layoutResID;
    private final SparseArray<View> mViews;     //android推荐使用的键值对数组
    private boolean mIsFinish = false;
    private BxDialogInterf mStatusListener;
    private CanceledOnTouchOutsideInterf mCanceledListener;

    public BxDialog(Context context) {
        super(context, R.style.BxDialog);
        mContext = context;
        mViews = new SparseArray<>();
    }

    public BxDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
        mViews = new SparseArray<>();
    }

    /**
     * 传入布局文件
     * @param layoutResID
     * &nbsp&nbsp&nbsp &nbsp&nbsp&nbsp 在第二个Layout中写代码，类似activity中的xml文件 <br/>
     */
    public BxDialog loadLayout(int layoutResID) {
        this.layoutResID = layoutResID;
        show();
        dismiss();
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        setCanceledOnTouchOutside(false);
        matchWidth();
    }

    /**
     * 宽度填充屏幕
     */
    private void matchWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = dm.widthPixels;
//        p.height = dm.heightPixels - getStatusBarHeight() - getNavigationBarHeight(mContext);
        getWindow().setAttributes(p);
    }



    /**
     * 点击外部是否消失GCDialog
     *
     * @param cancel true可以消失,false不能消失,默认为false
     * @return
     */
    public BxDialog setGCCanceledOnTouchOutside(boolean cancel) {
        try {
            setCanceledOnTouchOutside(cancel);
            View inflate = getLayoutInflater().inflate(layoutResID, null);
            if (cancel) {
                setOnClickListener(inflate.getId(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if(mCanceledListener != null) {
                            mCanceledListener.CanceledListener();
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "跟布局缺少ID,请在布局文件中给根布局加上ID");
        }
        return this;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /* 触摸外部弹窗 */
        if (isOutOfBounds(getContext(), event)) {
            if(mCanceledListener != null) {
                mCanceledListener.CanceledListener();
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 判断是否触摸到空白区域
     * @param context
     * @param event
     * @return
     */
    private boolean isOutOfBounds(Context context, MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();
        final int slop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        final View decorView = getWindow().getDecorView();
        return (x < -slop) || (y < -slop) || (x > (decorView.getWidth() + slop))
                || (y > (decorView.getHeight() + slop));
    }

    /**
     * 设置dialog点击外部区域的监听
     *
     * @param canceledListener 外部区域监听回调
     * @return
     */
    public BxDialog setCanceledListener(CanceledOnTouchOutsideInterf canceledListener) {
        mCanceledListener = canceledListener;
        return this;
    }

    /**
     * 定时消失
     *
     * @param timeMillis
     */
    public BxDialog setDelayedDismiss(final Activity activity, int timeMillis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                });
            }
        }, timeMillis);
        return this;
    }

    /**
     * 彻底清除GCDialog
     *
     * @return
     */
    public BxDialog dismissCancel() {
        dismiss();
        cancel();
        return this;
    }

    /**
     * 设置dialog状态监听
     *
     * @param statusListener 状态监听回调
     * @return
     */
    public BxDialog setStatusListener(BxDialogInterf statusListener) {
        mStatusListener = statusListener;
        return this;
    }

    @Override
    public void show() {
        super.show();
        if (mStatusListener != null) {
            mStatusListener.onShow();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (mStatusListener != null) {
            mStatusListener.onDismiss();
        }
    }

    /**
     * 手机返回键按钮监听
     *
     * @param OnclickListener
     * @return
     */
    public BxDialog setKeyCodeBack(OnclickListener OnclickListener) {
        if (OnclickListener != null) {
            this.OnclickListener = OnclickListener;
        }
        mIsFinish = true;
        return this;
    }

    private OnclickListener OnclickListener;

    public interface OnclickListener {
        void onBackClick(Context context);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mIsFinish) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (OnclickListener != null) {
                    OnclickListener.onBackClick(mContext);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //通过getView获得对应的控件
    public <TView extends View> TView getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = findViewById(id);
            mViews.put(id, view);
        }
        return (TView) view;
    }


    public BxDialog setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    //可写在不同的文件中,这边是为了方便复用
    interface CanceledOnTouchOutsideInterf {
        void CanceledListener();
    }

    interface BxDialogInterf {
        /**
         * dialog显示
         */
        void onShow();
        /**
         * dialog消失
         */
        void onDismiss();
    }

}