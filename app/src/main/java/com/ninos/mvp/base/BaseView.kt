package com.ninos.mvp.base

import android.content.Context
import android.view.View

/**
 * Created by ninos on 2019/1/8.
 */
interface BaseView {
    fun showToast(text: String)
    fun showSnackbar(text: String, view: View)
    fun startActivity(c: Class<*>)
    fun startActivityForResult(c: Class<*>, requestCode: Int)
    fun showSoftInput(v: View)
    fun hideSoftMethod(v: View)
    fun showDialog(title: String, message: String, cancelable: Boolean)
    fun showDialog(
        title: String,
        message: String,
        cancelable: Boolean,
        btn: String,
        handler: () -> Unit
    )

    fun showDialog(
        title: String,
        message: String,
        cancelable: Boolean,
        confirmStr: String,
        cancelStr: String,
        confirm: () -> Unit,
        cancel: () -> Unit
    )

    fun isShowDialog(): Boolean
    fun dismissDialog()
    fun finishActivity()
}