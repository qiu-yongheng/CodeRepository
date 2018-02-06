package com.qyh.coderepository.menu.view.smartrefresh.header.bezierradar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.blankj.utilcode.util.SizeUtils;


/**
 * @author 邱永恒
 * @time 2018/2/5  11:47
 * @desc 中心圆形加载进度视图
 */
public class RoundProgressView extends View {

    private Paint mPath;
    private Paint mPantR;
    private ValueAnimator mAnimator;
    private int endAngle = 0;
    private int stratAngle = 270;
    private int mRadius = 0;
    private int mOutsideCircle = 0;
    private RectF mRect = new RectF(0, 0, 0, 0);

    public RoundProgressView(Context context) {
        super(context);
        initView();
    }

    public RoundProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RoundProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 初始化画笔
        mPath = new Paint();
        mPantR = new Paint();
        mPath.setAntiAlias(true);
        mPantR.setAntiAlias(true);
        mPath.setColor(Color.WHITE);
        mPantR.setColor(0x55000000);

        // 初始化尺寸
        mRadius = SizeUtils.dp2px(20);
        mOutsideCircle = SizeUtils.dp2px(7);
        mPath.setStrokeWidth(SizeUtils.dp2px(3));
        mPantR.setStrokeWidth(SizeUtils.dp2px(3));

        // 属性动画
        mAnimator = ValueAnimator.ofInt(0, 360);
        mAnimator.setDuration(4000);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); // 先加速后减速
    }

    /**
     * view绑定到界面的回调
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                endAngle = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }

    /**
     * view取消绑定的回调
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimator.removeAllUpdateListeners();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec) + SizeUtils.dp2px(3) * 4, MeasureSpec.getMode(heightMeasureSpec));

        setMeasuredDimension(resolveSize(getSuggestedMinimumWidth() , widthMeasureSpec),
                resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    public void setBackColor(@ColorInt int backColor) {
        mPantR.setColor(backColor & 0x00ffffff | 0x55000000);
    }

    public void setFrontColor(@ColorInt int color) {
        mPath.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 1. 获取控件大小
        int width = getWidth();
        int height = getHeight();

        // 是否处于开发状态(用于xml预览)
        if (isInEditMode()) {
            stratAngle = 0;
            endAngle = 270;
        }

        mPath.setStyle(Paint.Style.FILL); // 填充
        canvas.drawCircle(width / 2, height / 2, mRadius, mPath); // 画内圆

        mPath.setStyle(Paint.Style.STROKE); // 设置为空心
        canvas.drawCircle(width / 2, height / 2, mRadius + mOutsideCircle, mPath); // 画外圆

        mPantR.setStyle(Paint.Style.FILL); // 填充
        mRect.set(width / 2 - mRadius, height / 2 - mRadius, width / 2 + mRadius, height / 2 + mRadius); // 设置绘制区域
        canvas.drawArc(mRect, stratAngle, endAngle, true, mPantR); // 绘制内圆圆弧, 颜色半透明, 使用中心点

        mRadius += mOutsideCircle;
        mPantR.setStyle(Paint.Style.STROKE);
        mRect.set(width / 2 - mRadius, height / 2 - mRadius, width / 2 + mRadius, height / 2 + mRadius);
        canvas.drawArc(mRect, stratAngle, endAngle, false, mPantR); // 绘制外圆轮廓, 颜色半透明, 不使用中心点
        mRadius -= mOutsideCircle;
    }

    public void startAnim() {
        if (mAnimator != null) {
            mAnimator.start();
        }
    }

    public void stopAnim() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }
}
