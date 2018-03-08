package com.qyh.coderepository.menu.view.codoon

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent

/**
 * @author 邱永恒
 *
 * @time 2018/3/6  16:43
 *
 * @desc ${TODD}
 *
 */
class MyImageView : RecyclerView{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("MyImageView", "onTouchEvent()")
        return false
    }
}