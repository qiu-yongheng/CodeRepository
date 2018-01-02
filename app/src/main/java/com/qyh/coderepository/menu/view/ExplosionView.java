package com.qyh.coderepository.menu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 邱永恒
 * @time 2018/1/2  16:01
 * @desc ${TODD}
 */

public class ExplosionView extends FrameLayout{
    private final Context context;
    private List<Integer> list;
    private Paint paint;
    {
        list = new ArrayList<>();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public ExplosionView(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public ExplosionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ExplosionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        // 创建控件
        ImageView ivMenu = new ImageView(context);

    }
}
