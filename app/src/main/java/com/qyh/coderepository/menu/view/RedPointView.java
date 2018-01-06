package com.qyh.coderepository.menu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kc.common.util.log.LoggerUtil;
import com.qyh.coderepository.R;

/**
 * @author 邱永恒
 * @time 2018/1/4  22:27
 * @desc ${TODD}
 */


public class RedPointView extends FrameLayout {

    private Context context;
    private PointF startPointF;
    private PointF currentPointF;
    private Paint paint;
    private boolean isTouch;
    private float DEFAULT_RADIUS = 20;
    private float radio = DEFAULT_RADIUS;
    private Path path;
    private boolean isAnimStart;
    private TextView textView;
    private ImageView imageView;

    public RedPointView(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public RedPointView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public RedPointView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
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

        // 创建TextView
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setBackgroundResource(R.drawable.red_point_tv_bg);
        textView.setText("99+");
        textView.setPadding(10, 10, 10, 10);
        this.addView(textView);

        // 创建ImageView
        imageView = new ImageView(context);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(R.drawable.explode);
        imageView.setVisibility(INVISIBLE);
        this.addView(imageView);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // 如果触摸了控件, 绘制小圆点
        if (isTouch && !isAnimStart) {
            // 绘制贝塞尔曲线
            calculatePath();
            canvas.drawPath(path, paint);
            canvas.drawCircle(startPointF.x, startPointF.y, radio, paint);
            canvas.drawCircle(currentPointF.x, currentPointF.y, radio, paint);

            textView.setX(currentPointF.x - textView.getWidth()/2);
            textView.setY(currentPointF.y - textView.getHeight()/2);
        } else {
            textView.setX(startPointF.x - textView.getWidth() / 2);
            textView.setY(startPointF.y - textView.getHeight() / 2);
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
        float offsetX = (float) (radio * Math.sin(a));
        float offsetY = (float) (radio * Math.cos(a));

        // 动态改变半径
        float distance = (float) Math.sqrt(Math.pow(y - startY, 2) + Math.pow(x - startX, 2));
        radio = -distance / 15 + DEFAULT_RADIUS;
        if (radio < 9) {
            radio = 9;
            isAnimStart = true;

            imageView.setX(currentPointF.x - textView.getWidth()/2);
            imageView.setY(currentPointF.y - textView.getHeight()/2);
            imageView.setVisibility(VISIBLE);
            ((AnimationDrawable)imageView.getDrawable()).start();

            textView.setVisibility(GONE);
        }

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
                Rect rect = new Rect();
                int[] location = new int[2];
                textView.getLocationOnScreen(location);
                rect.left = location[0];
                rect.top = location[1];
                rect.right = textView.getWidth() + location[0];
                rect.bottom = textView.getHeight() + location[1];
                if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    isTouch = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                radio = DEFAULT_RADIUS;
                break;
        }

        LoggerUtil.d("event.getX() = " + event.getX() + ", event.getY() = " + event.getY() + ", event.getRawX() = " + event.getRawX() + ", event.getRawY() = " + event.getRawY());

        currentPointF.set(event.getX(), event.getY());
        postInvalidate();
        return true;
    }
}
