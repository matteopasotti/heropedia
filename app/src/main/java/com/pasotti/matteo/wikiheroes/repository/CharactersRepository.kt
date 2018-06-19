package com.pasotti.matteo.wikiheroes.repository

import android.arch.lifecycle.LiveData
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.models.CharacterResponse
import com.pasotti.matteo.wikiheroes.room.CharacterDao
import com.pasotti.matteo.wikiheroes.utils.Utils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepository @Inject
constructor(val characterDao: CharacterDao, val marvelApi: MarvelApi){

    val defaultLimit = 20

    val timestamp = Date().time;

    val hash = Utils.md5(timestamp.toString()+Utils.MARVEL_PRIVATE_KEY+Utils.MARVEL_PUBLIC_KEY)

    fun getCharacters() : LiveData<Resource<CharacterResponse>> {
        return marvelApi.getCharacters(timestamp.toString(), Utils.MARVEL_PUBLIC_KEY, hash, defaultLimit)
    }
}
