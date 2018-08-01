package com.pasotti.matteo.wikiheroes.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import java.io.Serializable


@Entity(primaryKeys = ["id"])
data class Character(val id: Int, val name: String,
                     val description: String,
                     val modified: String,
                     @Embedded(prefix = "thumbnail_")
                     val thumbnail: Thumbnail,
                     val resourceURI: String,
                     @Embedded(prefix = "comics_")
                     val comics: CollectionItem,
                     @Embedded(prefix = "series_")
                     val series: CollectionItem,
                     @Embedded(prefix = "stories_")
                     val stories: CollectionItem,
                     @Embedded(prefix = "events_")
                     val events: CollectionItem,
                     val urls: MutableList<ItemUrl>,
                     var page : Int) : Serializable