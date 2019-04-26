package com.pasotti.matteo.wikiheroes.models

import androidx.room.Embedded
import androidx.room.Entity


@Entity(primaryKeys = ["deskItemId"])
data class DeskItem(val deskItemId: Int,
                    @Embedded(prefix = "item_")
                    val item: Detail)