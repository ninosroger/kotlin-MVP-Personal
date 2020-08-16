package com.ninos.mvp.base

import android.view.animation.DecelerateInterpolator
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.ninos.mvp.R

/**
 * @author Ninos
 *
 * 统一Toolbar的Activity，封装常用Toolbar控件及事件
 * 带自定义toolbar不要继承此类
 */
abstract class ToolBarActivity<P : BasePresenter> : BaseActivity<P>() {
    /**
     * toolbar控件
     */
    private lateinit var toolBar: Toolbar
    /**
     * appbarLayout控件，滚动动效及toolbar阴影等样式设置
     */
    private lateinit var appBar: AppBarLayout
    /**
     * 返回按钮控件
     */
    private lateinit var toolBarBack: ImageView
    /**
     * 标题控件
     */
    private lateinit var toolBarTitle: TextView
    /**
     * 右边预留文本控件
     */
    private lateinit var toolBarActionTv: TextView
    /**
     * 右边预留图片控件
     */
    private lateinit var toolBarActionIv: ImageView
    /**
     * 是否隐藏,防止重复显示及隐藏参数
     * 禁止变量名以is开头
     */
    private var mIsHidden = false

    /**
     * 顶部标题文本设置
     *
     * @return CharSequence类型文本
     */
    protected abstract fun provideTitle(): CharSequence

    /**
     * 准备初始化toolbar
     * 子类集成时，一定要super，否则不走Toolbar控件初始化代码
     */
    override fun initThings() {
        initToolBar()
    }

    /**
     * 初始化toolbar
     */
    private fun initToolBar() {
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        toolBarBack = findViewById(R.id.toolbar_back)
        toolBarTitle = findViewById(R.id.toolbar_title)
        toolBarActionTv = findViewById(R.id.toolbar_action_tv)
        toolBarActionIv = findViewById(R.id.toolbar_action_iv)
        appBar = findViewById(R.id.app_bar_layout)
        if (canBack())
            toolBarBack.setOnClickListener { onBackPressed() }
        else
            toolBarBack.visibility = View.GONE
        if (canTvAction())
            toolBarActionTv.setOnClickListener { tvAction() }
        else
            toolBarActionTv.visibility = View.GONE
        if (canIvAction())
            toolBarActionIv.setOnClickListener { ivAction() }
        else
            toolBarActionIv.visibility = View.GONE
        toolBarTitle.text = provideTitle()
    }

    /**
     * Toolbar右边文本控件的点击事件
     */
    open fun tvAction() {

    }

    /**
     * Toolbar右边图片控件的点击事件
     */
    open fun ivAction() {

    }

    /**
     * 设置标题栏的透明度
     *
     * @param alpha 透明数值，float类型，0.0为透明，1.0为不透明
     */
    protected fun setAppBarAlpha(alpha: Float) {
        appBar.alpha = alpha
    }

    /**
     * 隐藏和显示Toolbar
     *
     * 使用animate动态隐藏显示toolbar
     *
     * @param isHidden true为隐藏，false不隐藏
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
     * 动态更改title
     *
     * @param title title文本
     */
    protected fun setTitleName(title: CharSequence) {
        toolBarTitle.text = title
    }


    /**
     * 是否可以返回
     *
     * @return true为可返回，false为不可返回
     */
    open fun canBack(): Boolean = true

    /**
     * toolbar右侧文本控件是否响应事件
     *
     * @return false为不响应，true为响应
     */
    open fun canTvAction(): Boolean = false

    /**
     * toolbar右侧图片控件是否响应事件
     *
     * @return false为不响应，true为响应
     */
    open fun canIvAction(): Boolean = false
}