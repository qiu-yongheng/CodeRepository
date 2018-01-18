package com.qyh.coderepository.menu.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.qyh.coderepository.R;

/**
 * @author 邱永恒
 * @time 2018/1/8  11:55
 * @desc ${TODD}
 */

public class ShadowView extends View{
    private Context context;
    private Paint paint;

    public ShadowView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public ShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {
        setLayerType( LAYER_TYPE_SOFTWARE , null); // 关闭硬件加速
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        /**
         * radius: 模糊半径
         * dx: x轴偏移
         * dy: y轴偏移
         * 阴影颜色
         */
        paint.setShadowLayer(10, 10, 10, Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        paint.setTextSize(40);
        canvas.drawText("这是黄色的阴影", 0, 100, paint);

        canvas.translate(0, 100);

        canvas.drawCircle(20, 20, 20, paint);

        canvas.translate(0, 100);

        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.item_1), 0, 0, paint);

        paint.clearShadowLayer();
        paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER));
        canvas.translate(0, 200);
        canvas.drawCircle(100, 50, 50, paint);

        paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
        canvas.translate(0, 200);
        canvas.drawCircle(100, 50, 50, paint);

        paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER));
        canvas.translate(0, 200);
        canvas.drawCircle(100, 50, 50, paint);

        canvas.restore();
    }
}
