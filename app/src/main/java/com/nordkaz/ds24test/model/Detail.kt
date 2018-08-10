package com.nordkaz.ds24test.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.nordkaz.ds24test.db.MyTypeConverter


@Entity(tableName = "details")
@TypeConverters(MyTypeConverter::class)
data class Detail (
        @PrimaryKey @field:SerializedName("id") val id: Long,
        @field:SerializedName("createdAt") val createdAt: String,
        @field:SerializedName("text") val text: String,
        @field:SerializedName("tagList") val tagList: List<MyTag>
)