package com.pasotti.matteo.wikiheroes.room

import androidx.room.TypeConverter
import java.util.*
import com.pasotti.matteo.wikiheroes.models.ItemUrl
import com.google.gson.reflect.TypeToken
import java.util.Collections.emptyList
import com.google.gson.Gson
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.models.Price


class RoomConverters {

    var gson = Gson()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {

        return when (value) {
            null -> null
            else -> Date(value)
        }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {

        return when (date) {
            null -> null
            else -> date.time
        }
    }

    @TypeConverter
    fun stringToItemUrlList(data: String?): List<ItemUrl> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<ItemUrl>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun itemUrlListToString(someObjects: List<ItemUrl>): String {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToItemList(data: String?): List<Item> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Item>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun itemListToString(items: List<Item>): String {
        return gson.toJson(items)
    }
}