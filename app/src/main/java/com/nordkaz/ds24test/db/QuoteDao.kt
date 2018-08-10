package com.nordkaz.ds24test.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.nordkaz.ds24test.model.Quote


@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(quotes: List<Quote>)

    @Query("SELECT * FROM quotes")
    fun getQuotes(): LiveData<List<Quote>>

}