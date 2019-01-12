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
        return marvelApi.getComicsBySeriesId(id, Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "issueNumber")
    }

    fun getComicsByCreatorId( id : String, offset : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsByCreatorId(id , Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), true, "issueNumber", offset, defaultLimit)
    }

    fun getSeriesByCreatorId( id : String, offset : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesByCreatorId(id , Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "-startYear", offset, defaultLimit)
    }

    fun getEventsByCreatorId( id : String, offset : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getEventsByCreatorId(id , Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "issueNumber", offset, defaultLimit)
    }
}