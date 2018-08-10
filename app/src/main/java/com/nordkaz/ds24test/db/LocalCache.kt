package com.nordkaz.ds24test.db

import android.arch.lifecycle.LiveData
import android.util.Log
import com.nordkaz.ds24test.model.Detail
import com.nordkaz.ds24test.model.Quote
import java.util.concurrent.Executor


class LocalCache(
        private val quoteDao: QuoteDao,
        private val detailDao: DetailDao,
        private val ioExecutor: Executor
) {

    fun insertQuoteList(quotes: List<Quote>, insertFinished: ()-> Unit) {
        ioExecutor.execute {
            Log.d("LocalCache", "inserting ${quotes.size} quotes")
            quoteDao.insert(quotes)
            insertFinished()
        }
    }

    fun getQuotes(): LiveData<List<Quote>> {
        return quoteDao.getQuotes()
    }

    fun insertDetail(detail: Detail, insertFinished: ()-> Unit) {
        ioExecutor.execute {
            Log.d("LocalCache", "inserting detail")
            detailDao.insert(detail)
            insertFinished()
        }
    }

    fun getDetail(id: Int): LiveData<Detail> {
        return detailDao.getDetail(id)
    }
}