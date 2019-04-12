package com.pasotti.matteo.wikiheroes.repository

import androidx.lifecycle.LiveData
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.ShopItem
import com.pasotti.matteo.wikiheroes.room.ComicsDao
import com.pasotti.matteo.wikiheroes.room.ShopDao
import com.pasotti.matteo.wikiheroes.utils.PreferenceManager
import com.pasotti.matteo.wikiheroes.utils.Utils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class ComicsRepository @Inject
constructor(private val marvelApi: MarvelApi, val comicsDao: ComicsDao, val shopDao: ShopDao, val preferenceManager: PreferenceManager) {

    private val defaultLimit = 20

    var offset = 0

    private val timestamp = Date().time

    private val hash = Utils.md5(timestamp.toString() + Utils.MARVEL_PRIVATE_KEY + Utils.MARVEL_PUBLIC_KEY)

    fun getComicsBySeriesId(id: String): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsBySeriesId(id, Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "-issueNumber")
    }

    fun getComicsBySeriesId(id: String, offset: Int): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsBySeriesId(id, Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "-issueNumber", offset, defaultLimit)
    }

    fun getComicsByCreatorId(id: String, offset: Int): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsByCreatorId(id, Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), true, "-onsaleDate", offset, defaultLimit)
    }

    fun getComicsByCharacterId(id: Int, offset: Int): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsByCharacterId(id.toString(), Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "-onsaleDate", offset, defaultLimit)
    }

    fun getEventsByCreatorId(id: String, offset: Int): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getEventsByCreatorId(id, Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "issueNumber", offset, defaultLimit)
    }

    fun getComicsByCharacterId(id: Int): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsByCharacterId(id.toString(), Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "-onsaleDate")
    }

    fun getComicsOfTheWeek(week: Utils.WEEK): LiveData<Resource<List<Detail>>> {
        return object : NetworkBoundResource<List<Detail>, DetailResponse>() {
            override fun saveFetchData(item: DetailResponse) {
                preferenceManager.setString(PreferenceManager.LAST_DATE_SYNC, Utils.getCurrentDate())
                val newComics = item.data.results
                if (newComics.isNotEmpty()) {

                    if(week == Utils.WEEK.thisWeek) {
                        preferenceManager.setString(PreferenceManager.THIS_WEEK, getPublishedDate(newComics[0]))
                    }

                    offset += defaultLimit
                    newComics.forEach { comic ->
                        run {
                            comic.page = 0
                            comic.week = week
                        }
                    }

                    comicsDao.insertComics(newComics)
                }
            }

            override fun shouldFetch(data: List<Detail>?): Boolean {

                if (data != null && data.isNotEmpty()) {
                    offset = data.size
                }

                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Detail>> {
                return comicsDao.getComics(week)
            }

            override fun fetchService(): LiveData<ApiResponse<DetailResponse>> {
                return marvelApi.getComicsOfTheWeek(week.toString(), "-onsaleDate", timestamp.toString(), Utils.MARVEL_PUBLIC_KEY, hash, 0, 100)
            }

            override fun onFetchFailed() {
            }

        }.asLiveData
    }

    fun getOldestComicsByCharacterId(id: Int): LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getComicsByCharacterId(id.toString(), Utils.MARVEL_PUBLIC_KEY, hash, timestamp.toString(), "onsaleDate")
    }

    fun checkSyncComics() {

        val todayDate = Utils.getCurrentDate()
        var lastSynchDate = preferenceManager.getString(PreferenceManager.LAST_DATE_SYNC, "")

        // refresh comics every 5 days
        if (lastSynchDate != null && lastSynchDate != "" && Utils.getDifferenceBetweenDates(lastSynchDate, todayDate) == 2L) {
            thread {
                comicsDao.deleteComics()
            }

        }
    }

    fun addToShop(det: Detail) {
        thread {
            val shopItem = ShopItem(det.id, det)
            shopDao.insertItemInShop(shopItem)
        }
    }

    fun removeFromShop(det: Detail) {
        thread {
            shopDao.removeItemFromShop(det.id)
        }
    }

    fun getItemFromShop(det: Detail): LiveData<ShopItem> {
        return shopDao.getItemFromShop(det.id)
    }

    fun getItemsFromShop(): LiveData<List<ShopItem>> {
        return shopDao.getItemsInShop()
    }


    fun getPublishedDate(item: Detail): String {
        if (item.dates != null && item.dates.size > 0) {
            for (date in item.dates) {
                if (date.type.equals("onsaleDate")) {
                    return date.date.toString()
                }
            }
        }

        return ""
    }

    fun getThisWeekDate(): String? {
        return preferenceManager.getString(PreferenceManager.THIS_WEEK, "")
    }
}