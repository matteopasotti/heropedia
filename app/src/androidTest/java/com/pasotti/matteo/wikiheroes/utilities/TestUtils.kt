package com.pasotti.matteo.wikiheroes.utilities

import com.pasotti.matteo.wikiheroes.models.CollectionItem
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.models.ItemUrl
import com.pasotti.matteo.wikiheroes.models.Thumbnail

/**
 * objects used for tests.
 */

val items = mutableListOf<Item>()

val thumbnail = Thumbnail("" , "")

val comics = CollectionItem(10, "", items , 10)

val series = CollectionItem(20, "", items , 10)

val stories = CollectionItem(3, "", items , 10)

val events = CollectionItem(3, "", items , 10)

val urls = mutableListOf<ItemUrl>(ItemUrl("", ""))



