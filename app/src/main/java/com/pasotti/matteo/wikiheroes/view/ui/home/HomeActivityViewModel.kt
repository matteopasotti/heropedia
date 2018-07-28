package com.pasotti.matteo.wikiheroes.view.ui.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.api.SchedulersFacade
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.CharacterResponse
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.view.adapter.CharacterAdapter
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable



class HomeActivityViewModel @Inject
constructor(private val charactersRepository: CharactersRepository, private val schedulersFacade: SchedulersFacade) : ViewModel() {

    var charactersLiveData: LiveData<Resource<List<Character>>> = MutableLiveData()

    val defaultLimit = 20

    var countLimit = 0

    private val page: MutableLiveData<Int> = MutableLiveData()

    init {
        charactersLiveData = Transformations.switchMap(page , {charactersRepository.getCharacters(page.value!!)} )
    }

    fun postPage(page: Int) { this.page.value = page }

    override fun onCleared() {
        charactersRepository.clear()
    }
}