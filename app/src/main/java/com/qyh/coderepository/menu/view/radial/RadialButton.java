package com.qyh.coderepository.menu.view.radial;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;

/**
 * @author 邱永恒
 * @time 2018/1/8  13:35
 * @desc ${TODD}
 */

public class RadialButton extends AppCompatButton {
    private Context context;
    private int mX, mY;
    private ObjectAnimator mAnimator;
    private int DEFAULT_RADIUS = 50;
    private int mCurRadius = 0;
    private RadialGradient mRadialGradient;
    private Paint mPaint;

    public RadialButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RadialButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public RadialButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 根据手指位置移动圆圈
        if (mX != event.getX() || mY != event.getY()) {
            mX = (int) event.getX();
            mY = (int) event.getY();
            setRadius(DEFAULT_RADIUS);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                // 取消之前的动画
                if (mAnimator != null && mAnimator.isRunning()) {
                    mAnimator.cancel();
                }

                if (mAnimator == null) {
                    mAnimator = ObjectAnimator.ofInt(this,"radius",DEFAULT_RADIUS, getWidth());
                }

                mAnimator.setInterpolator(new AccelerateInterpolator());
                mAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setRadius(0);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                mAnimator.setDuration(1000);
                mAnimator.start();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setRadius(final int radius) {
        mCurRadius = radius;
        if (mCurRadius > 0) {
            mRadialGradient = new RadialGradient(mX, mY, mCurRadius, 0x00FFFFFF, 0x8858FAAC, Shader.TileMode.CLAMP);
            mPaint.setShader(mRadialGradient);
        }
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mX, mY, mCurRadius, mPaint);
    }
}
