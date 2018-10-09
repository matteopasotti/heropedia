package com.pasotti.matteo.wikiheroes.view.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import javax.inject.Inject


class HomeActivityViewModel @Inject
constructor(private val charactersRepository: CharactersRepository) : ViewModel() {

    var charactersLiveData: LiveData<Resource<List<Character>>> = MutableLiveData()

    private val page: MutableLiveData<Int> = MutableLiveData()

    init {
        charactersLiveData = Transformations.switchMap(page , {charactersRepository.getCharacters(page.value!!)} )
    }

    fun postPage(page: Int) { this.page.value = page }
}