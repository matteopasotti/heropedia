package com.pasotti.matteo.wikiheroes.repository

import androidx.lifecycle.LiveData
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.utils.Utils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicsRepository @Inject
constructor(private val marvelApi: MarvelApi) {

    private val defaultLimit = 20

    private val timestamp = Date().time

    private val hash = Utils.md5(timestamp.toString() + Utils.MARVEL_PRIVATE_KEY + Utils.MARVEL_PUBLIC_KEY)

    fun getComicsBySeriesId(id : String) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsBySeriesId(id, Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "-issueNumber")
    }

    fun getComicsBySeriesId(id : String, offset : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsBySeriesId(id, Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "-issueNumber", offset, defaultLimit)
    }

    fun getComicsByCreatorId( id : String, offset : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsByCreatorId(id , Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), true, "-onsaleDate", offset, defaultLimit)
    }

    fun getComicsByCharacterId(id : Int, offset : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsByCharacterId(id.toString(), Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "-onsaleDate", offset, defaultLimit)
    }

    fun getEventsByCreatorId( id : String, offset : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getEventsByCreatorId(id , Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "issueNumber", offset, defaultLimit)
    }

    fun getComicsByCharacterId(id : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsByCharacterId(id.toString(), Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "-onsaleDate")
    }

    fun getComicsOfTheWeek( offset: Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsOfTheWeek("thisWeek" , "-onsaleDate" , timestamp.toString() ,Utils.MARVEL_PUBLIC_KEY ,  hash,  offset, defaultLimit )
    }

    fun getOldestComicsByCharacterId(id : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsByCharacterId(id.toString(), Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "onsaleDate")
    }
}