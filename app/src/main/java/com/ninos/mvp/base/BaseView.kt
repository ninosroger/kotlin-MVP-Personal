package com.ninos.mvp.base

import android.view.View

/**
 * @author Ninos
 *
 * Activity和Fragment常用方法接口，统一方法名，便于开发
 */
interface BaseView {
    /**
     * 简化Toast show方法
     *
     * @param text 字符串类型消息文本
     */
    fun showToast(text: CharSequence)

    /**
     * 简化Snackbar show方法
     *
     * @param text 字符串类型消息文本
     */
    fun showSnackbar(text: CharSequence)

    /**
     * 简化startActivity方法
     *
     * @param cls 要打开的Activity
     */
    fun startActivity(cls: Class<*>)

    /**
     * 简化startActivityForResult方法
     *
     * @param cls 要打开的Activity
     * @param requestCode 请求code
     */
    fun startActivityForResult(cls: Class<*>, requestCode: Int)

    /**
     * 显示软键盘
     *
     * @param view 显示软键盘后需要获取焦点的view
     */
    fun showSoftInput(view: View)

    /**
     * 隐藏软键盘
     */
    fun hideSoftMethod()

    /**
     * 简化常用dialog方法，具体看参数
     * 用于loading弹框
     *
     * @param resId 资源id，loading布局文件
     * @param cancelable 点击dialog以外是否可以关闭dialog
     */
    fun showDialog(resId: Int, cancelable: Boolean)

    /**
     * 简化常用dialog方法，具体看参数
     * 单按钮dialog
     *
     * @param title dialog标题
     * @param message dialog提示内容文本
     * @param cancelable 点击dialog以外是否可以关闭dialog
     * @param btn 单按钮文本
     * @param handler 按钮的点击回调
     */
    fun showDialog(
        title: String,
        message: String,
        cancelable: Boolean,
        btn: String,
        handler: () -> Unit
    )

    /**
     * 简化常用dialog方法，具体看参数
     *
     * @param title dialog标题
     * @param message dialog提示内容文本
     * @param cancelable 点击dialog以外是否可以关闭dialog
     * @param confirmStr confirm文本，例：确认  同意  好  前往  是
     * @param cancelStr cancel标题，例：取消  拒绝  否  关闭
     * @param confirm confirm的点击回调
     * @param cancel cancel的点击回调
     */
    fun showDialog(
        title: String,
        message: String,
        cancelable: Boolean,
        confirmStr: String,
        cancelStr: String,
        confirm: () -> Unit,
        cancel: () -> Unit
    )

    /**
     * 判断dialog是否显示
     *
     * @return 布尔类型，true为显示，false为未显示
     */
    fun isShowDialog(): Boolean

    /**
     * 关闭dialog
     */
    fun dismissDialog()

    /**
     * 结束Activity方法
     */
    fun finishActivity()
}