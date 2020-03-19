package com.ninos.mvp.presenter

import com.ninos.mvp.base.PaginationPresenter
import com.ninos.mvp.contract.MainContract
import com.ninos.mvp.repository.component.RepositoryComponent
import com.ninos.mvp.repository.localdb.entity.MainEntity

class MainPresenter : PaginationPresenter<MainContract.View>() {
    override fun getData(page: Int, count: Int) {
        repository.localDB.mainDao().insertMain(MainEntity("this is a test message data $page"))
        view.hasMore()
    }

    fun getMainList(): List<MainEntity> {
        return repository.localDB.mainDao().getMainList()
    }
}