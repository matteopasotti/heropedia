package com.pasotti.matteo.wikiheroes.repository

import androidx.lifecycle.LiveData
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.utils.PreferenceManager
import com.pasotti.matteo.wikiheroes.utils.Utils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreatorsRepository @Inject
constructor(private val marvelApi: MarvelApi, val preferenceManager: PreferenceManager) {

    private val defaultLimit = 10

    var offset = 0

    private val timestamp = Date().time

    private val hash = Utils.md5(timestamp.toString() + Utils.MARVEL_PRIVATE_KEY + Utils.MARVEL_PUBLIC_KEY)

    fun searchCreatorByName( nameStartWith : String) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.searchCreatorByName(nameStartWith, Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString() , "firstName" , offset, defaultLimit)
    }


    fun getCreatorDetailById( id : String) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getCreatorDetailById(id , Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString())
    }
}