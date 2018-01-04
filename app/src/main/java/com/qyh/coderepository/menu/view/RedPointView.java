package com.qyh.coderepository.menu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.kc.common.util.log.LoggerUtil;

/**
 * @author 邱永恒
 * @time 2018/1/4  22:27
 * @desc ${TODD}
 */


public class RedPointView extends FrameLayout {

    private PointF startPointF;
    private PointF currentPointF;
    private Paint paint;
    private boolean isTouch;
    private float DEFAULT_RADIUS = 20;
    private float radio;
    private Path path;
    private boolean isAnimStart;

    public RedPointView(@NonNull Context context) {
        super(context);
        initView();
    }

    public RedPointView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RedPointView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 开始点
        startPointF = new PointF(100, 100);
        // 移动后的点的坐标
        currentPointF = new PointF();

        // 贝塞尔曲线路径
        path = new Path();

        // 画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // 绘制开始的原点
        canvas.drawCircle(startPointF.x, startPointF.y, DEFAULT_RADIUS, paint);

        // 如果触摸了控件, 绘制小圆点
        if (isTouch) {
            // 绘制贝塞尔曲线
            calculatePath();
            canvas.drawPath(path, paint);
            canvas.drawCircle(currentPointF.x, currentPointF.y, DEFAULT_RADIUS, paint);
        }

        super.dispatchDraw(canvas);
    }

    private void calculatePath() {
        // 当前点
        float x = currentPointF.x;
        float y = currentPointF.y;
        // 起点
        float startX = startPointF.x;
        float startY = startPointF.y;
        float dx = x - startX;
        float dy = y - startY;
        // 计算角度
        double a = Math.atan(dy / dx);
        float offsetX = (float) (DEFAULT_RADIUS * Math.sin(a));
        float offsetY = (float) (DEFAULT_RADIUS * Math.cos(a));

        // 动态改变半径
        /*float distance = (float) Math.sqrt(Math.pow(y - startY, 2) + Math.pow(x - startX, 2));
        radio = -distance / 15 + DEFAULT_RADIUS;
        if (radio < 9) {
            isAnimStart = true;
        }*/

        // 根据角度算出四边形的四个点
        float x1 = startX - offsetX;
        float y1 = startY + offsetY;

        float x2 = x - offsetX;
        float y2 = y + offsetY;

        float x3 = x + offsetX;
        float y3 = y - offsetY;

        float x4 = startX + offsetX;
        float y4 = startY - offsetY;

        float anchorX = (startX + x) / 2;
        float anchorY = (startY + y) / 2;

        path.reset();
        path.moveTo(x1, y1);
        path.quadTo(anchorX, anchorY, x2, y2);
        path.lineTo(x3, y3);
        path.quadTo(anchorX, anchorY, x4, y4);
        path.lineTo(x1, y1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                break;
        }

        LoggerUtil.d("event.getX() = " + event.getX() + ", event.getY() = " + event.getY() + ", event.getRawX() = " + event.getRawX() + ", event.getRawY() = " + event.getRawY());

        currentPointF.set(event.getX(), event.getY());
        postInvalidate();
        return true;
    }
}
