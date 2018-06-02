package com.pasotti.matteo.wikiheroes.models

import java.io.Serializable

data class Character(val id: Int, val name: String,
                     val description: String,
                     val modified: String,
                     val thumbnail: Thumbnail,
                     val resourceURI: String,
                     val comics: CollectionItem,
                     val series: CollectionItem,
                     val stories: CollectionItem,
                     val events: CollectionItem,
                     val urls: MutableList<ItemUrl>) : Serializable