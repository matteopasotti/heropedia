package com.pasotti.matteo.wikiheroes.repository

import androidx.lifecycle.LiveData
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.models.DeskItem
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.room.ShopDao
import java.util.*


class SeriesRepository (private val marvelApi: MarvelApi, private val shopDao: ShopDao) : BaseRepository() {

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

    suspend fun getSeriesByCharacterIdCoroutine(id: Int) : DetailResponse? {
        return safeApiCall(
                call = { marvelApi.getSeriesByCharacterIdCoroutine(id.toString()).await() },
                errorMessage = "Error fetching Series by character Id"
        )
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