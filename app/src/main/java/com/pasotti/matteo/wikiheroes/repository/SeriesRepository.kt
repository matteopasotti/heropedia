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
class SeriesRepository @Inject
constructor(private val marvelApi: MarvelApi) {

    private val defaultLimit = 20

    private val timestamp = Date().time

    private val hash = Utils.md5(timestamp.toString() + Utils.MARVEL_PRIVATE_KEY + Utils.MARVEL_PUBLIC_KEY)

    fun getSeriesBySeriesId( id : String) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesBySeriesId(id, Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString())
    }

    fun getSeriesByCreatorId( id : String, offset : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesByCreatorId(id , Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "-startYear", offset, defaultLimit)
    }

    fun getSeriesByCharacterId(id : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesByCharacterId(id.toString(), Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString())
    }

    fun getSeriesByCharacterId(id : Int, offset : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesByCharacterId(id.toString(), Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(),offset, defaultLimit)
    }
}