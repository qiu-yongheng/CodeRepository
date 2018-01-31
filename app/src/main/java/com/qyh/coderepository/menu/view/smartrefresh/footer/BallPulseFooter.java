package com.qyh.coderepository.menu.view.smartrefresh.footer;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SizeUtils;
import com.qyh.coderepository.menu.view.smartrefresh.footer.ballpulse.BallPulseView;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.getSize;
import static android.view.View.MeasureSpec.makeMeasureSpec;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 球脉冲底部加载组件
 * Created by SCWANG on 2017/5/30.
 */

public class BallPulseFooter extends ViewGroup{

    private BallPulseView mBallPulseView;

    //<editor-fold desc="ViewGroup">
    public BallPulseFooter(@NonNull Context context) {
        super(context);
        initView(context, null, 0);
    }

    public BallPulseFooter(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
    }

    public BallPulseFooter(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        // 添加脉冲球控件
        mBallPulseView = new BallPulseView(context);
        addView(mBallPulseView, WRAP_CONTENT, WRAP_CONTENT);
        setMinimumHeight(SizeUtils.dp2px(60));
    }

    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 创建测量规范: wrap_content
        int widthSpec = makeMeasureSpec(getSize(widthMeasureSpec), AT_MOST);
        int heightSpec = makeMeasureSpec(getSize(heightMeasureSpec), AT_MOST);
        // 测量子控件(设置测量规范)
        mBallPulseView.measure(widthSpec, heightSpec);
        // 保存容器测量大小
        setMeasuredDimension(
                resolveSize(mBallPulseView.getMeasuredWidth(), widthMeasureSpec),
                resolveSize(mBallPulseView.getMeasuredHeight(), heightMeasureSpec)
        );


    }

    /**
     * 布局子控件
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 父控件尺寸
        int pwidth = getMeasuredWidth();
        int pheight = getMeasuredHeight();
        // 子控件尺寸
        int cwidth = mBallPulseView.getMeasuredWidth();
        int cheight = mBallPulseView.getMeasuredHeight();
        // 计算子控件原点位置
        int left = pwidth / 2 - cwidth / 2;
        int top = pheight / 2 - cheight / 2;
        mBallPulseView.layout(left, top, left + cwidth, top + cheight);
    }

    /**
     * 启动子控件的动画
     */
    public void onStartAnimator() {
        mBallPulseView.startAnim();
    }

    public int onFinish() {
        mBallPulseView.stopAnim();
        return 0;
    }

    @Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (colors.length > 1) {
            mBallPulseView.setNormalColor(colors[1]);
            mBallPulseView.setAnimatingColor(colors[0]);
        } else if (colors.length > 0) {
            mBallPulseView.setNormalColor(ColorUtils.compositeColors(0x99ffffff,colors[0]));
            mBallPulseView.setAnimatingColor(colors[0]);
        }
    }
    @NonNull
    public View getView() {
        return this;
    }
}
