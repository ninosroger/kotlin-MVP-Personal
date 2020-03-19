package com.ninos.mvp.base

import com.ninos.mvp.repository.component.RepositoryComponent

/**
 * Created by ninos on 2019/1/8.
 */
abstract class BasePresenter<V : BaseView> {
    protected lateinit var view: V
    protected lateinit var repository: RepositoryComponent

    /**
     *初始化Repository
     */
    fun attachView(view: BaseView) {
        this.view = view as V
        repository = RepositoryComponent
    }
}
