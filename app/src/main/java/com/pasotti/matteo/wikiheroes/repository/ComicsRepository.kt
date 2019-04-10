package com.pasotti.matteo.wikiheroes.repository

import androidx.lifecycle.LiveData
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.room.ComicsDao
import com.pasotti.matteo.wikiheroes.utils.PreferenceManager
import com.pasotti.matteo.wikiheroes.utils.Utils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class ComicsRepository @Inject
constructor(private val marvelApi: MarvelApi , val comicsDao: ComicsDao , val preferenceManager: PreferenceManager) {

    private val defaultLimit = 20

    var offset = 0

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

    fun getComicsOfTheWeek( page : Int, week: Utils.WEEK) : LiveData<Resource<List<Detail>>> {
        return object : NetworkBoundResource<List<Detail>, DetailResponse>() {
            override fun saveFetchData(item: DetailResponse) {

                preferenceManager.setString(PreferenceManager.LAST_DATE_SYNC , Utils.getCurrentDate())
                val newComics = item.data.results
                if(newComics.isNotEmpty()) {
                    offset += defaultLimit
                    newComics.forEach { comic ->
                        run {
                            comic.page = page
                            comic.week = week
                        }
                    }

                    comicsDao.insertComics(newComics)
                }
            }

            override fun shouldFetch(data: List<Detail>?): Boolean {

                if(data != null && data.isNotEmpty()) {
                    offset = data.size
                }

                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Detail>> {
                return if(page == 0) {
                    comicsDao.getComics(week)
                } else  {
                    comicsDao.getComicsByPage(page , week)
                }
            }

            override fun fetchService(): LiveData<ApiResponse<DetailResponse>> {
                if(page == 0) {
                    offset = 0
                }
                return marvelApi.getComicsOfTheWeek(week.toString() , "-onsaleDate" , "comic", timestamp.toString() ,Utils.MARVEL_PUBLIC_KEY ,  hash,  offset, defaultLimit )
            }

            override fun onFetchFailed() {
            }

        }.asLiveData
    }

    fun getOldestComicsByCharacterId(id : Int) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsByCharacterId(id.toString(), Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "onsaleDate")
    }

    fun checkSyncComics() {

        val todayDate = Utils.getCurrentDate()
        var lastSynchDate = preferenceManager.getString(PreferenceManager.LAST_DATE_SYNC, "")

        // refresh comics every 5 days
        if (lastSynchDate != null && lastSynchDate != "" && Utils.getDifferenceBetweenDates(lastSynchDate, todayDate) == 5L) {
            thread {
                comicsDao.deleteComics()
            }

        }
    }
}