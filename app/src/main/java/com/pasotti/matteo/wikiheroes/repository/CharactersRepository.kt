package com.pasotti.matteo.wikiheroes.repository

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.api.SchedulersFacade
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.CharacterResponse
import com.pasotti.matteo.wikiheroes.room.CharacterDao
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.CharacterAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.CharactersAdapter
import io.reactivex.Scheduler
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepository @Inject
constructor(val characterDao: CharacterDao, val marvelApi: MarvelApi, val schedulersFacade: SchedulersFacade) {

    private val disposables = CompositeDisposable()

    val defaultLimit = 20

    var countLimit = 0

    val timestamp = Date().time;

    val hash = Utils.md5(timestamp.toString() + Utils.MARVEL_PRIVATE_KEY + Utils.MARVEL_PUBLIC_KEY)

    val data = MutableLiveData<Resource<CharacterResponse>>()

    fun getCharacters(page : Int): LiveData<Resource<List<Character>>> {

        return object : NetworkBoundResource<List<Character>, CharacterResponse>() {
            override fun saveFetchData(item: CharacterResponse) {
                if(page > 0) {
                    countLimit += defaultLimit
                } else {
                    countLimit = item.data.limit
                }

                Log.i("CharacterRepository", "saveFetchData countLimit = " + countLimit)
                characterDao.insertCharacters(item.data.results)
            }

            override fun shouldFetch(data: List<Character>?): Boolean {
                countLimit = if(data != null && data.size > 0) data.size else 0
                return page > 0 || data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Character>> {
                return characterDao.getCharacters()
            }

            override fun fetchService(): LiveData<ApiResponse<CharacterResponse>> {
                return marvelApi.getCharacters(timestamp.toString(), Utils.MARVEL_PUBLIC_KEY, hash, countLimit + defaultLimit)
            }

            override fun onFetchFailed() {

            }

        }.asLiveData

    }

    fun clear() {
        disposables.clear()
    }
}
