package com.nordkaz.ds24test.model

import android.arch.lifecycle.LiveData

data class QuoteSearchResult(
        val data: LiveData<List<Quote>>,
        val networkErrors: LiveData<String>
)