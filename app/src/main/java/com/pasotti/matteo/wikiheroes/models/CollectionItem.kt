package com.pasotti.matteo.wikiheroes.models

data class CollectionItem(val available: Int, val collectionURI: String,
                          val items: MutableList<Item>, val returned: Int)