package com.pasotti.matteo.wikiheroes.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Detail(val id: Int, val digitalId: String,
                  val title: String,
                  val issueNumber: String,
                  val variantDescription: String,
                  val description: String,
                  val isbn: String,
                  val fromat: String,
                  val resourceURI: String,
                  val thumbnail: Thumbnail,
                  val images: MutableList<Thumbnail>,
                  val urls: MutableList<ItemUrl>) : Serializable, Parcelable