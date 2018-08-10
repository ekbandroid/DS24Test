package com.nordkaz.ds24test.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.nordkaz.ds24test.model.Detail
import com.nordkaz.ds24test.model.Quote


@Dao
interface DetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(detail: Detail)

    @Query("SELECT * FROM details WHERE id = :id")
    fun getDetail(id: Int): LiveData<Detail>

}