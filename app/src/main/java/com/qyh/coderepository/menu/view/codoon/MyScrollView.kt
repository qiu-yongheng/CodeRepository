package com.qyh.coderepository.menu.view.codoon

import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ScrollView
import com.blankj.utilcode.util.SizeUtils


/**
 * @author 邱永恒
 *
 * @time 2018/3/6  13:15
 *
 * @desc ${TODD}
 *
 */
class MyScrollView : ScrollView {
    private var once: Boolean = false
    private var isOpen: Boolean = true
    private var screenWidth = 0
    private var screenHeight = 0
    private var mapHeight = 0f
    private var downY = 0f
    private var scrollMap = 0f
    private var marginTop = SizeUtils.dp2px(90f).toFloat()
    private var downTime = 0L
    private var upTime = 0f
    private var oldScrollY = 0
    private lateinit var headView: LinearLayout
    private lateinit var scrollView: ScrollView
    private lateinit var mapView: View
    private lateinit var contentView: View
    private var mapBottomPadding = 700
    private var listener: OnScrollPercentListener? = null
    private var velocityTracker: VelocityTracker? = null
    private var velocityY = 0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initAttr()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttr()
    }

    companion object {
        val TAG = "MyScrollView"
    }

    private fun initAttr() {
        // 获取屏幕宽度
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        screenWidth = outMetrics.widthPixels
        screenHeight = outMetrics.heightPixels
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!once) {
            Log.d(TAG, "onMeasure()")
            val linearLayout = getChildAt(0) as LinearLayout
            // 获取地图控件
            mapView = linearLayout.getChildAt(0)
            // 获取数据列表
            contentView = linearLayout.getChildAt(1)
            // 重新计算地图控件高度
            mapView.layoutParams.height = screenHeight - mapBottomPadding
            mapHeight = mapView.layoutParams.height.toFloat()
            once = true
        }
        // 父控件计算子控件尺寸
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        val index = ev?.actionIndex
        val action = ev?.actionMasked
        val pointerId = ev?.getPointerId(index!!)
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                downTime = System.currentTimeMillis()
                if (velocityTracker == null) {
                    velocityTracker = VelocityTracker.obtain()
                } else {
                    velocityTracker?.clear()
                }
                velocityTracker?.addMovement(ev)
            }
            MotionEvent.ACTION_MOVE -> {
                velocityTracker?.addMovement(ev)
                velocityTracker?.computeCurrentVelocity(1000)
                velocityY = -(velocityTracker?.getYVelocity(pointerId!!)!!)
                Log.d(TAG, "X speed: $velocityY")
            }
            MotionEvent.ACTION_UP -> {
                scrollMap = screenHeight - mapBottomPadding - marginTop
                upTime = (System.currentTimeMillis() - downTime).div(1000f)
                Log.d(TAG, "scrollY: $scrollY, oldScrollY: $oldScrollY, 滑动时间: $upTime, 滑动速度: ${(scrollY - oldScrollY).div(upTime)}")
                if (scrollY > mapHeight.div(2) && scrollY <= scrollMap) {
                    // 当head正在显示, 且滚动距离大于mapHeight一半时, 显示内容控件
                    smoothScrollTo(0, (mapHeight - marginTop).toInt())
                    isOpen = false
                } else if (scrollY < mapHeight.div(2) && scrollY <= scrollMap) {
                    // 显示地图控件
                    smoothScrollTo(0, 0)
                    isOpen = true
                } else {
                    super.fling(velocityY.toInt())
                }
                oldScrollY = scrollY
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                velocityTracker?.recycle()
            }
        }
        return super.onTouchEvent(ev)
    }

    /**
     * 滚动监听
     */
    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        // 计算缩放比: 滚动距离 / 地图高度 = 1 ~ 0
        val scale = t * 1.0f / (mapHeight - marginTop)
        // 透明度变化 0.1 ~ 1.0 -> 0.1+ 0.9 * (1- scale) ;
        val alpha = 0.1f + 0.9f * (1 - scale)

        // 平移 0 ~ 0.8f
        mapView.translationY = mapHeight * scale * 0.8f
        mapView.alpha = alpha

        listener?.onScroll(scale)
    }

    /**
     * 打开头
     */
    fun openHead() {
        if (!isOpen) {
            smoothScrollTo(0, 0)
            isOpen = true
        }
    }

    /**
     * 关闭头
     */
    fun closeHead() {
        if (isOpen) {
            smoothScrollTo(0, (mapHeight - marginTop).toInt())
            isOpen = false
        }
    }

    /**
     * 切换头显示
     */
    fun toggle() {
        if (isOpen) closeHead() else openHead()
    }

    /**
     * 设置显示内容控件时, 距离顶部的距离
     */
    fun setMarginTop(margin: Float) {
        marginTop = margin
    }

    interface OnScrollPercentListener {
        fun onScroll(percent: Float)
    }

    /**
     * 设置滚动监听器给外界使用
     */
    fun setOnScrollPercentListener(listener: OnScrollPercentListener) {
        this.listener = listener
    }

    /**
     * 如果Head isOpen, 不拦截触摸事件, 给子类处理
     * 如果Head isClose, 自己处理触摸事件
     */
//    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        return true
//    }

    override fun fling(velocityY: Int) {
        super.fling(velocityY)
        Log.d(TAG, "松开手的初速度: $velocityY")
    }
}
