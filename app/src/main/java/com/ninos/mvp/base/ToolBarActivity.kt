package com.ninos.mvp.base

import android.view.animation.DecelerateInterpolator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.ninos.mvp.R

/**
 * Created by ninos on 2019/1/8.
 */
abstract class ToolBarActivity<P : BasePresenter<*>> : BaseActivity<P>() {
    private lateinit var toolBar: Toolbar
    protected lateinit var imgBack: ImageView
    protected lateinit var tvTitle: TextView
    private lateinit var appBar: AppBarLayout
    protected lateinit var tvAction: TextView
    private var mIsHidden = false

    /**
     * @return 标题
     */
    protected abstract fun provideTitle(): CharSequence

    /**
     * 初始化toolbar
     */
    override fun initThings(savedInstanceState: Bundle?) {
        initToolBar()
    }

    private fun initToolBar() {
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        imgBack = findViewById(R.id.img_back)
        tvTitle = findViewById(R.id.tv_title)
        tvAction = findViewById(R.id.tv_action)
        appBar = findViewById(R.id.app_bar_layout)
        if (canBack())
            imgBack.setOnClickListener { onBackPressed() }
        else
            imgBack.visibility = View.GONE
        if (canAction())
            tvAction.setOnClickListener { action() }
        else
            tvAction.visibility = View.GONE
        tvTitle.text = provideTitle()
    }

    /**
     * Toolbar右边按钮的点击事件
     */
    open fun action() {

    }

    /**
     * @param alpha 设置标题栏的透明度
     */
    protected fun setAppBarAlpha(alpha: Float) {
        appBar.alpha = alpha
    }

    /**
     * 隐藏和显示Toolbar
     */
    protected fun hideOrShowToolbar(isHidden: Boolean) {
        if (mIsHidden != isHidden) {
            appBar.animate()
                .translationY((if (mIsHidden) 0 else -appBar.height).toFloat())
                .setInterpolator(DecelerateInterpolator(2f))
                .start()
            mIsHidden = isHidden
        }
    }

    /**
     * @return 返回按钮是否可以显示
     */
    open fun canBack(): Boolean = true

    /**
     * @return 右边按钮是否显示
     */
    open fun canAction(): Boolean = false
}