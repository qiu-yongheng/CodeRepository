package com.qyh.coderepository.menu.view.quadll;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

/**
 * @author 邱永恒
 * @time 2018/1/3  15:32
 * @desc LinearLayout实现贝塞尔曲线动画背景
 */

public class QuadLinearLayout extends LinearLayout{
    private Paint paint;
    private Path path;
    private int mItemWaveLength = 800;
    private int dx;
    private ValueAnimator animator;
    private int dy;
    private int originY = 70;
    private long time = 5000;
    private int waveHeight = 60;
    private int measuredWidth;
    private int measuredHeight;

    public QuadLinearLayout(Context context) {
        super(context);
    }

    public QuadLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QuadLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(40, 0, 200, 0));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measuredWidth = getMeasuredWidth();
        measuredHeight = getMeasuredHeight();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        paint.setColor(Color.argb(40, 0, 200, 0));
        path.reset();

        int halfWaveLen = mItemWaveLength / 2;
        path.moveTo(-mItemWaveLength + dx, originY + dy);
        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            path.rQuadTo(halfWaveLen / 2, -waveHeight, halfWaveLen, 0);
            path.rQuadTo(halfWaveLen / 2, waveHeight, halfWaveLen, 0);
        }
        path.lineTo(measuredWidth, measuredHeight);
        path.lineTo(0, measuredHeight);
        path.close();

        canvas.drawPath(path, paint);



        paint.setColor(Color.argb(60, 0, 200, 0));
        path.reset();
        int halfWaveLen1 = (mItemWaveLength + 200) / 2;
        path.moveTo(-mItemWaveLength - 200 + dx, originY + dy);
        for (int i = -mItemWaveLength - 200; i <= getWidth() + mItemWaveLength + 200; i += (mItemWaveLength + 200)) {
            path.rQuadTo(halfWaveLen1 / 2, -waveHeight, halfWaveLen, 0);
            path.rQuadTo(halfWaveLen1 / 2, waveHeight, halfWaveLen, 0);
        }
        path.lineTo(measuredWidth, measuredHeight);
        path.lineTo(0, measuredHeight);
        path.close();

        canvas.drawPath(path, paint);
    }

    public void startAnim() {
        if (animator == null) {
            animator = ValueAnimator.ofInt(0, mItemWaveLength);
            animator.setDuration(time);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //dy++;
                    dx = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });

            animator.start();
        }
    }

    public void setHeight(int height) {
        originY = 300 + getHeight() * height / 100;
        invalidate();
    }

    public void setSpeed(int speed) {
        time = 10000 - 9000 * speed / 100;
        invalidate();
        if (animator != null) {
            animator.setDuration(time);
        }
    }

    public void setWave(int wave) {
        mItemWaveLength = 1500 - 1200 * wave / 100;
        invalidate();
        if (animator != null) {
            animator.setIntValues(mItemWaveLength);
        }
    }

    public void setWaveHeight(int progress) {
        waveHeight = 100 + 400 * progress / 100;
        invalidate();
    }
}
