package com.ninos.mvp.presenter

import com.ninos.mvp.base.PaginationPresenter
import com.ninos.mvp.repository.component.RepositoryComponent
import com.ninos.mvp.repository.localdb.entity.MainEntity

/**
 * @author Ninos
 */
class MainPresenter : PaginationPresenter() {
    override fun getData(page: Int, count: Int) {
        RepositoryComponent.localDB.mainDao()
            .insertMain(MainEntity("this is a test message data $page"))
    }

    fun getMainList(): List<MainEntity> {
        return RepositoryComponent.localDB.mainDao().getMainList()
    }
}