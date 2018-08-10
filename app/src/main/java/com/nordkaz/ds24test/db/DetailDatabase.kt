package com.nordkaz.ds24test.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.nordkaz.ds24test.model.Detail

@Database(
        entities = [Detail::class],
        version = 1,
        exportSchema = false
)
abstract class DetailDatabase : RoomDatabase() {

    abstract fun detailDao(): DetailDao

    companion object {

        @Volatile
        private var INSTANCE: DetailDatabase? = null

        fun getInstance(context: Context): DetailDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        DetailDatabase::class.java, "Detail.db")
                        .build()
    }
}