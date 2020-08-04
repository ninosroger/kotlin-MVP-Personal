package com.ninos.mvp.page.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ninos.mvp.R
import com.ninos.mvp.base.SwipeRefreshActivity
import com.ninos.mvp.page.adapter.MainAdapter
import com.ninos.mvp.presenter.MainPresenter

/**
 * @author Ninos
 */
class MainActivity : SwipeRefreshActivity<MainPresenter, MainAdapter, String>() {
    override fun provideAdapter(): MainAdapter = MainAdapter(this)

    override fun provideLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(this)

    override fun setAutoLoadMore(): Boolean = false

    override fun onItemClick(view: View, position: Int, item: String) {
        val list = presenter.getMainList()
        val temp = ArrayList<String>()
        list.forEach {
            temp.add(it.appLog)
        }
        bindData(temp)
        showSnackbar("")
    }

    override fun provideTitle(): CharSequence = "MVP for AndroidX"

    override fun provideLayoutId(): Int = R.layout.activity_swipe_refresh

    override fun initListeners() {}

    override fun createPresenter(): MainPresenter = MainPresenter()

    override fun pulldownRefresh() {
        super.pulldownRefresh()
        val list = ArrayList<String>()
        list.add("Android O")
        list.add("Android P")
        list.add("Android E")
        list.add("Android F")
        list.add("Android G")
        list.add("Android I")
        list.add("Android L")
        list.add("Android S")
        bindData(list)
        refreshLayout.finishRefresh()
    }
}
