package com.kou.fisaa.data.local.converters

import androidx.room.TypeConverter
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.kou.fisaa.data.entities.Advertisement
import java.lang.reflect.Type


class ListAdvertisementConverter {

    var gson = Gson()

    @TypeConverter
    fun stringToAdvertisementList(data: String?): List<Advertisement?>? {
        if (data == null) {
            return emptyList<Advertisement>()
        }
        val listType: Type = object : TypeToken<List<Advertisement?>?>() {}.type
        return gson.fromJson<List<Advertisement?>>(data, listType)
    }

    @TypeConverter
    fun advertisementListToString(someObjects: List<Advertisement?>?): String? {
        return gson.toJson(someObjects)
    }
}