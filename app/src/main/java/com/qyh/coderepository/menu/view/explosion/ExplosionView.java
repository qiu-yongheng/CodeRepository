package com.qyh.coderepository.menu.view.explosion;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kc.common.util.log.LoggerUtil;
import com.qyh.coderepository.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱永恒
 * @time 2018/1/2  16:01
 * @desc ${TODD}
 */

public class ExplosionView extends FrameLayout {
    private final Context context;
    private List<Integer> list = new ArrayList<>();
    private List<ImageView> itemList = new ArrayList<>();
    private Paint paint;
    private int menuRadio = 80;
    private int itemRadio = 78;
    private int distanceRadio = 400;
    private int menuResId;
    private PointF currentPoint;
    private ImageView ivMenu;
    private boolean isTouch;
    private boolean isShow;
    private int currentX;
    private int currentY;

    public ExplosionView(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public ExplosionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public ExplosionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 做初始化操作
     */
    private void initView() {
        // 初始化画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 初始化开始点
        currentPoint = new PointF(100, 100);

        // 初始化item控件
        list.add(R.mipmap.item_2);
        list.add(R.mipmap.item_3);
        list.add(R.mipmap.item_4);
        list.add(R.mipmap.item_5);
        menuResId = R.mipmap.item_1;

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (Integer integer : list) {
            itemList.add(createView(layoutParams, integer, itemRadio, false));
        }

        // 初始化menu控件
        ivMenu = createView(layoutParams, menuResId, menuRadio, true);
        ivMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShow) {
                    isShow = true;
                    show();
                } else {
                    isShow = false;
                    dismiss();
                }
            }
        });
    }

    /**
     * 创建图片控件
     *
     * @param layoutParams
     * @param integer
     * @param radio
     * @param isShow
     * @return
     */
    private ImageView createView(LayoutParams layoutParams, Integer integer, int radio, boolean isShow) {
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), integer);
        Matrix matrix = new Matrix();
        float scale = (float) radio * 2 / srcBitmap.getWidth();
        matrix.setScale(scale, scale);
        BitmapShader bitmapShader = new BitmapShader(srcBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader.setLocalMatrix(matrix);

        Bitmap imgBitmap = Bitmap.createBitmap(radio * 2, radio * 2, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(imgBitmap);

        paint.setShader(bitmapShader);
        c.drawCircle(radio, radio, radio, paint);

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageBitmap(imgBitmap);
        imageView.setVisibility(isShow ? VISIBLE : GONE);
        imageView.setId(integer);
        LoggerUtil.d("imageView id = " + imageView.getId());
        this.addView(imageView);
        return imageView;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        ivMenu.setX(currentPoint.x - ivMenu.getWidth() / 2);
        ivMenu.setY(currentPoint.y - ivMenu.getHeight() / 2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Rect rect = new Rect();
                int[] location = new int[2];
                ivMenu.getLocationOnScreen(location);
                rect.left = location[0];
                rect.top = location[1];
                rect.right = ivMenu.getWidth() + location[0];
                rect.bottom = ivMenu.getHeight() + location[1];
                if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    isTouch = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isTouch) {
                    currentPoint.set(event.getX(), event.getY());
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 解决onTouchEvent()与onClick()冲突
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Boolean intercept = false;
        //获取坐标点：
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deletx = x - currentX;
                int delety = y - currentY;
                if (Math.abs(deletx) != 0 || Math.abs(delety) != 0) {
                    intercept = true;
                    isTouch = true;
                    Log.d("拦截了", "111");
                } else {
                    intercept = false;
                    isTouch = false;
                    Log.d("没拦截", deletx + ", " + delety);
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            default:
                break;
        }
        currentX = x;
        currentY = y;
        return intercept;
    }

    public void show() {
        for (int i = 0; i < itemList.size(); i++) {
            // 每个控件相隔角度
            double angel = 90 / (itemList.size() - 1);
            // 计算控件坐标
            double radian = Math.toRadians(angel * i);
            int y = (int) (Math.sin(radian) * distanceRadio);
            int x = (int) (Math.cos(radian) * distanceRadio);
            //
            ImageView imageView = itemList.get(i);
            imageView.setVisibility(VISIBLE);
            AnimatorSet set = new AnimatorSet();
            //包含平移、缩放和透明度动画
            set.playTogether(
                    ObjectAnimator.ofFloat(imageView, "translationX", currentPoint.x - itemRadio, currentPoint.x - x - itemRadio),
                    ObjectAnimator.ofFloat(imageView, "translationY", currentPoint.y - itemRadio, currentPoint.y - y - itemRadio),
                    ObjectAnimator.ofFloat(imageView, "rotation", 180, 0),
                    ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 1f),
                    ObjectAnimator.ofFloat(imageView, "scaleY", 0f, 1f),
                    ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1));
            //动画周期为500ms
            set.setDuration(500).start();
        }
    }

    public void dismiss() {
        for (int i = 0; i < itemList.size(); i++) {
            // 每个控件相隔角度
            double angel = 90 / (itemList.size() - 1);
            // 计算控件坐标
            double radian = Math.toRadians(angel * i);
            int y = (int) (Math.sin(radian) * distanceRadio);
            int x = (int) (Math.cos(radian) * distanceRadio);
            //
            ImageView imageView = itemList.get(i);
            AnimatorSet set = new AnimatorSet();
            //包含平移、缩放和透明度动画
            set.playTogether(
                    ObjectAnimator.ofFloat(imageView, "translationX", currentPoint.x - x - itemRadio, currentPoint.x - itemRadio),
                    ObjectAnimator.ofFloat(imageView, "translationY", currentPoint.y - y - itemRadio, currentPoint.y - itemRadio),
                    ObjectAnimator.ofFloat(imageView, "rotation", 0, 180),
                    ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0.1f),
                    ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0.1f),
                    ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f));
            //动画周期为500ms
            set.setDuration(500).start();
        }
    }


    public void setImgList(List<Integer> list) {
        this.list = list;
    }

    public void setMenuImg(int resId) {
        this.menuResId = resId;
    }
}
