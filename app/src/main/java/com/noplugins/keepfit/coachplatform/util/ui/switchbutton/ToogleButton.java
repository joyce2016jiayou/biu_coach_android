package com.noplugins.keepfit.coachplatform.util.ui.switchbutton;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import com.noplugins.keepfit.coachplatform.R;

/**
 * @author 1one
 * @date 2019/7/13.
 */

public class ToogleButton extends View {

    private static final int DEFAULT_TOOGLE_WIDTH = 58;//默认的宽度
    private static final int DEFAULT_TOOGLE_HEIGHT = 36;//默认的高度

    // 边线
    private int borderWidth;//边线宽度
    private int borderColor;//边线颜色
    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // 边线里面的背景
    private float height;//背景高度
    private float width;//背景宽度
    private float viewRadius;//背景半径
    private int currBgColor;//背景颜色
    private int animBgColor;//背景颜色
    private int checkedBgColor;//开关为开的时候的背景色
    private int uncheckedBgColor;//开关为关的时候的背景色
    private Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // 按钮
    private int buttonColor;//按钮的颜色
    private Paint buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int buttonRingColor;//开关圆形按钮的圆环颜色
    private int buttonRingWidth;//开关圆形按钮的圆环宽度
    private Paint buttonRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float buttonRadius;//按钮半径
    private float buttonRadiusRate;//按钮直径和控件高度的比率
    private float mButtonX;//按钮的偏移量
    private int buttonOffset = 3;// 按钮与边框之间的距离

    private int checkState = 1;//按钮的开关状态【默认为关闭状态】
    private static final int CHECKED = 0;//打开状态
    private static final int UNCHECKED = 1;//关闭状态

    private int animationDuration;//动画时间
    private boolean isAnimation = false;//是否在滑动中

    /**
     * 背景位置
     */
    private float left;
    private float top;
    private float right;
    private float bottom;
    private float centerX;
    private float centerY;

    private ValueAnimator valueAnimator;

    private final android.animation.ArgbEvaluator argbEvaluator
            = new android.animation.ArgbEvaluator();

    public ToogleButton(Context context) {
        this(context, null);
    }

