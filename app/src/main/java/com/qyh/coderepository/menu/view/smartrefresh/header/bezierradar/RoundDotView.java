package com.qyh.coderepository.menu.view.smartrefresh.header.bezierradar;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;


/**
 * @author 邱永恒
 * @time 2018/2/2  11:34
 * @desc 绘制7个小圆点, 根据手指Y轴滑动距离设置7个小圆点的距离
 */
public class RoundDotView extends View {

    private int num = 7;
    private Paint mPath;
    private float mRadius;
    private float fraction;

    public RoundDotView(Context context) {
        super(context);
        initView();
    }

    public RoundDotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RoundDotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    public void initView() {
        mPath = new Paint();
        mPath.setAntiAlias(true);
        mPath.setColor(Color.WHITE);
        mRadius = SizeUtils.dp2px(7);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    public void setDotColor(@ColorInt int color) {
        mPath.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        /** 1. 计算每个圆点控件的显示区域的宽 */
        // width = t*(w/n)-(t>1)*((t-1)*(w/n)/t)
        // 当fraction <= 1时, 每个圆点的的显示区域是: (屏幕宽度 / 圆点数量) * 显示百分比
        // 当fraction > 1时, 会产生下拉阻尼效果, 公式: 圆点显示区域 - ((显示百分比 - 1) * (屏幕宽度 / 圆点数量) / 显示百分比)
        float wide = (width / num) * fraction - ((fraction > 1) ? ((fraction - 1) * (width / num) / fraction) : 0);

        /** 2. 计算控件高度 */
        // y2 = x - (t>1)*((t-1)*x/t);
        float high = height - ((fraction > 1) ? ((fraction - 1) * height / 2 / fraction) : 0);

        for (int i = 0; i < num; i++) {
            /** 3. 居中索引 */
            float index = 1f + i - (1f + num) / 2; // y3 = (x + 1) - (n + 1)/2; 居中 index 变量：0 1 2 3 4 结果： -2 -1 0 1 2
            /** 4. 计算透明度 */
            float alpha = 255 * (1 - (2 * (Math.abs(index) / num))); // y4 = m * ( 1 - 2 * abs(y3) / n); 横向 alpha 差
            float x = SizeUtils.px2dp(height);
            mPath.setAlpha((int) (alpha * (1d - 1d / Math.pow((x / 800d + 1d), 15))));//y5 = y4 * (1-1/((x/800+1)^15));竖直 alpha 差
            /** 5. 计算圆球半径 */
            float radius = mRadius * (1 - 1 / ((x / 10 + 1)));//y6 = mRadius*(1-1/(x/10+1));半径
            /** 6. 绘制圆球 */
            canvas.drawCircle(width / 2 + wide * index, high / 2, radius, mPath);
        }
    }

    public void setFraction(float fraction) {
        this.fraction = fraction;
        invalidate();
    }

    public void startAnim(float fraction) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "fraction", fraction, 0f, fraction);
        animator.setDuration(5000).setRepeatCount(ObjectAnimator.INFINITE);
        animator.start();
    }
}
