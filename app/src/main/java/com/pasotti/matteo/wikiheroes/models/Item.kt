package com.pasotti.matteo.wikiheroes.models

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize


@Entity(primaryKeys = ["resourceURI"])
@Parcelize
data class Item(val resourceURI: String, val name: String, val type: String?) : Parcelable