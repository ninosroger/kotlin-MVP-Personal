package com.ninos.mvp.repository.localdb.dao

import androidx.room.*
import com.ninos.mvp.repository.localdb.entity.MainEntity

/**
 * @author Ninos
 */
@Dao
interface MainDao {
    @Insert
    fun insertMain(vararg data: MainEntity)

    @Update
    fun updateMain(vararg data: MainEntity)

    @Delete
    fun deleteMain(vararg data: MainEntity)

    @Query("select * from demo_table")
    fun getMainList(): List<MainEntity>
}