    public ToogleButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToogleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ToogleButton, defStyleAttr, 0);
        // 边线宽度
        borderWidth = (int) array.getDimension(R.styleable.ToogleButton_border_width, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        // 边线颜色
        borderColor = array.getColor(R.styleable.ToogleButton_border_color, Color.parseColor("#cfcfcf"));

        //背景颜色
        checkedBgColor = array.getColor(R.styleable.ToogleButton_checked_bg_color, Color.parseColor("#00BABB"));
        uncheckedBgColor = array.getColor(R.styleable.ToogleButton_unchecked_bg_color, Color.parseColor("#cfcfcf"));

        // 按钮颜色
        buttonColor = array.getColor(R.styleable.ToogleButton_button_color, Color.parseColor("#ffffff"));
        //开关圆形按钮的圆环颜色
        buttonRingColor = array.getColor(R.styleable.ToogleButton_button_ring_color, Color.parseColor("#00BABB"));
        //开关圆形按钮的圆环宽度
        buttonRingWidth = (int) array.getDimension(R.styleable.ToogleButton_button_ring_width, borderWidth);
        buttonOffset = (int) array.getDimension(R.styleable.ToogleButton_button_offset, borderWidth);
        //按钮直径和控件高度的比率
        buttonRadiusRate = array.getFloat(R.styleable.ToogleButton_button_radius_rate, 0.8f);

        //动画时间
        animationDuration = array.getInt(R.styleable.ToogleButton_animation_duration, 500);
        array.recycle();
        init();
    }

    /**
     * 初始化一些变量设置
     */
    private void init() {
        // 边框
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);


        // 当前颜色为默认 非选中状态 颜色
        currBgColor = uncheckedBgColor;
        bgPaint.setColor(currBgColor);

        // 按钮
        buttonPaint.setColor(buttonColor);
        buttonPaint.setStyle(Paint.Style.FILL);

        // 按钮圆环
        buttonRingPaint.setColor(buttonRingColor);
        buttonRingPaint.setStrokeWidth(buttonRingWidth);
        buttonRingPaint.setStyle(Paint.Style.STROKE);


        valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(animationDuration);
        valueAnimator.setRepeatCount(0);
        valueAnimator.addUpdateListener(animatorUpdateListener);
        valueAnimator.addListener(animatorListener);
    }

    private ValueAnimator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (checkState == UNCHECKED) {
                checkState = CHECKED;
                isAnimation = false;
                if (null != onCheckListener) {
                    onCheckListener.onCheck(true);
                }
            } else if (checkState == CHECKED) {
                checkState = UNCHECKED;
                isAnimation = false;
                if (null != onCheckListener) {
                    onCheckListener.onCheck(false);
                }
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float totalOffset = width - 2 * viewRadius;
            if (checkState == UNCHECKED) {//关闭状态时
                mButtonX = totalOffset * (Float) animation.getAnimatedValue();
                currBgColor = (int) argbEvaluator.evaluate(
                        (Float) animation.getAnimatedValue(),
                        uncheckedBgColor,
                        checkedBgColor
                );
            } else if (checkState == CHECKED) {//打开状态时
                mButtonX = totalOffset - totalOffset * (Float) animation.getAnimatedValue();
                currBgColor = (int) argbEvaluator.evaluate(
                        (Float) animation.getAnimatedValue(),
                        checkedBgColor, uncheckedBgColor
                );
            }
            postInvalidate();
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);

        if (widthSpec == MeasureSpec.AT_MOST) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_TOOGLE_WIDTH, MeasureSpec.EXACTLY);
        }

        if (heightSpec == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_TOOGLE_HEIGHT, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 边框内部宽高
        height = h - borderWidth - borderWidth;
        width = w - borderWidth - borderWidth;

        // 边框内部的范围
        left = borderWidth;
        top = borderWidth;
        right = w - borderWidth;
        bottom = h - borderWidth;

        centerX = (left + right) * 0.5f;
        centerY = (top + bottom) * 0.5f;

        viewRadius = height * 0.5f;// 边框内部高度/2
        // 按钮半径
        buttonRadius = viewRadius - buttonRingWidth / 2 - buttonOffset;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制边框
        drawBorder(canvas);
        // 绘制边框里面的背景
        drawBg(canvas, checkState);

        //绘制按钮滑动过程中的渐变边框
        drawAnim(canvas);

        // 绘制按钮
        drawButton(canvas);

    }

    private void drawBorder(Canvas canvas) {
        canvas.drawRoundRect(new RectF(left, top, right, bottom), viewRadius, viewRadius, borderPaint);
    }

    private void drawBg(Canvas canvas, int checkState) {
        if (checkState == UNCHECKED) {
            // 关闭状态
            currBgColor = uncheckedBgColor;
        } else {
            // 开启状态
            currBgColor = checkedBgColor;
        }
        bgPaint.setColor(currBgColor);
        canvas.drawRoundRect(new RectF(left, top, right, bottom), viewRadius, viewRadius, bgPaint);

    }

    private void drawButton(Canvas canvas) {
        canvas.drawCircle(buttonRadius + buttonOffset + borderWidth + buttonRingWidth / 2 + mButtonX, centerY, buttonRadius, buttonPaint);
        canvas.drawCircle(buttonRadius + buttonOffset + borderWidth + buttonRingWidth / 2 + mButtonX, centerY, buttonRadius, buttonRingPaint);
    }

    private void drawAnim(Canvas canvas) {
        float rate = mButtonX / (width - 2 * viewRadius);
        float animLeft, animTop, animRight, animBottom;
        float startX, startY;
        if (checkState == UNCHECKED) {
            // 关闭状态
            startX = borderWidth + viewRadius;
            startY = height / 2 + borderWidth;
            animLeft = startX - rate * viewRadius;
            animRight = startX + rate * (width - viewRadius);
            animTop = startY - rate * viewRadius;
            animBottom = startY + rate * viewRadius;
            currBgColor = checkedBgColor;
            buttonRingPaint.setColor(Color.parseColor("#cfcfcf"));

        } else {
            rate = 1 - rate;
            // 开启状态
            startX = borderWidth + width - viewRadius;
            startY = height / 2 + borderWidth;
            animLeft = startX - rate * (width - viewRadius);
            animRight = startX + rate * viewRadius;
            animTop = startY - rate * viewRadius;
            animBottom = startY + rate * viewRadius;
            currBgColor = uncheckedBgColor;
            buttonRingPaint.setColor(Color.parseColor("#00BABB"));

        }
        bgPaint.setStrokeWidth(0);
        bgPaint.setColor(currBgColor);
        canvas.drawRoundRect(new RectF(animLeft, animTop, animRight, animBottom), viewRadius, viewRadius, bgPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        int eventAsked = event.getActionMasked();
        switch (eventAsked) {
            case MotionEvent.ACTION_DOWN:
                if (isAnimation) {
                    return false;
                }
                if (checkState == UNCHECKED) {
                    toogleOn();
                } else if (checkState == CHECKED) {
                    toogleOff();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    /**
     * 打开
     */
    public void toogleOn() {
        isAnimation = true;
        valueAnimator.start();
    }

    /**
     * 关闭
     */
    public void toogleOff() {
        isAnimation = true;
        valueAnimator.start();
    }

    /**
     * 定义一个选中接口回调
     */
    private OnCheckListener onCheckListener;

    public interface OnCheckListener {
        void onCheck(boolean isCheck);
    }

    public void setOnCheckListener(OnCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }
}
