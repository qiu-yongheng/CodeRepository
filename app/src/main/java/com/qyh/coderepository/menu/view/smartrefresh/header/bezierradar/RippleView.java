package com.qyh.coderepository.menu.view.smartrefresh.header.bezierradar;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.kc.common.util.log.LoggerUtil;

/**
 * @author 邱永恒
 * @time 2018/2/1  16:37
 * @desc 涟漪view
 */
public class RippleView extends View {
    private int mRadius;
    private Paint mPaint;
    private ValueAnimator mAnimator;

    public RippleView(Context context) {
        super(context);
        initView();
    }

    public RippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xffffffff);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    public void setFrontColor(@ColorInt int color) {
        mPaint.setColor(color);
    }

    /**
     * 启动属性动画
     */
    public void startReveal() {
        LoggerUtil.d(String.format("onMeasure width = %d,height = %d",getWidth(),getHeight()));
        if (mAnimator == null) {
            int height = getHeight();
            int width = getWidth();
            double powHeight = Math.pow(height, 2);
            double powWidth = Math.pow(getWidth(), 2);
            double sqrt = Math.sqrt(powHeight + powWidth);
            int bigRadius = (int) sqrt;
            mAnimator = ValueAnimator.ofInt(0, bigRadius, 0);
            mAnimator.setDuration(2000);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRadius = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                }
            });
        }
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mPaint);
    }
}
