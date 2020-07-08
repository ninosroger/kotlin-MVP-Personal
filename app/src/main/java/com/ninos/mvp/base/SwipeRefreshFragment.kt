package com.ninos.mvp.base

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.ninos.mvp.R
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * Created by ninos on 2019/1/8.
 */
abstract class SwipeRefreshFragment<P : PaginationPresenter<*>, A : BaseAdapter<*, B, P>, B> :
    ToolBarFragment<P>(),
    BaseAdapter.OnItemClickListener<B>, LoadMoreView {
    lateinit var recyclerView: RecyclerView
    lateinit var refreshLayout: RefreshLayout
    lateinit var adapter: A
    lateinit var layoutManager: RecyclerView.LayoutManager
    var page = 1
    var count = 10


    override fun initThings(view: View) {
        super.initThings(view)
        recyclerView = view.findViewById(R.id.recycler_view)
        refreshLayout = view.findViewById<SmartRefreshLayout>(R.id.refresh_layout)
        this.layoutManager = provideLayoutManager()
        this.adapter = provideAdapter()
        refreshLayout.setEnableAutoLoadMore(setAutoLoadMore())
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        refreshLayout.setOnRefreshListener {
            //下拉刷新
            pulldownRefresh()
        }
        refreshLayout.setOnLoadMoreListener {
            //上拉加载
            presenter.getData(page, count)
        }
        adapter.setOnItemClickListener(this)
    }

    override fun noMore() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    override fun hasMore() {
        refreshLayout.finishLoadMore()
        page++
    }

    /**
     * 下拉刷新操作
     */
    open fun pulldownRefresh() {
    }

    /**
     * @return 提供Adapter
     */
    protected abstract fun provideAdapter(): A

    /**
     * @return 提供LayoutManager
     */
    protected abstract fun provideLayoutManager(): RecyclerView.LayoutManager

    /**
     * @return 是否自动加载数据
     */
    protected abstract fun setAutoLoadMore(): Boolean

    /**
     * 基本的绑定数据
     * @param data
     */
    fun bindData(data: ArrayList<B>) =
        if (page == 1)
            adapter.addDatas(data)
        else
            adapter.addMore(data)
}