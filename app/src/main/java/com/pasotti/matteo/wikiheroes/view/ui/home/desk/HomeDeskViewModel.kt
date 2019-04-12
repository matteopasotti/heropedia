package com.pasotti.matteo.wikiheroes.view.ui.home.desk

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.view.adapter.DetailAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.FavCharacterAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.ShopComicAdapter
import javax.inject.Inject

class HomeDeskViewModel @Inject
constructor(private val comicsRepository: ComicsRepository, private val charactersRepository: CharactersRepository) : ViewModel() {

    lateinit var adapter : ShopComicAdapter

    lateinit var adapterCharacters : FavCharacterAdapter

    fun getItemsFromShop() = comicsRepository.getItemsFromShop()

    fun getFavCharacters() = charactersRepository.getFavCharacters()

    fun getThisWeekDate() = comicsRepository.getThisWeekDate()
}