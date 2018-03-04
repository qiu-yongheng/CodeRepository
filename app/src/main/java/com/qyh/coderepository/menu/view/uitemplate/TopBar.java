package com.qyh.coderepository.menu.view.uitemplate;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.qyh.coderepository.R;

/**
 * @author 邱永恒
 * @time 2018/2/7  20:01
 * @desc ${TODO}
 */


public class TopBar extends RelativeLayout{
    private String leftButtonText;
    private String rightButtonText;
//    private Drawable

    public TopBar(Context context) {
        super(context);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);

        typedArray.getString(R.styleable.TopBar_leftButtonText);

        typedArray.recycle();
    }
}
