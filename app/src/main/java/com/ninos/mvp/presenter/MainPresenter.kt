package com.ninos.mvp.presenter

import com.ninos.mvp.base.PaginationPresenter
import com.ninos.mvp.contract.MainContract

/**
 * @author Ninos
 */
class MainPresenter : PaginationPresenter<MainContract>() {
    override fun getData(page: Int, count: Int) {
    }
}