package com.qyh.coderepository.menu.view.smartrefresh.footer.ballpulse;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;


import com.blankj.utilcode.util.SizeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 邱永恒
 * @time 2018/1/30  16:32
 * @desc 脉冲球动画()
 */
public class BallPulseView extends View {

    public static final int DEFAULT_SIZE = 50; //dp

    private Paint paint;

    private int normalColor = 0xffeeeeee; // 停止动画时, 圆点指示器的颜色, 默认白色
    private int animatingColor = 0xffe75946; // 开始动画时, 圆点指示器的颜色, 默认红色

    private float circleSpacing; // 圆圈间隔
    private float[] scaleFloats = new float[]{1f, 1f, 1f};

    private boolean mIsStarted = false;
    private ArrayList<ValueAnimator> mAnimators;
    private Map<ValueAnimator, ValueAnimator.AnimatorUpdateListener> mUpdateListeners = new HashMap<>();

    public BallPulseView(Context context) {
        this(context, null);
    }

    public BallPulseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallPulseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        circleSpacing = SizeUtils.dp2px(4);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
    }

    /**
     * resolveSize()获取系统测量过的安全尺寸
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int default_size = SizeUtils.dp2px(DEFAULT_SIZE);
        setMeasuredDimension(resolveSize(default_size, widthMeasureSpec), resolveSize(default_size, heightMeasureSpec));
    }

    /**
     * 添加到界面的回调
     * 取消所有动画
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimators != null) {
            for (int i = 0; i < mAnimators.size(); i++) {
                mAnimators.get(i).cancel();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        // 圆半径
        float radius = (Math.min(getWidth(), getHeight()) - circleSpacing * 2) / 6;
        // 计算绘制坐标原点
        float x = getWidth() / 2 - (radius * 2 + circleSpacing);
        float y = getHeight() / 2;

        for (int i = 0; i < 3; i++) {
            canvas.save();
            float translateX = x + (radius * 2) * i + circleSpacing * i;
            canvas.translate(translateX, y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            canvas.drawCircle(0, 0, radius, paint);
            canvas.restore();
        }
    }

    private boolean isStarted() {
        return mIsStarted;
    }

    /**
     * 初始化动画
     * 3个动画, 每个动画延时120ms, 主要提供圆形的缩放功能
     */
    private void createAnimators() {
        mAnimators = new ArrayList<>();
        int[] delays = new int[]{120, 240, 360};
        for (int i = 0; i < 3; i++) {
            final int index = i;

            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);

            scaleAnim.setDuration(750);
            scaleAnim.setRepeatCount(ValueAnimator.INFINITE);
            scaleAnim.setStartDelay(delays[i]);

            mUpdateListeners.put(scaleAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mAnimators.add(scaleAnim);
        }
    }

    public void setIndicatorColor(@ColorInt int color) {
        paint.setColor(color);
    }

    public void setNormalColor(@ColorInt int color) {
        normalColor = color;
    }

    public void setAnimatingColor(@ColorInt int color) {
        animatingColor = color;
    }

    public void startAnim() {
        if (mAnimators == null)
            createAnimators();
        if (mAnimators == null)
            return;
        if (isStarted())
            return;

        for (int i = 0; i < mAnimators.size(); i++) {
            ValueAnimator animator = mAnimators.get(i);

            //when the animator restart , add the updateListener again because they was removed by animator stop .
            ValueAnimator.AnimatorUpdateListener updateListener = mUpdateListeners.get(animator);
            if (updateListener != null) {
                animator.addUpdateListener(updateListener);
            }
            animator.start();
        }
        mIsStarted = true;
        setIndicatorColor(animatingColor);
    }

    public void stopAnim() {
        if (mAnimators != null && mIsStarted) {
            mIsStarted = false;
            for (ValueAnimator animator : mAnimators) {
                if (animator != null /*&& animator.isStarted()*/) {
                    animator.removeAllUpdateListeners();
                    animator.end();
                }
            }
            scaleFloats = new float[]{1f, 1f, 1f};
        }
        setIndicatorColor(normalColor);
    }

}
