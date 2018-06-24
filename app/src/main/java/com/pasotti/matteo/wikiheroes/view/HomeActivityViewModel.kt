package com.pasotti.matteo.wikiheroes.view

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.api.SchedulersFacade
import com.pasotti.matteo.wikiheroes.models.CharacterResponse
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import javax.inject.Inject
import io.reactivex.disposables.CompositeDisposable



class HomeActivityViewModel @Inject
constructor(private val charactersRepository: CharactersRepository, private val schedulersFacade: SchedulersFacade) : ViewModel() {

    var charactersLiveData: LiveData<Resource> = MutableLiveData<Resource>()

    init {
        charactersLiveData = getCharacters()
    }

    fun getCharacters() : MutableLiveData<Resource> = charactersRepository.getCharacters() as MutableLiveData<Resource>


    override fun onCleared() {
        charactersRepository.clear()
    }
}