package com.nordkaz.ds24test.api

import com.nordkaz.ds24test.model.Detail
import com.google.gson.annotations.SerializedName

data class DetailResponse(
        @SerializedName("data") val items: List<Detail> = emptyList()
)
