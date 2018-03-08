package com.qyh.coderepository.menu.view.codoon

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.SizeUtils

/**
 * @author 邱永恒
 *
 * @time 2018/3/5  17:27
 *
 * @desc ${TODD}
 *
 */
class TabView : View {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var backColor = Color.BLACK
    private var clickColor = Color.GREEN
    private var text = "请输入标题"
    private var isClicked = false
    private var strokeWidth = SizeUtils.dp2px(2f).toFloat()
    private var textWidth = 0
    private var textHeight = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    companion object {
        val TAG = "TabView"
    }

    init {
        Log.d(TAG, "init()")
        paint.strokeWidth = strokeWidth
        paint.textSize = SizeUtils.dp2px(18f).toFloat()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val defaultSize = SizeUtils.dp2px(45f)
        setMeasuredDimension(View.resolveSize(defaultSize, widthMeasureSpec), View.resolveSize(defaultSize, heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(TAG, "onDraw(): $text")
        canvas!!.save()
        // 绘制分隔线
        paint.color = Color.GRAY
        canvas.drawLine(0f, height - 1f, width.toFloat(), height - 1f, paint)
        // 绘制文字和下划线
        paint.textAlign = Paint.Align.CENTER
        paint.color = if (isClicked) clickColor else backColor
        canvas.drawText(text, width.div(2f), height.div(2f) + textHeight.div(2), paint)
        if (isClicked) {
            canvas.drawLine(0f, height - strokeWidth, width.toFloat(), height - strokeWidth, paint)
        }
        canvas.restore()
    }

    fun setBackColor(color: Int) {
        backColor = color
    }

    fun setClickColor(color: Int) {
        clickColor = color
    }

    fun setText(content: String) {
        Log.d(TAG, "setText(): $content")
        this.text = content
        // 测量文字大小
        val rect = Rect()
        paint.getTextBounds(text, 0, text.length, rect)
        textWidth = rect.width()
        textHeight = rect.height()
    }

    fun isClicked(): Boolean {
        return isClicked
    }

    fun setClicked(isClicked: Boolean) {
        this.isClicked = isClicked
        invalidate()
    }
}