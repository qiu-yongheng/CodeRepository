package com.qyh.coderepository.menu.view.codoon

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

/**
 * @author 邱永恒
 *
 * @time 2018/3/5  16:23
 *
 * @desc ${TODD}
 *
 */
class TabIndicatorView : LinearLayout, View.OnClickListener {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val array = arrayOf("详情", "配速", "图表")
    private val views = arrayListOf<TabView>()
    private var listener: OnTabClickListener? = null

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        // 水平布局
        orientation = HORIZONTAL
        for (title in array) {
            val tabView = TabView(context)
            tabView.setText(title)
            val layoutParams = LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f)
            tabView.layoutParams = layoutParams
            tabView.id = generateViewId()
            tabView.setOnClickListener(this)
            views.add(tabView)
            addView(tabView)
        }
    }

    override fun onClick(v: View?) {
        reset()
        findViewById<TabView>(v!!.id).setClicked(true)
        listener?.onClick(v)
    }

    /**
     * 初始化所有tab的状态
     */
    private fun reset() {
        (0 until childCount)
                .map { getChildAt(it) as TabView }
                .forEach { it.setClicked(false) }
    }

    interface OnTabClickListener {
        fun onClick(v: View)
    }

    fun setOnTabClickListener(listener: OnTabClickListener) {
        this.listener = listener
    }
}