package com.pasotti.matteo.wikiheroes.repository

import androidx.lifecycle.LiveData
import com.pasotti.matteo.wikiheroes.BuildConfig
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.room.ItemDao
import com.pasotti.matteo.wikiheroes.utils.PreferenceManager
import com.pasotti.matteo.wikiheroes.utils.Utils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class CreatorsRepository @Inject
constructor(private val marvelApi: MarvelApi, val itemDao: ItemDao, val preferenceManager: PreferenceManager) {

    private val defaultLimit = 10

    var offset = 0

    private val timestamp = Date().time

    private val hash = Utils.md5(timestamp.toString() + BuildConfig.MARVEL_PRIVATE_KEY + BuildConfig.MARVEL_API_KEY)

    fun searchCreatorByName( nameStartWith : String) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.searchCreatorByName(nameStartWith, BuildConfig.MARVEL_API_KEY, hash, timestamp.toString() , "firstName" , offset, defaultLimit)
    }


    fun getCreatorDetailById( id : String) : LiveData<ApiResponse<DetailResponse>> {
        return marvelApi.getCreatorDetailById(id , BuildConfig.MARVEL_API_KEY, hash, timestamp.toString())
    }

    fun saveCreator( item : Item) {
        thread {
            itemDao.insertItem(item)
        }
    }

    fun removeCreator( item: Item) {
        thread {
            itemDao.removeItem(item.resourceURI)
        }
    }

    fun getFavCreator( item : Item) : LiveData<Item> {
        return itemDao.getItemByResourceUri(item.resourceURI)
    }

    fun getItems() : LiveData<List<Item>> {
        return itemDao.getItems()
    }
}