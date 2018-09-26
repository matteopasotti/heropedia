package com.pasotti.matteo.wikiheroes.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ItemUrl(val type: String, val url: String) : Parcelable