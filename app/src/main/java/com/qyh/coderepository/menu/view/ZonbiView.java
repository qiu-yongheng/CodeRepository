package com.qyh.coderepository.menu.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.qyh.coderepository.R;

/**
 * @author 邱永恒
 * @time 2018/1/10  14:05
 * @desc ${TODD}
 */

public class ZonbiView extends View {

    private Paint paint;
    private Bitmap bitmap;
    private int width;
    private int height;
    private int imgWidth;
    private int imgheight;
    private int x;
    private int y;
    private int value;

    public ZonbiView(Context context) {
        super(context);
        init();
    }

    public ZonbiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZonbiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.zonbi);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        imgWidth = width / 11 + 1;
        imgheight = height / 2;
        width = imgWidth * 11;
        height = imgheight * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);

        Rect src = new Rect(x, y, x + imgWidth, y + imgheight);

        Rect dst = new Rect(-imgWidth * 2, -imgheight * 2, imgWidth * 2, imgheight * 2);

        Log.d("==", "x: " + x + ", y: " + y);

        canvas.drawBitmap(bitmap, src, dst, paint);

        if (x + imgWidth >= width) {
            // 换行
            x = 0;
            if (y + imgheight >= height) {
                y = 0;
            } else {
                y += imgheight;
            }
        } else {
            x += imgWidth;
        }

        canvas.restore();
    }

    public void startAnim() {
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 23);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int v = (int) animation.getAnimatedValue();
                if (value != v) {
                    invalidate();
                    value = v;
                }
            }
        });

        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }
}
