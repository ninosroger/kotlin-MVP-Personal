package com.ninos.mvp.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

/**
 * @author Ninos
 *
 * 基础Activity，对重复性代码封装，开放常用方法
 * 为了方便开发，Activity和Fragment开放的方法名和参数尽量做到保持一致
 *
 * @param P 泛型类型，是BasePresenter子类
 */
abstract class BaseActivity<P : BasePresenter<*>> : AppCompatActivity(), BaseView {
    private lateinit var context: Context
    lateinit var presenter: P
    private lateinit var dialog: AlertDialog

    /**
     * 基础Activity初始化
     *
     * 开放方法的加载顺序
     * beforeProvideLayoutId -> provideLayoutId -> createPresenter -> initThings -> initListeners
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        beforeProvideLayoutId()
        setContentView(provideLayoutId())
        presenter = createPresenter()
        presenter.attachView(this)
        initThings(savedInstanceState)
        initListeners()
    }

    /**
     * 获取布局文件
     *
     * @return Int类型资源id，布局文件
     */
    protected abstract fun provideLayoutId(): Int

    /**
     * 加载布局之前的操作方法
     */
    protected fun beforeProvideLayoutId() {}

    /**
     * 初始化事件监听者
     */
    abstract fun initListeners()

    /**
     * 初始化
     */
    protected abstract fun initThings(savedInstanceState: Bundle?)

    /**
     * 绑定Presenter
     */
    abstract fun createPresenter(): P

    /**
     * 显示Toast
     */
    override fun showToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

    override fun showSnackbar(text: String, view: View) =
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()

    /**
     * 启动Actviity
     */
    override fun startActivity(c: Class<*>) = startActivity(Intent(context, c))


    /**
     * 启动Actviity
     */
    override fun startActivityForResult(c: Class<*>, requestCode: Int) =
        startActivityForResult(Intent(context, c), requestCode)

    override fun showSoftInput(v: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED)
    }

    /**
     * 隐藏输入法界面
     */
    override fun hideSoftMethod() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    /**
     * Dialog提示
     */
    override fun showDialog(resId: Int, cancelable: Boolean) {
        if (dialog != null && dialog!!.isShowing)
            dialog!!.dismiss()
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setCancelable(cancelable)
        dialog = builder.create()
        dialog!!.show()
    }

    /**
     * Dialog提示:带确定button
     */
    override fun showDialog(
        title: String,
        message: String,
        cancelable: Boolean,
        btn: String,
        handler: () -> Unit
    ) {
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
    override fun isShowDialog(): Boolean = if (dialog == null) false else dialog!!.isShowing

    /**
     * 关闭Dialog
     */
    override fun dismissDialog() {
        if (dialog != null)
            dialog!!.dismiss()
    }

    override fun finishActivity() = onBackPressed()
}