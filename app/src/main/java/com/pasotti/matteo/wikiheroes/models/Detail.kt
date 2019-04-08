package com.pasotti.matteo.wikiheroes.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.pasotti.matteo.wikiheroes.room.RoomConverters
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Entity(primaryKeys = ["id"])
@Parcelize
data class Detail( val id: Int,
                   val digitalId: String?,
                   val title: String?,
                   val issueNumber: String?,
                   val variantDescription: String?,
                   val description: String?,
                   val modified: String?,
                   val isbn: String?,
                   val upc: String?,
                   val diamondCode: String?,
                   val ean: String?,
                   val issn: String?,
                   val format: String?,
                   val pageCount: Int,
                   @Embedded(prefix = "series_")
                   val series: Item?,
                   val dates: MutableList<Date>?,
                   val prices: MutableList<Price>?,
                   val creators: CollectionItem?,
                   val characters: CollectionItem?,
                   val resourceURI: String,
                   val thumbnail: Thumbnail?,
                   val images: MutableList<Thumbnail>?,
                   val urls: MutableList<ItemUrl>?,
                   var page : Int) : Parcelable