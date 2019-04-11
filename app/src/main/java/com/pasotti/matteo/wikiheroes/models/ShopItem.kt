package com.pasotti.matteo.wikiheroes.models

import androidx.room.Embedded
import androidx.room.Entity


@Entity(primaryKeys = ["shopItemId"])
data class ShopItem(val shopItemId: Int,
                    @Embedded(prefix = "item_")
                    val item: Detail)