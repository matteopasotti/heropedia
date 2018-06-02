package com.pasotti.matteo.wikiheroes.models

data class CharacterData(val offset: Int, val limit: Int, val total: Int, val count: Int,
                         var results: MutableList<Character>)