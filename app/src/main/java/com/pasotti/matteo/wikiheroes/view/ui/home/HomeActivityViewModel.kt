package com.pasotti.matteo.wikiheroes.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.view.adapter.CharactersAdapter
import javax.inject.Inject


class HomeActivityViewModel @Inject
constructor(private val charactersRepository: CharactersRepository) : ViewModel() {

    var charactersLiveData: LiveData<Resource<List<Character>>> = MutableLiveData()

    lateinit var adapter : CharactersAdapter

    var firstTime = false

    var pageCounter = 0

    private val page: MutableLiveData<Int> = MutableLiveData()

    init {
        charactersLiveData = Transformations.switchMap(page) { charactersRepository.getCharacters(page.value!!)}
    }

    fun postPage(page: Int) { this.page.value = page }
}