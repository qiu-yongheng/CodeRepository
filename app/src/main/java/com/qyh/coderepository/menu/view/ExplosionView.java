package com.qyh.coderepository.menu.view;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

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
    private int menuResId;
    private PointF startPoint;
    private PointF currentPoint;
    private ImageView ivMenu;
    private boolean isTouch;
    private boolean isClick;

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

    public void setImgList(List<Integer> list) {
        this.list = list;
    }

    public void setMenuImg(int resId) {
        this.menuResId = resId;
    }

    private void initView() {
        // 初始化画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 初始化开始点
        startPoint = new PointF(500, 500);
        currentPoint = new PointF();

        // 初始化item控件
        list.add(R.mipmap.item_2);
        list.add(R.mipmap.item_3);
        list.add(R.mipmap.item_4);
        list.add(R.mipmap.item_5);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (Integer integer : list) {
            itemList.add(createView(layoutParams, integer, itemRadio, false));
        }

        // 初始化menu控件
        ivMenu = createView(layoutParams, R.mipmap.item_1, menuRadio, true);
        ivMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClick) {
                    isClick = true;
                    show();
                } else {
                    isClick = false;
                    dismiss();
                }
            }
        });
    }

    public void show() {
        ImageView imageView = itemList.get(0);
        imageView.setVisibility(VISIBLE);
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(imageView, "translationX", startPoint.x - imageView.getWidth()/2, startPoint.x-400),
                ObjectAnimator.ofFloat(imageView, "translationY", startPoint.y - imageView.getHeight()/2, startPoint.y-300),
                ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(imageView, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1));
        //动画周期为500ms
        set.setDuration(1000).start();
    }

    public void dismiss() {
        ImageView imageView = itemList.get(0);
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(imageView, "translationX", startPoint.x-400, startPoint.x - imageView.getWidth()/2),
                ObjectAnimator.ofFloat(imageView, "translationY", startPoint.y-300, startPoint.y - imageView.getHeight()/2),
                ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0f));
        //动画周期为500ms
        set.setDuration(1000).start();
    }

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
        this.addView(imageView);
        return imageView;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (isTouch) {
            ivMenu.setX(currentPoint.x - ivMenu.getWidth() / 2);
            ivMenu.setY(currentPoint.y - ivMenu.getHeight() / 2);
        } else {
            ivMenu.setX(startPoint.x - ivMenu.getWidth() / 2);
            ivMenu.setY(startPoint.y - ivMenu.getHeight() / 2);
        }
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
        return false;
    }
}
