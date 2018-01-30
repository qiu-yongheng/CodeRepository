package com.qyh.coderepository.menu.view.flow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author 邱永恒
 * @time 2018/1/8  16:44
 * @desc ${TODD}
 */

public class FlowLayout extends ViewGroup {
    private Context context;
    private int lineWidth = 0;//记录每一行的宽度
    private int lineHeight = 0;//记录每一行的高度
    private int height = 0;//记录整个FlowLayout所占高度
    private int width = 0;//记录整个FlowLayout所占宽度

    public FlowLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public void init() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取系统建议的数值的模式
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 获取子控件数量
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            // 测量子控件(在子控件onMeasure()之后才能调用getMeasuredWidth()获得值)
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 获取xml设置参数
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > measureWidth) {
                //需要换行
                width = Math.max(lineWidth, childWidth);
                height += lineHeight;
                //因为由于盛不下当前控件，而将此控件调到下一行，所以将此控件的高度和宽度初始化给lineHeight、lineWidth
                lineHeight = childHeight;
                lineWidth = childWidth;
            } else {
                // 否则累加值lineWidth,lineHeight取最大高度
                lineHeight = Math.max(lineHeight, childHeight);
                lineWidth += childWidth;
            }

            //最后一行是不会超出width范围的，所以要单独处理
            if (i == count - 1) {
                height += lineHeight;
                width = Math.max(width, lineWidth);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }
}
