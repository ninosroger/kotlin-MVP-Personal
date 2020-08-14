package com.ninos.mvp.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatTextView
import com.ninos.mvp.R

/**
 * 为TextView添加宽高可设的Drawable
 *
 * 用法同TextView
 * 参数见 R.styleable.DrawableTextView
 */
class DrawableTextView : AppCompatTextView {
    private var drawableWidth = 0
    private var drawableHeight = 0
    private var drawableTop: Drawable? = null
    private var drawableEnd: Drawable? = null
    private var drawableStart: Drawable? = null
    private var drawableBottom: Drawable? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView)
        val count = typedArray.indexCount
        for (i in 0 until count) {
            var attr = typedArray.getIndex(i)
            when (attr) {
                R.styleable.DrawableTextView_drawableEnd -> drawableEnd =
                    typedArray.getDrawable(attr)
                R.styleable.DrawableTextView_drawableStart -> drawableStart =
                    typedArray.getDrawable(attr)
                R.styleable.DrawableTextView_drawableTop -> drawableTop =
                    typedArray.getDrawable(attr)
                R.styleable.DrawableTextView_drawableBottom -> drawableBottom =
                    typedArray.getDrawable(attr)
                R.styleable.DrawableTextView_drawableWidth -> drawableWidth =
                    typedArray.getDimensionPixelSize(attr, 0)
                R.styleable.DrawableTextView_drawableHeight -> drawableHeight =
                    typedArray.getDimensionPixelSize(attr, 0)
            }
        }
        drawableStart?.setBounds(0, 0, drawableWidth, drawableHeight)
        drawableEnd?.setBounds(0, 0, drawableWidth, drawableHeight)
        drawableTop?.setBounds(0, 0, drawableWidth, drawableHeight)
        drawableBottom?.setBounds(0, 0, drawableWidth, drawableHeight)
        setCompoundDrawables(drawableStart, drawableTop, drawableEnd, drawableBottom)
    }
}