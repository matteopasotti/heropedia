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
constructor(val marvelApi: MarvelApi) {

    val defaultLimit = 10

    var offset = 0

    val timestamp = Date().time

    val hash = Utils.md5(timestamp.toString() + Utils.MARVEL_PRIVATE_KEY + Utils.MARVEL_PUBLIC_KEY)

    fun getComicsBySeriesId(id : String) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsBySeriesId(id, Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "issueNumber")
    }
}