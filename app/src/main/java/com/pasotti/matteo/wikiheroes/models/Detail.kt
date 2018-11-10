package com.pasotti.matteo.wikiheroes.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

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
                   val fromat: String?,
                   val pageCount: Int,
                   val series: Item?,
                   val dates: MutableList<Date>?,
                   val prices: MutableList<Price>?,
                   val creators: CollectionItem?,
                   val characters: CollectionItem?,
                   val resourceURI: String?,
                   val thumbnail: Thumbnail?,
                   val images: MutableList<Thumbnail>?,
                   val urls: MutableList<ItemUrl>?) : Parcelable