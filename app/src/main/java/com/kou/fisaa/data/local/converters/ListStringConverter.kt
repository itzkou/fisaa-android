package com.kou.fisaa.data.local.converters

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson


class ListStringConverter {

    var gson = Gson()

    @TypeConverter
    fun restoreList(listOfString: String?): List<String?>? {
        return gson.fromJson(listOfString, object : TypeToken<List<String?>?>() {}.type)
    }

    @TypeConverter
    fun saveList(listOfString: List<String?>?): String? {
        return gson.toJson(listOfString)
    }
}