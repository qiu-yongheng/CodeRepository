package com.qyh.coderepository.menu.view.quad;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.kc.common.util.log.LoggerUtil;

/**
 * @author 邱永恒
 * @time 2018/1/3  13:29
 * @desc 贝塞尔曲线动画
 */

public class RQuadToView extends View {

    private Paint paint;
    private Path path;
    private int mItemWaveLength = 1500;
    private int dx;
    private ValueAnimator animator;
    private int dy;
    private int originY = 300;
    private long time = 10000;
    private int waveHeight = 100;

    public RQuadToView(Context context) {
        super(context);
    }

    public RQuadToView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RQuadToView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.argb(30, 0, 200, 0));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.reset();
        int halfWaveLen = mItemWaveLength / 2;
        path.moveTo(-mItemWaveLength + dx, originY + dy);
        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            path.rQuadTo(halfWaveLen / 2, -waveHeight, halfWaveLen, 0);
            path.rQuadTo(halfWaveLen / 2, waveHeight, halfWaveLen, 0);
        }
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();

        canvas.drawPath(path, paint);

        if (animator != null && dy + 100 >= getHeight()) {
            LoggerUtil.d(animator.isRunning() + ", " + animator.isStarted() + ", " + animator.isPaused());
            animator.cancel();
            LoggerUtil.d("cancel: " + animator.isRunning() + ", " + animator.isStarted() + ", " + animator.isPaused());
        }
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

    public void setHeight(int progress) {
        originY = 300 + getHeight() * progress / 100;
        invalidate();
    }

    public void setSpeed(int progress) {
        time = 10000 - 9000 * progress / 100;
        invalidate();
        if (animator != null) {
            animator.setDuration(time);
        }
    }

    public void setWave(int progress) {
        mItemWaveLength = 1500 - 1200 * progress / 100;
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
