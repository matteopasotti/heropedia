package com.pasotti.matteo.wikiheroes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.CharacterResponse
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.room.CharacterDao
import com.pasotti.matteo.wikiheroes.utils.Utils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepository @Inject
constructor(val characterDao: CharacterDao, val marvelApi: MarvelApi) {

    val defaultLimit = 10

    var offset = 0

    val timestamp = Date().time

    val hash = Utils.md5(timestamp.toString() + Utils.MARVEL_PRIVATE_KEY + Utils.MARVEL_PUBLIC_KEY)

    val data = MutableLiveData<Resource<CharacterResponse>>()

    var originalList = listOf<Character>()


    fun getCharacters(page: Int): LiveData<Resource<List<Character>>> {

        return object : NetworkBoundResource<List<Character>, CharacterResponse>() {

            override fun saveFetchData(item: CharacterResponse) {

                offset += defaultLimit
                val newCharacters = item.data.results

                for (character in newCharacters) {
                    character.page = page
                }

                characterDao.insertCharacters(newCharacters)
            }

            override fun shouldFetch(data: List<Character>?): Boolean {
                if(data != null && data.size > 0) {
                    offset = data.size
                }
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Character>> {
                if(page == 0) {
                    return characterDao.getCharacters()
                } else  {
                    return characterDao.getCharacters(page)
                }

            }

            override fun fetchService(): LiveData<ApiResponse<CharacterResponse>> {
                return marvelApi.getCharacters("-modified", timestamp.toString(), Utils.MARVEL_PUBLIC_KEY, hash, offset, defaultLimit)
            }

            override fun onFetchFailed() {

            }

        }.asLiveData

    }

    fun getComicsByCharacterId(id : Int) : LiveData<ApiResponse<DetailResponse>> {

        return marvelApi.getComicsByCharacterId(id.toString(), Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "-onsaleDate")

    }


    fun getSeriesByCharacterId(id : Int) : LiveData<ApiResponse<DetailResponse>> {

        return marvelApi.getSeriesByCharacterId(id.toString(), Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString())

    }

    fun getStoriesByCharacterId(id : Int) : LiveData<ApiResponse<DetailResponse>> {

        return marvelApi.getStoriesByCharacterId(id.toString(), Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString())

    }
}