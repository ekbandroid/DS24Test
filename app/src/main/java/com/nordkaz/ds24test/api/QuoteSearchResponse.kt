package com.nordkaz.ds24test.api


import com.nordkaz.ds24test.model.Quote
import com.google.gson.annotations.SerializedName


data class QuoteSearchResponse(
        @SerializedName("data") val items: List<Quote> = emptyList(),
        val nextPage: Int? = null
)
