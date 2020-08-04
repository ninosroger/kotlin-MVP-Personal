package com.ninos.mvp.repository.component

import androidx.room.Room
import com.ninos.mvp.repository.api.ApiService
import com.ninos.mvp.repository.localdb.LocalDB
import com.ninos.mvp.repository.okhttp.OkHttpService
import com.ninos.mvp.Application

/**
 * @author Ninos
 */
object RepositoryComponent {
    /**
     *获取ApiService
     */
    val api: ApiService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpService.getService()
    }

    /**
     * ### 获取本地数据库
     */
    val localDB: LocalDB by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        Room.databaseBuilder(Application.instance, LocalDB::class.java, "AndroidX.db")
            .allowMainThreadQueries().build()
    }
}
