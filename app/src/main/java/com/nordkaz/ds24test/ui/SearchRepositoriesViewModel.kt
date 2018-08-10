package com.nordkaz.ds24test.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.nordkaz.ds24test.data.QuoteRepository
import com.nordkaz.ds24test.model.Detail
import com.nordkaz.ds24test.model.DetailResult
import com.nordkaz.ds24test.model.Quote
import com.nordkaz.ds24test.model.QuoteSearchResult

class SearchRepositoriesViewModel(val repository: QuoteRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    private val detailQueryLiveData = MutableLiveData<Int>()
    private val mQuoteResult: LiveData<QuoteSearchResult> = Transformations.map(queryLiveData, {
        repository.searchQuotes()
    })
    private val mDetailResult: LiveData<DetailResult> = Transformations.map(detailQueryLiveData, {
        repository.searchDetail(it)
    })

    val quotes: LiveData<List<Quote>> = Transformations.switchMap(mQuoteResult,
            { it -> it.data })
    val networkErrors: LiveData<String> = Transformations.switchMap(mQuoteResult,
            { it -> it.networkErrors })

    val details: LiveData<Detail> = Transformations.switchMap(mDetailResult,
            { it -> it.data })
    val networkErrorsDetails: LiveData<String> = Transformations.switchMap(mDetailResult,
            { it -> it.networkErrors })

    fun searchQuotes() {
        queryLiveData.postValue("")
    }

    fun searchDetails(id: Int) {
        detailQueryLiveData.postValue(id)
    }

    private var itemCount = repository.NETWORK_PAGE_SIZE
    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (itemCount > (totalItemCount))
            itemCount = totalItemCount
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            if ((immutableQuery != null)&&(!repository.quotesOver)) {
                repository.requestMore(itemCount + 1)
                itemCount += repository.NETWORK_PAGE_SIZE
            }
        }
    }

    fun lastQueryValue(): String? = queryLiveData.value
}