package com.qyh.coderepository.menu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;

/**
 * @author 邱永恒
 * @time 2018/2/3  16:49
 * @desc ${TODD}
 */

public class BaseLlView extends LinearLayout {
    private Paint paint;
    private int textSize;

    public BaseLlView(Context context) {
        super(context);
    }

    public BaseLlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(SizeUtils.dp2px(1));
        textSize = SizeUtils.sp2px(12);
    }

    /**
     * 绘制子控件
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);


        paint.setTextSize(textSize);
        canvas.drawText("X轴", getWidth() - 40 - textSize, getHeight() / 2 + 10 + textSize, paint);
        canvas.drawText("Y轴", getWidth() / 2 + 10, 10 + textSize, paint);
        super.dispatchDraw(canvas);
    }
}
