package com.ninos.mvp.repository.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ninos.mvp.repository.localdb.dao.MainDao
import com.ninos.mvp.repository.localdb.entity.MainEntity

@Database(
    version = 1, entities = [
        MainEntity::class]
)
abstract class LocalDB : RoomDatabase() {
    abstract fun mainDao(): MainDao
}