package com.pasotti.matteo.wikiheroes.view.ui.search

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.view.adapter.CharactersAdapter
import javax.inject.Inject

class SearchActivityViewModel @Inject
constructor(private val charactersRepository: CharactersRepository) : ViewModel() {

    lateinit var adapter : CharactersAdapter

    fun searchCharacter( nameStartsWith : String) = charactersRepository.searchCharacterByName(nameStartsWith)
}