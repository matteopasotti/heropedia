package com.pasotti.matteo.wikiheroes.room

import androidx.room.TypeConverter
import java.util.*
import com.google.gson.reflect.TypeToken
import java.util.Collections.emptyList
import com.google.gson.Gson
import com.pasotti.matteo.wikiheroes.models.*
import java.util.Date


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
    fun fromStringtoDate( data: String?) : MutableList<com.pasotti.matteo.wikiheroes.models.Date>? {
        if( data == null ) {
            return null
        }

        val obj = object : TypeToken<List<com.pasotti.matteo.wikiheroes.models.Date>>(){}.type

        return gson.fromJson(data, obj)
    }

    @TypeConverter
    fun fromListDateToString( someObjects : MutableList<com.pasotti.matteo.wikiheroes.models.Date>) : String {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToThumbnail( data: String?) : Thumbnail? {
        if( data == null ) {
            return null
        }

        val obj = object : TypeToken<Thumbnail>(){}.type

        return gson.fromJson(data, obj)
    }

    @TypeConverter
    fun thumbnailToString( someObject: Thumbnail) : String {
        return gson.toJson(someObject)
    }

    @TypeConverter
    fun stringToCollectionItem( data: String? ) : CollectionItem? {

        if( data == null ) {
            return null
        }

        val obj = object : TypeToken<CollectionItem>(){}.type

        return gson.fromJson(data, obj)
    }

    @TypeConverter
    fun collectionItemToString( someObject : CollectionItem) : String {
        return gson.toJson(someObject)
    }

    @TypeConverter
    fun stringToListThumbnail( data: String?) : MutableList<Thumbnail> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<MutableList<Thumbnail>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun thumbnailListToString( someObjects: MutableList<Thumbnail>) : String {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToListPrice( data: String?) : MutableList<Price> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<MutableList<Price>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun priceListToString( someObjects: MutableList<Price>) : String {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToListDate( data : String?) : List<Date> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<Date>>() {

        }.type

        return gson.fromJson(data, listType)

    }

    @TypeConverter
    fun dateListToString( someObjects: List<Date>) : String {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToItemUrlList(data: String?): MutableList<ItemUrl> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<MutableList<ItemUrl>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun itemUrlListToString(someObjects: MutableList<ItemUrl>): String {
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