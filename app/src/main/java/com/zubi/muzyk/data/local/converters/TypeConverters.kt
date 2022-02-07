package com.zubi.muzyk.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.zubi.muzyk.data.local.entity.Image

class ImageConverters {

    private val gson: Gson = Gson()

    @TypeConverter
    fun jsonToImage(json: String): Image {
        return gson.fromJson(json, Image::class.java)
    }

    @TypeConverter
    fun imageToJson(image: Image): String {
        return gson.toJson(image)
    }
}


class StringListConverters {

    @TypeConverter
    fun stringToList(string: String): List<String> {
        return string.split(",").map { it.trim() }
    }

    @TypeConverter
    fun listToString(list: List<String>): String {
        return list.joinToString { "," }
    }

}
