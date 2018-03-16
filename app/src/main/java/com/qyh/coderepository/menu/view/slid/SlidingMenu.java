package com.qyh.coderepository.menu.view.slid;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.kc.common.util.log.LoggerUtil;
import com.qyh.coderepository.R;

/**
 * @author 邱永恒
 * @time 2018/2/5  21:56
 * @desc 仿QQ侧滑菜单(继承水平滚动控件实现)
 */
public class SlidingMenu extends HorizontalScrollView {
    /**
     * ScrollView唯一线性布局
      */
    private LinearLayout mWrapper;
    /**
     * 菜单控件
     */
    private ViewGroup mMenu;
    /**
     * 内容控件
     */
    private ViewGroup mContent;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 菜单宽度
     */
    private int mMenuWidth;
    /**
     * 菜单距离右侧屏幕距离(dp)
     */
    private int mMenuRightPadding = 50;
    /**
     * 标示是否测量过
     */
    private boolean once;
    /**
     * 标识菜单是否打开
     */
    private boolean isOpen;

    /**
     * 在代码中new对象时, 调用
     *
     * @param context
     */
    public SlidingMenu(Context context) {
        this(context, null);
    }

    /**
     * 未使用自定义属性时，调用
     *
     * @param context
     * @param attrs
     */
    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 当使用了自定义属性时，会调用此构造方法(这里应该有问题, 应该两个参数的构造方法也可以获取自定义属性)
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 获取我们定义的属性
        initAttr(context, attrs, defStyle);
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyle) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyle, 0);
        mMenuRightPadding = array.getDimensionPixelSize(R.styleable.SlidingMenu_rightPadding, 50);
        array.recycle();

        // 获取屏幕宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
    }

    /**
     * 设置子View的宽和高 设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            // 获取XML布局中添加的线性布局
            mWrapper = (LinearLayout) getChildAt(0);
            // 获取线性布局中的菜单布局
            mMenu = (ViewGroup) mWrapper.getChildAt(0);
            // 获取线性布局中的内容布局
            mContent = (ViewGroup) mWrapper.getChildAt(1);
            // 重新设置菜单布局的宽度
            mMenuWidth = mMenu.getLayoutParams().width =
                    mScreenWidth - mMenuRightPadding;
            // 重新设置内容布局的宽度为屏幕宽度
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        // 修改子控件的尺寸后, ViewGroup的尺寸就会重新计算
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过设置偏移量，将menu隐藏
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 默认显示内容布局
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                // 获取隐藏在左边的宽度
                int scrollX = getScrollX();
                if (scrollX >= mMenuWidth / 2) {
                    // 滑动不到菜单宽度一半, 显示内容控件
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {
                    // 显示菜单控件
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
                // 拦截触摸事件
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 判断是否已打开Menu
     * @return
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen)
            return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (!isOpen)
            return;
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
    }

    /**
     * 切换菜单
     */
    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    /**
     *
     * @param l Current horizontal scroll origin.
     * @param t Current vertical scroll origin.
     * @param oldl Previous horizontal scroll origin.
     * @param oldt Previous vertical scroll origin.
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LoggerUtil.d("l: " + l + "\nt: " + t + "\noldl: " + oldl + "\noldt: " + oldt);
        // 计算缩放比: 滚动距离 / 菜单宽度
        float scale = l * 1.0f / mMenuWidth; // 1 ~ 0

        /**
         * 区别1：内容区域1.0~0.7 缩放的效果 scale : 1.0~0.0 -> 0.7 + 0.3 * scale
         *
         * 区别2：菜单的偏移量需要修改
         *
         * 区别3：菜单的显示时有缩放以及透明度变化 缩放：0.7 ~1.0 -> 1.0 - scale * 0.3
         *       透明度 0.6 ~ 1.0 -> 0.6+ 0.4 * (1- scale) ;
         */
        float rightScale = 0.7f + 0.3f * scale; // 内容控件缩放比
        float leftScale = 1.0f - scale * 0.3f; // 菜单控件缩放比
        float leftAlpha = 0.6f + 0.4f * (1 - scale); // 菜单控件透明度

        // 调用属性动画，设置TranslationX(视觉感觉, 菜单一直显示在内容控件下面)
        mMenu.setTranslationX(mMenuWidth * scale * 0.8f);
        mMenu.setScaleX(leftScale); // 0.7f ~ 1.0f
        mMenu.setScaleY(leftScale); // 0.7f ~ 1.0f
        mMenu.setAlpha(leftAlpha); // 0.6f ~ 1.0f
        // 设置content的缩放的中心点
        mContent.setPivotX(0);
        mContent.setPivotY(mContent.getHeight() / 2);
        mContent.setScaleX(rightScale); // 1.0f ~ 0.7f
        mContent.setScaleY(rightScale); // 1.0f ~ 0.7f
    }

}
