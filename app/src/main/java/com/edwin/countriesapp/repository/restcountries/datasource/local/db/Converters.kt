package com.edwin.countriesapp.repository.restcountries.datasource.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<Double?>? {
        val listType: Type = object : TypeToken<List<Double?>?>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Double?>?): String? {
        return gson.toJson(list)
    }

}