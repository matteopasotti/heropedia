package com.pasotti.matteo.wikiheroes.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Thumbnail(val path: String, val extension: String) : Parcelable