package com.pasotti.matteo.wikiheroes.repository

import androidx.lifecycle.LiveData
import com.pasotti.matteo.wikiheroes.BuildConfig
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.models.DeskItem
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.room.ShopDao
import com.pasotti.matteo.wikiheroes.utils.Utils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SeriesRepository @Inject
constructor(private val marvelApi: MarvelApi, private val shopDao: ShopDao) {

    private val defaultLimit = 20

    var offset = 0

    private val timestamp = Date().time

    fun getSeriesBySeriesId(id: String): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesBySeriesId(id)
    }

    fun getSeriesByCreatorId(id: String, offset: Int): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesByCreatorId(id, "-startYear", offset, defaultLimit)
    }

    fun getSeriesByCreatorId(id: String): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesByCreatorId(id, "-startYear", 0, defaultLimit)
    }

    fun getSeriesByCharacterId(id: Int): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesByCharacterId(id.toString())
    }

    fun getSeriesByCharacterId(id: Int, offset: Int): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesByCharacterId(id.toString(), offset, defaultLimit)
    }

    fun searchSeriesNameStartsWith(nameStartWith: String): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.searchSeriesNameStartsWith(nameStartWith, "-startYear", offset, defaultLimit)
    }

    fun getSeriesDetailById(seriesId: String): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getSeriesDetailById(seriesId)
    }

    fun getSeriesFromDesk(): LiveData<List<DeskItem>> {
        return shopDao.getSeriesInDesk()
    }
}