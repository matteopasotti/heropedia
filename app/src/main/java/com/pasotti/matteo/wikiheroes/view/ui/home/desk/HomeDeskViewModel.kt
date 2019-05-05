package com.pasotti.matteo.wikiheroes.view.ui.home.desk

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.repository.CreatorsRepository
import com.pasotti.matteo.wikiheroes.repository.SeriesRepository
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.FavCharacterAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.DeskComicsAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.DeskSeriesAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.FavCreatorAdapter
import javax.inject.Inject

class HomeDeskViewModel @Inject
constructor(private val comicsRepository: ComicsRepository,
            private val charactersRepository: CharactersRepository ,
            private val seriesRepository: SeriesRepository,
            private val creatorsRepository: CreatorsRepository) : ViewModel() {

    lateinit var adapter : DeskComicsAdapter

    lateinit var adapterSeries : DeskSeriesAdapter

    lateinit var adapterCharacters : FavCharacterAdapter

    lateinit var adapterPeople : FavCreatorAdapter

    fun getComicsFromShop() = comicsRepository.getItemsFromShop()

    fun getFavCharacters() = charactersRepository.getFavCharacters()

    fun getSeriesFromDesk() = seriesRepository.getSeriesFromDesk()

    fun getPeopleFromDesk() = creatorsRepository.getItems()

    fun getThisWeekDate() = comicsRepository.getThisWeekDate()

    fun getCreatorDetail( creator: Item) = creatorsRepository.getCreatorDetailById(Utils.getIdByResourceURI(creator.resourceURI))
}