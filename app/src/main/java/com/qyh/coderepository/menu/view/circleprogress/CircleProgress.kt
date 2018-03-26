package com.qyh.coderepository.menu.view.circleprogress

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.blankj.utilcode.util.SizeUtils
import com.qyh.coderepository.R
import com.qyh.coderepository.menu.view.codoon.TabView

/**
 * @author 邱永恒
 *
 * @time 2018/3/19  10:03
 *
 * @desc ${TODD}
 *
 */
class CircleProgress : View {
    /**
     * 画笔
     */
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    /**
     * 文字画笔
     */
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    /**
     * 圆形进度条的宽度
     */
    private var circleStrokeWidth = SizeUtils.dp2px(8f).toFloat()
    /**
     * 圆形进度条的半径
     */
    private var circleRadius = SizeUtils.dp2px(0f).toFloat()
    /**
     * 背景颜色
     */
    private var circleBackgroundColor = Color.GRAY
    /**
     * 进度条颜色
     */
    private var circleProgressColor = Color.BLUE
    /**
     * 进度条圆弧区域
     */
    private var progressRectF = RectF()
    /**
     * 百分比字体大小
     */
    private var textPercentSize = SizeUtils.sp2px(18f).toFloat()
    /**
     * 百分比描述字体大小
     */
    private var textPercentDesSize = SizeUtils.sp2px(10f).toFloat()
    /**
     * 属性字体大小
     */
    private var textAttrSize = SizeUtils.sp2px(15f).toFloat()
    /**
     * 百分比字体颜色
     */
    private var textPercentColor = Color.BLACK
    /**
     * 其他字体颜色
     */
    private var textColor = Color.GRAY
    /**
     * 百分比
     */
    private var percent = 0
    /**
     * 最大进度
     */
    private var maxProgress = 100f
    /**
     * 百分比描述
     */
    private var percentDes = "超越用户"
    /**
     * 属性名
     */
    private var attrText = "最大速度"
    /**
     * 属性值
     */
    private var attrValue = "17.82Km/h"
    /**
     * 圆形距离textview的间距
     */
    private var circleMarginBotton = SizeUtils.dp2px(8f)
    /**
     * textview之间的间距
     */
    private var attrTextMarginBotton = SizeUtils.dp2px(6f)
    /**
     * 百分数rect
     */
    private val percentRect = Rect()
    /**
     * 百分数标注rect
     */
    private val desRect = Rect()
    /**
     * 属性名rect
     */
    private val attrKeyRect = Rect()
    /**
     * 属性值rect
     */
    private val attrValueRect = Rect()
    /**
     * 控件宽度
     */
    private var mWidth: Int = 0
    /**
     * 控件高度
     */
    private var mHeight: Int = 0
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(context, attrs)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs)
    }

    init {
        Log.d(TabView.TAG, "init()")
        paint.strokeWidth = circleStrokeWidth
    }

    private fun initAttrs(context: Context?, attrs: AttributeSet?) {
        val array = context?.obtainStyledAttributes(attrs!!, R.styleable.CircleProgress)
        circleStrokeWidth = array!!.getDimension(R.styleable.CircleProgress_circleStrokeWidth, SizeUtils.dp2px(8f).toFloat())
        circleBackgroundColor = array.getColor(R.styleable.CircleProgress_circleBackground, Color.LTGRAY)
        circleProgressColor = array.getColor(R.styleable.CircleProgress_circleProgressColor, Color.BLUE)
        textPercentColor = array.getColor(R.styleable.CircleProgress_textPercentColor, Color.BLACK)
        textColor = array.getColor(R.styleable.CircleProgress_textColor, Color.LTGRAY)
        textPercentSize = array.getDimension(R.styleable.CircleProgress_textPercentSize, SizeUtils.sp2px(18f).toFloat())
        textPercentDesSize = array.getDimension(R.styleable.CircleProgress_textPercentDesSize, SizeUtils.sp2px(10f).toFloat())
        textAttrSize = array.getDimension(R.styleable.CircleProgress_textAttrSize, SizeUtils.sp2px(15f).toFloat())
        percent = array.getInt(R.styleable.CircleProgress_percent, 0)
        maxProgress = array.getFloat(R.styleable.CircleProgress_maxProgress, 100f)
        try {
            percentDes = array.getString(R.styleable.CircleProgress_percentDes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            attrText = array.getString(R.styleable.CircleProgress_attrText)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            attrValue = array.getString(R.styleable.CircleProgress_attrValue)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 半径
        circleRadius = measuredWidth / 2f - circleStrokeWidth / 2
        // 进度条区域
        progressRectF.left = 0f + circleStrokeWidth / 2f
        progressRectF.right = measuredWidth.toFloat() - circleStrokeWidth / 2f
        progressRectF.top = 0f + circleStrokeWidth / 2f
        progressRectF.bottom = measuredWidth.toFloat() - circleStrokeWidth / 2f
        // 测量属性字体大小
        textPaint.textSize = textAttrSize
        textPaint.getTextBounds(attrText, 0, attrText.length, attrKeyRect)
        textPaint.getTextBounds(attrValue, 0, attrValue.length, attrValueRect)
        // 测量百分比字体大小
        val percent = "${percent * 100 / maxProgress.toInt()}%"
        textPaint.textSize = textPercentSize
        textPaint.getTextBounds(percent, 0, percent.length, percentRect)
        textPaint.textSize = textPercentDesSize
        textPaint.getTextBounds(percentDes, 0, percentDes.length, desRect)


        val wSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val wMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val hSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val hMode = View.MeasureSpec.getMode(heightMeasureSpec)

        if (wMode == View.MeasureSpec.EXACTLY) {
            mWidth = wSize
        } else {
            mWidth = SizeUtils.dp2px(100f)
        }

        if (hMode == View.MeasureSpec.EXACTLY) {
            mHeight = hSize
        } else {
            // 宽度 + 圆距离下面文字的间距 + 两个文字的高度 + 文字之间的间距
            mHeight = mWidth + circleMarginBotton + attrKeyRect.height() + attrTextMarginBotton + attrValueRect.height()
        }
        setMeasuredDimension(mWidth, mHeight)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        textPaint.color = Color.RED
        canvas.drawLine(0f, height / 2f, width.toFloat(), height / 2f, textPaint)
        canvas.drawLine(width / 2f, 0f, width / 2f, height.toFloat(), textPaint)
        // 1. 绘制圆环背景
        drawBackground(canvas)
        // 2. 绘制圆环进度
        drawProgress(canvas)
        // 3. 绘制进度百分比
        drawPercent(canvas)
        // 7. 绘制进度属性具体数据
        drawAttr(canvas)
        canvas.restore()
    }


    /**
     * 绘制属性值
     */
    private fun drawAttr(canvas: Canvas) {
        textPaint.textSize = textAttrSize
        canvas.translate(width / 2f, width.toFloat() + circleMarginBotton)
        canvas.drawText(attrText, -attrKeyRect.width().div(2f), attrKeyRect.height().toFloat(), textPaint)
        canvas.translate(0f, attrKeyRect.height() + attrTextMarginBotton.toFloat())
        canvas.drawText(attrValue, -attrValueRect.width().div(2f), attrValueRect.height().toFloat(), textPaint)
    }


    /**
     * 绘制进度数值
     */
    private fun drawPercent(canvas: Canvas) {
        val percent = "${percent * 100 / maxProgress.toInt()}%"
        val centerH = percentRect.height() - desRect.height()
        // 绘制百分比
        textPaint.textSize = textPercentSize
        textPaint.color = textPercentColor
        canvas.drawText(percent, width.div(2f) - percentRect.width().div(2f), width.div(2f) + centerH - SizeUtils.dp2px(3f), textPaint)
        // 绘制描述
        textPaint.textSize = textPercentDesSize
        textPaint.color = textColor
        canvas.drawText(percentDes, width.div(2f) - desRect.width().div(2f), width.div(2f) + (percentRect.height() + desRect.height()).div(2) + SizeUtils.dp2px(3f), textPaint)
    }

    /**
     * 绘制进度
     */
    private fun drawProgress(canvas: Canvas) {
        paint.color = circleProgressColor
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        val sweepAngle = if (percent / maxProgress >= 1) 360f else 360f * percent / maxProgress
        canvas.drawArc(progressRectF, 90f, sweepAngle, false, paint)
    }

    /**
     * 绘制背景
     */
    private fun drawBackground(canvas: Canvas) {
        paint.color = circleBackgroundColor
        paint.style = Paint.Style.STROKE
        canvas.drawArc(progressRectF, 0f, 360f, false, paint)
    }
}