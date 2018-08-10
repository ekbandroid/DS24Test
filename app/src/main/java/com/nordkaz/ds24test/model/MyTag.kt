package com.nordkaz.ds24test.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MyTag (
    @field:SerializedName("label") val label: String,
    @field:SerializedName("color") val color: String
)