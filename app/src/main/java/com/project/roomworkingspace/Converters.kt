package com.project.roomworkingspace

import androidx.room.TypeConverter
import com.google.gson.Gson
import org.json.JSONArray
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun listToJson(value: List<String>) = Gson().toJson(value)

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}