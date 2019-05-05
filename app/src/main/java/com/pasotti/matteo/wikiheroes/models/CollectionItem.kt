package com.pasotti.matteo.wikiheroes.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionItem(val available: Int, val collectionURI: String?,
                          val items: MutableList<Item>?, val returned: Int) :  Parcelable