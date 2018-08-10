package com.nordkaz.ds24test.model

import android.arch.lifecycle.LiveData

data class DetailResult(
        val data: LiveData<Detail>,
        val networkErrors: LiveData<String>
)