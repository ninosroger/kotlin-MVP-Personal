package com.ninos.mvp.base

/**
 * @author Ninos
 *
 * 用于列表数据请求的Presenter，是BasePresenter的子类
 */
abstract class PaginationPresenter<V : LoadMoreContract> : BasePresenter<V>() {

    /**
     * 获取分页数据，用于分页请求
     *
     * @param page 当前页数
     * @param count 每页显示的数据条数
     */
    abstract fun getData(page: Int, count: Int)

    /**
     * 使用加载下一页数据，用于分页接口之后
     * 该方法根据具体数据结构及服务器返回参数进行改动
     *
     * size为接口返回数据条数，count为规定接口每页返回的数据条数
     * 当size等于count的时候，代表还有可能有下一页数据
     * 当size小于count的时候，代表没有下一页数据
     *
     * @param size getData返回的分页数据
     * @param count 每页数据条数，同getData里的count
     *
     * @return boolean类型，当true时加载下一页，为false时加载完毕
     */
    fun hasMoreData(size: Int, count: Int) {
        if (size == count)
            contract.hasMore()
        else
            contract.noMore()
    }
}