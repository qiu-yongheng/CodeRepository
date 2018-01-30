package com.qyh.coderepository.menu.view.lineargradient;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author 邱永恒
 * @time 2018/1/7  22:42
 * @desc ${TODD}
 */


public class LinearGradientView extends View{
    private Context context;
    private Paint paint;
    private String text;
    private int textWidth;
    private int textHeight;
    private int dx;

    public LinearGradientView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setTextSize(70);
        paint.setStrokeWidth(20);
        text = "这是屌炸天的闪动文字特效";

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        textWidth = rect.width();
        textHeight = rect.height();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // 创建LinearGradient
        LinearGradient linearGradient = new LinearGradient(-textWidth + 100 + dx, 100, 100 + dx, 100, new int[]{Color.BLACK, Color.GREEN, Color.BLACK}, new float[]{0f, 0.5f, 1f}, Shader.TileMode.CLAMP);
        paint.setShader(linearGradient);

        // 绘制文字
        canvas.drawText(text, 100, 100, paint);
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(textWidth * 2);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(3000);
        animator.start();
    }
}
