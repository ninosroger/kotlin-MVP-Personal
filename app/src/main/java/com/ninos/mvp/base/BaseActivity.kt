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
import com.ninos.mvp.R

/**
 * @author Ninos
 *
 * 基础Activity，对重复性代码封装，开放常用方法
 * 为了方便开发，Activity和Fragment开放的方法名和参数尽量做到保持一致
 *
 * @param P 泛型类型，是BasePresenter子类
 */
abstract class BaseActivity<P : BasePresenter<*>> : AppCompatActivity(), BaseContract {
    private lateinit var context: Context
    lateinit var presenter: P
    private lateinit var dialog: AlertDialog
    lateinit var view: View

    /**
     * 基础Activity初始化
     *
     * 开放方法的加载顺序
     * beforeProvideLayoutId -> provideLayoutId -> createPresenter -> initThings -> initListeners -> initData
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        beforeProvideLayoutId()
        setContentView(provideLayoutId())
        view = window.decorView.rootView
        presenter = createPresenter()
        presenter.attachView(this)
        initThings()
        initListeners()
        initData()
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
    open fun beforeProvideLayoutId() {}

    /**
     * 初始化走完之后需要设置的数据
     */
    open fun initData() {}

    /**
     * 设置事件监听
     */
    abstract fun initListeners()

    /**
     * 初始化控件
     */
    protected abstract fun initThings()

    /**
     * 绑定Presenter
     *
     * @return P 返回Presenter对象
     */
    abstract fun createPresenter(): P

    /**
     * 简化Toast show方法
     *
     * @param text 字符串类型消息文本
     */
    override fun showToast(text: CharSequence) =
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

    /**
     * 简化Snackbar show方法
     *
     * @param text 字符串类型消息文本
     */
    override fun showSnackbar(text: CharSequence) =
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()

    /**
     * 简化startActivity方法
     *
     * @param cls 要打开的Activity
     */
    override fun startActivity(cls: Class<*>) = startActivity(Intent(context, cls))


    /**
     * 简化startActivityForResult方法
     *
     * @param cls 要打开的Activity
     * @param requestCode 请求code
     */
    override fun startActivityForResult(cls: Class<*>, requestCode: Int) =
        startActivityForResult(Intent(context, cls), requestCode)

    /**
     * 显示软键盘
     *
     * @param view 显示软键盘后需要获取焦点的view
     */
    override fun showSoftInput(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    /**
     * 隐藏软键盘
     */
    override fun hideSoftMethod() {
        var foucusView = currentFocus
        if (foucusView != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as (InputMethodManager)
            imm.hideSoftInputFromWindow(foucusView.windowToken, 0)
        }
    }

    /**
     * 简化常用dialog方法，具体看参数
     * 用于loading弹框
     *
     * @param resId 资源id，loading布局文件
     * @param cancelable 点击dialog以外是否可以关闭dialog
     */
    override fun showDialog(resId: Int, cancelable: Boolean) {
        if (isShowDialog())
            dialog.dismiss()
        val builder = AlertDialog.Builder(context, R.style.AlertDialog)
        builder.setView(resId)
        builder.setCancelable(cancelable)
        dialog = builder.create()
        dialog.show()
    }

    /**
     * 简化常用dialog方法，具体看参数
     * 单按钮dialog
     *
     * @param title dialog标题
     * @param message dialog提示内容文本
     * @param cancelable 点击dialog以外是否可以关闭dialog
     * @param btn 单按钮文本
     * @param handler 按钮的点击回调，回传dialog对象
     */
    override fun showDialog(
        title: String,
        message: String,
        cancelable: Boolean,
        btn: String,
        handler: (dialog: AlertDialog) -> Unit
    ) {
        if (isShowDialog())
            dialog.dismiss()
        val builder = AlertDialog.Builder(context, R.style.AlertDialog)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(btn) { it, _ -> handler((it as AlertDialog)) }
        dialog = builder.create()
        dialog.show()
    }

    /**
     * 简化常用dialog方法，具体看参数
     *
     * @param title dialog标题
     * @param contentId dialog布局文件id
     * @param cancelable 点击dialog以外是否可以关闭dialog
     * @param confirmStr confirm文本，例：确认  同意  好  前往  是
     * @param cancelStr cancel标题，例：取消  拒绝  否  关闭
     * @param confirm confirm的点击回调，回传dialog对象
     * @param cancel cancel的点击回调
     */
    override fun showDialog(
        title: String,
        contentId: Int,
        cancelable: Boolean,
        confirmStr: String,
        cancelStr: String,
        confirm: (dialog: AlertDialog) -> Unit,
        cancel: () -> Unit
    ) {
        if (isShowDialog())
            dialog.dismiss()
        val builder = AlertDialog.Builder(context, R.style.AlertDialog)
        builder.setTitle(title)
        builder.setView(contentId)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(confirmStr) { it, _ -> confirm((it as AlertDialog)) }
        builder.setNegativeButton(cancelStr) { _, _ -> cancel() }
        dialog = builder.create()
        dialog.show()
    }

    /**
     * 简化常用dialog方法，具体看参数
     *
     * @param title dialog标题
     * @param message dialog提示内容文本
     * @param cancelable 点击dialog以外是否可以关闭dialog
     * @param confirmStr confirm文本，例：确认  同意  好  前往  是
     * @param cancelStr cancel标题，例：取消  拒绝  否  关闭
     * @param confirm confirm的点击回调，回传dialog对象
     * @param cancel cancel的点击回调
     */
    override fun showDialog(
        title: String,
        message: String,
        cancelable: Boolean,
        confirmStr: String,
        cancelStr: String,
        confirm: (dialog: AlertDialog) -> Unit,
        cancel: () -> Unit
    ) {
        if (isShowDialog())
            dialog.dismiss()
        val builder = AlertDialog.Builder(context, R.style.AlertDialog)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(cancelable)
        builder.setPositiveButton(confirmStr) { it, _ -> confirm((it as AlertDialog)) }
        builder.setNegativeButton(cancelStr) { _, _ -> cancel() }
        dialog = builder.create()
        dialog.show()
    }

    /**
     * 判断dialog是否显示
     *
     * @return 布尔类型，true为显示，false为未显示
     */
    override fun isShowDialog(): Boolean = if (::dialog.isInitialized) dialog.isShowing else false

    /**
     * 关闭dialog
     */
    override fun dismissDialog() {
        if (isShowDialog())
            dialog.dismiss()
    }

    /**
     * 结束Activity方法
     */
    override fun finishActivity() = onBackPressed()
}