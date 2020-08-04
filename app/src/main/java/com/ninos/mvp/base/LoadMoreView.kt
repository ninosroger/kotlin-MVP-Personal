package com.ninos.mvp.base

/**
 * @author Ninos
 *
 * 为有上拉加载的页面配置的接口
 */
interface LoadMoreView : BaseView {
    /**
     * 没有下一页数据，加载完毕
     */
    fun noMore()

    /**
     * 有下一页数据，加载下一页
     */
    fun hasMore()
}