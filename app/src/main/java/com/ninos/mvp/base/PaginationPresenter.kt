package com.ninos.mvp.base

import com.ninos.mvp.repository.bean.ProcessData

/**
 * Created by ninos on 2019/1/8.
 */
abstract class PaginationPresenter<V : LoadMoreView> : BasePresenter<V>() {

    /**
     * 获取数据
     * @param page
     * @param count
     */
    abstract fun getData(page: Int, count: Int)

    /**
     * 设置数据状态
     * @param page
     * @param count
     * @param data
     */
    fun isHasMoreData(page: Int, count: Int, data: ProcessData<List<*>>) {
        if (data.content!!.size < count) {
            //没有更多了
            view.noMore()
        } else {
            //还有更多
            view.hasMore()
        }

    }
}