package com.pasotti.matteo.wikiheroes.view.ui.home.desk

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.repository.SeriesRepository
import com.pasotti.matteo.wikiheroes.view.adapter.FavCharacterAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.DeskComicsAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.DeskSeriesAdapter
import javax.inject.Inject

class HomeDeskViewModel @Inject
constructor(private val comicsRepository: ComicsRepository,
            private val charactersRepository: CharactersRepository ,
            private val seriesRepository: SeriesRepository) : ViewModel() {

    lateinit var adapter : DeskComicsAdapter

    lateinit var adapterSeries : DeskSeriesAdapter

    lateinit var adapterCharacters : FavCharacterAdapter

    fun getComicsFromShop() = comicsRepository.getItemsFromShop()

    fun getFavCharacters() = charactersRepository.getFavCharacters()

    fun getSeriesFromDesk() = seriesRepository.getSeriesFromDesk()

    fun getThisWeekDate() = comicsRepository.getThisWeekDate()
}