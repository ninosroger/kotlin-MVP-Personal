package com.ninos.mvp.base

import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.ninos.mvp.R

/**
 * Created by ninos on 2019/1/8.
 */
abstract class ToolBarFragment<P : BasePresenter<*>> : BaseFragment<P>() {
    private lateinit var toolBar: Toolbar
    private lateinit var appBar: AppBarLayout
    private lateinit var toolBarBack: ImageView
    private lateinit var toolBarTitle: TextView
    private lateinit var toolBarAction: TextView
    private var mIsHidden = false


    /**
     * @return 标题
     */
    protected abstract fun provideTitle(): CharSequence

    /**
     * 初始化toolbar
     */
    override fun initThings(view: View) {
        initToolBar(view)
    }

    private fun initToolBar(v: View) {
        toolBar = v.findViewById(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolBar)
        toolBarBack = v.findViewById(R.id.toolbar_back)
        toolBarTitle = v.findViewById(R.id.toolbar_title)
        toolBarAction = v.findViewById(R.id.toolbar_action)
        appBar = v.findViewById(R.id.app_bar_layout) as AppBarLayout
        if (canBack())
            toolBarBack.setOnClickListener { (activity as AppCompatActivity).onBackPressed() }
        else
            toolBarBack.visibility = View.GONE
        if (canAction())
            toolBarAction.setOnClickListener { action() }
        else
            toolBarAction.visibility = View.GONE
        toolBarTitle.text = provideTitle()
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
    protected fun hideOrShowToolbar() {
        appBar.animate()
            .translationY((if (mIsHidden) 0 else -appBar.height).toFloat())
            .setInterpolator(DecelerateInterpolator(2f))
            .start()
        mIsHidden = !mIsHidden
    }

    /**
     * @return 返回按钮是否可以显示
     */
    open fun canBack(): Boolean = false

    /**
     * @return 右边按钮是否显示
     */
    open fun canAction(): Boolean = false
}