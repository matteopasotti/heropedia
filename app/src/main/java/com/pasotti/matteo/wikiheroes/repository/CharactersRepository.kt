package com.pasotti.matteo.wikiheroes.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.api.SchedulersFacade
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

    fun getCharacters(): LiveData<Resource<CharacterResponse>> {

        disposables.add(marvelApi.getCharacters(timestamp.toString(), Utils.MARVEL_PUBLIC_KEY, hash, defaultLimit)
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .subscribe(
                        { greeting ->
                            data.value = Resource.success(greeting)
                            countLimit = greeting.data.limit
                        },
                        { throwable -> data.value = Resource.error(throwable) }
                ))


        return data

    }

    fun loadMoreCharacters(adapter : CharacterAdapter) {
        disposables.add(marvelApi.getCharacters(timestamp.toString(), Utils.MARVEL_PUBLIC_KEY, hash, countLimit + defaultLimit)
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .subscribe(
                        { greeting ->

                            updateAdapter(adapter , greeting)
                        },
                        { throwable -> data.value = Resource.error(throwable) }
                ))
    }

    fun updateAdapter(adapter: CharacterAdapter, response: CharacterResponse) {
        adapter.clearAndAddNews(response.data.results)
        //adapter.notifyItemRangeChanged(countLimit, countLimit + defaultLimit)
        //originalList = response.data.results
        countLimit += defaultLimit
    }

    fun clear() {
        disposables.clear()
    }


    /*private fun loadGreeting(loadGreetingUseCase: LoadGreetingUseCase) {
        disposables.add(loadGreetingUseCase.execute()
                .subscribeOn(schedulersFacade.io())
                .observeOn(schedulersFacade.ui())
                .doOnSubscribe({ __ -> response.setValue(Response.loading()) })
                .subscribe(
                        { greeting -> response.setValue(Response.success(greeting)) },
                        { throwable -> response.setValue(Response.error(throwable)) }
                )
        )
    }*/
}
