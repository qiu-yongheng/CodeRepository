package com.qyh.coderepository.menu.view.codoon

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout

/**
 * @author 邱永恒
 *
 * @time 2018/3/5  13:34
 *
 * @desc ${TODD}
 *
 */
class MyFrameLayout : FrameLayout, MyScrollView.OnScrollPercentListener, TabIndicatorView.OnTabClickListener {
    private var once: Boolean = false
    private var screenWidth = 0
    private var screenHeight = 0
    private var mapHeight = 0
    private lateinit var headView: LinearLayout
    private lateinit var scrollView: MyScrollView
    private lateinit var mapView: View
    private lateinit var cardView: CardView
    private var mapBottomPadding = 300

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initAttr()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttr()
    }

    companion object {
        val TAG = "MyFrameLayout"
    }

    private fun initAttr() {
        Log.d(TAG, "initAttr()")
        // 获取屏幕宽度
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        screenWidth = outMetrics.widthPixels
        screenHeight = outMetrics.heightPixels

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d(TAG, "onMeasure()")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d(TAG, "onLayout()")
        // 获取头
        headView = getChildAt(1) as LinearLayout
        val tabIndicatorView = headView.getChildAt(1) as TabIndicatorView
        tabIndicatorView.setOnTabClickListener(this)
        // 获取滚动控件
        scrollView = getChildAt(0) as MyScrollView
        scrollView.setOnScrollPercentListener(this)
        Log.d(TAG, "head height: ${headView.measuredHeight}")
        scrollView.setMarginTop(headView.measuredHeight.toFloat())
    }

    /**
     * 滚动回调
     * 设置headView透明度
     */
    override fun onScroll(percent: Float) {
        Log.d(TAG, "percent: $percent")
        headView.alpha = percent
        when (percent) {
            0f -> {headView.visibility = View.GONE}
            else -> {headView.visibility = visibility}
        }
    }

    /**
     * tab指示器点击监听
     * 根据点击事件展开地图
     */
    override fun onClick(v: View) {
        when (v.id) {
            1 -> {scrollView.openHead()}
            2 -> {}
            3 -> {}
        }
    }
}
