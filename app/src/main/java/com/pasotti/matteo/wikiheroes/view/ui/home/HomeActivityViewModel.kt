package com.pasotti.matteo.wikiheroes.view.ui.home

import androidx.lifecycle.*
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.CharacterResponse
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.view.adapter.CharactersAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeActivityViewModel @Inject
constructor(private val charactersRepository: CharactersRepository) : ViewModel() {

    lateinit var adapter : CharactersAdapter

    var firstTime = false

    var pageCounter = 0

    private val page: MutableLiveData<Int> = MutableLiveData()

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters


    fun getCharacters(page: Int) {
        viewModelScope.launch {
            _characters.postValue(charactersRepository.getCharactersCoroutine(page))
        }
    }

    fun postPage(page: Int) { this.page.value = page }
}