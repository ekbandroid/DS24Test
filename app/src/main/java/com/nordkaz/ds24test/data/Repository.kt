package com.nordkaz.ds24test.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.nordkaz.ds24test.api.QuotesService
import com.nordkaz.ds24test.api.DetailService
import com.nordkaz.ds24test.api.getDetails
import com.nordkaz.ds24test.api.searchQuotes
import com.nordkaz.ds24test.db.LocalCache
import com.nordkaz.ds24test.model.Detail
import com.nordkaz.ds24test.model.DetailResult
import com.nordkaz.ds24test.model.QuoteSearchResult
import java.util.concurrent.Executors


class QuoteRepository(
        private val service: QuotesService,
        private val serviceDetail: DetailService,
        private val cache: LocalCache
) {
    val NETWORK_PAGE_SIZE = 10

    val QUOTES_REQUEST_DELAY_MS:Long = 200

    var quotesOver = false

    private var lastItemCount = 1

    private val networkErrors = MutableLiveData<String>()

    private var isRequestInProgress = false

    fun searchQuotes(): QuoteSearchResult {
        Log.d("QuoteSearchResult", "New query")
        lastItemCount = 1
        requestAndSaveData()
        val data = cache.getQuotes()
        return QuoteSearchResult(data, networkErrors)
    }

    fun searchDetail(id: Int): DetailResult {
        Log.d("DetailResult", "New query")
        requestDetails(id)
        val data = cache.getDetail(id)
        return DetailResult(data, networkErrors)
    }

    fun requestMore(lastItemCount:Int) {
        this.lastItemCount = lastItemCount
        requestAndSaveData()
    }

    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        searchQuotes(service, lastItemCount, NETWORK_PAGE_SIZE, { quotes ->
            if (quotes.size < NETWORK_PAGE_SIZE)
                quotesOver = true
            cache.insertQuoteList(quotes, {
                Executors.newSingleThreadExecutor().run {
                    Thread.sleep(QUOTES_REQUEST_DELAY_MS)
                    isRequestInProgress = false
                }
            })
        }, { error ->
            networkErrors.postValue(error)
            Executors.newSingleThreadExecutor().run {
                Thread.sleep(QUOTES_REQUEST_DELAY_MS)
                isRequestInProgress = false
            }
        })
    }

    private val liveData = MutableLiveData<List<Detail>>()
    fun getLiveData():LiveData<List<Detail>> {
        return liveData
    }

    fun requestDetails(id:Int) {
        isRequestInProgress = true
        getDetails(serviceDetail, id, { data ->
            cache.insertDetail(data.get(0), {
                    isRequestInProgress = false
                })
        }, { error ->
            networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }

}