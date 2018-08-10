package com.nordkaz.ds24test.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quotes")
data class Quote(
        @PrimaryKey @field:SerializedName("id") val id: Long,
        @field:SerializedName("createdBy") val name: Long,
        @field:SerializedName("text") val quoteText: String
)
