package com.ninos.mvp.base

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.ninos.mvp.R
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * @author Ninos
 *
 * 下拉上拉刷新类
 * 刷新控件使用的是SmartRefreshLayout
 * github地址：https://github.com/scwang90/SmartRefreshLayout
 * 框架只引入基础下拉上拉刷新控件，需要引入其他样式，前往github复制依赖引入
 *
 * @param P Presenter类
 * @param E POJO实体类
 * @param A Adapter类
 */
abstract class SwipeRefreshFragment<P : PaginationPresenter<*>, A : BaseAdapter<*, E, P>, E> :
    ToolBarFragment<P>(),
    BaseAdapter.OnItemClickListener<E>, LoadMoreContract {
    /**
     * 列表控件
     */
    lateinit var recyclerView: RecyclerView

    /**
     * 刷新控件
     */
    lateinit var refreshLayout: RefreshLayout

    /**
     * Adapter对象
     */
    lateinit var adapter: A

    /**
     * 列表布局配置对象
     */
    lateinit var layoutManager: RecyclerView.LayoutManager

    /**
     * 分页页数
     */
    var page = 1

    /**
     * 分页数据数
     */
    var count = 10

    /**
     * 初始化recyclerView，refreshLayout，adapter
     */
    override fun initThings() {
        super.initThings()
        recyclerView = findViewById(R.id.recycler_view)
        refreshLayout = findViewById<SmartRefreshLayout>(R.id.refresh_layout)
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

    /**
     * 加载完毕
     */
    override fun noMore() {
        refreshLayout.finishLoadMoreWithNoMoreData()
    }

    /**
     * 加载更多
     */
    override fun hasMore() {
        refreshLayout.finishLoadMore()
        page++
    }

    /**
     * 下拉刷新操作
     * 默认从第一页开始重新加载数据
     */
    open fun pulldownRefresh() {
        page = 1
        presenter.getData(page, count)
    }

    /**
     * 绑定adapter
     *
     * @return 提供Adapter对象
     */
    protected abstract fun provideAdapter(): A

    /**
     * 为recyclerView添加layoutManager
     *
     * @return 提供LayoutManager对象
     */
    protected abstract fun provideLayoutManager(): RecyclerView.LayoutManager

    /**
     * 设置刷新控件refreshLayout是否会自动加载更多
     *
     * @return 是否自动加载状态，true自动加载，false不自动
     */
    protected abstract fun setAutoLoadMore(): Boolean

    /**
     * 绑定列表数据
     * @param data 接口返回分页数据
     */
    fun bindData(data: ArrayList<E>) =
        /**
         * 当为第一页时，不考虑是否存在旧数据，填充数据列表
         * 不为第一页时，数据列表添加新数据
         */
        if (page == 1)
            adapter.addDatas(data)
        else
            adapter.addMore(data)
}