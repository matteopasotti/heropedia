package com.pasotti.matteo.wikiheroes.repository

import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.room.CharacterDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepository @Inject
constructor(val characterDao: CharacterDao, val marvelApi: MarvelApi){
}
