package com.pasotti.matteo.wikiheroes.models

abstract class SearchObjectItem {

    companion object {
        const val TYPE_CHARACTER = 0
        const val TYPE_COMIC = 1
        const val TYPE_SERIES = 2
        const val TYPE_PERSON = 3
    }

    abstract fun getType(): Int
}