package com.ninos.mvp.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Created by ninos on 2019/1/8.
 */
abstract class BaseFragment<P : BasePresenter<*>> : Fragment(), BaseView {

    lateinit var presenter: P
    private var dialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(provideLayoutId(), container, false)
        presenter = createPresenter()
        presenter.attachView(this)
        initThings(view)
        initListeners()
        return view
    }

    /**
     * 获取布局文件d
     */
    protected abstract fun provideLayoutId(): Int


    /**
     * 初始化事件监听者
     */
    abstract fun initListeners()

    /**
     * 初始化
     */
    protected abstract fun initThings(view: View)

    /**
     * 绑定Presenter
     */
    abstract fun createPresenter(): P

    /**
     * 显示Toast
     */
    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun showSnackbar(text: String, view: View) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
    }

    /**
     * 启动Actviity
     */
    override fun startActivity(c: Class<*>) {
        startActivity(Intent(context, c))
    }

    /**
     * 启动Actviity
     */
    override fun startActivityForResult(c: Class<*>, requestCode: Int) {
        startActivityForResult(Intent(context, c), requestCode)
    }

    /**
     * 获取当前Context
     */
    override fun getContext(): Context = activity!!

    /**
     * 显示输入法界面
     */
    override fun showSoftInput(v: View) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED)
    }

    /**
     * 隐藏输入法界面
     */
    override fun hideSoftMethod(v: View) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }

    /**
     * Dialog提示
     */
    override fun showDialog(title: String, message: String, cancelable: Boolean) {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(cancelable)
        dialog = builder.create()
        dialog!!.show()
    }

    /**
     * Dialog提示:带确定button
     */
    override fun showDialog(title: String, message: String, cancelable: Boolean, btn: String, handler: () -> Unit) {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(btn) { _, _ -> handler() }
        dialog = builder.create()
        dialog!!.show()
    }

    /**
     * Dialog提示:带确定,取消button
     */
    override fun showDialog(
        title: String,
        message: String,
        cancelable: Boolean,
        confirmStr: String,
        cancelStr: String,
        confirm: () -> Unit,
        cancel: () -> Unit
    ) {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(confirmStr) { _, _ -> confirm() }
        builder.setNegativeButton(cancelStr) { _, _ -> cancel() }
        dialog = builder.create()
        dialog!!.show()
    }

    /**
     * 是否显示Dialog
     */
    override fun isShowDialog(): Boolean {
        return if (dialog == null) false else dialog!!.isShowing
    }

    /**
     * 关闭Dialog
     */
    override fun dismissDialog() {
        if (dialog != null)
            dialog!!.dismiss()
    }

    override fun finishActivity() {
        activity!!.finish()
    }
}