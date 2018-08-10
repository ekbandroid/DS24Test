package com.nordkaz.ds24test.db

import android.arch.persistence.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import com.nordkaz.ds24test.model.MyTag
import java.util.*


class MyTypeConverter {

    var gson = Gson()
    @TypeConverter
    fun stringToMyTagList(data: String?): List<MyTag> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<MyTag>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun MyTagListToString(objects: List<MyTag>): String {
        return gson.toJson(objects)
    }

}