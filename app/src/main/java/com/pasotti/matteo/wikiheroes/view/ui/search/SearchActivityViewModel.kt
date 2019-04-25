package com.pasotti.matteo.wikiheroes.view.ui.search

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.repository.CreatorsRepository
import com.pasotti.matteo.wikiheroes.repository.SeriesRepository
import com.pasotti.matteo.wikiheroes.view.adapter.CharactersAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.SearchAdapter
import javax.inject.Inject

class SearchActivityViewModel @Inject
constructor(private val charactersRepository: CharactersRepository,
            private val comicsRepository: ComicsRepository,
            private val seriesRepository: SeriesRepository,
            private val creatorsRepository: CreatorsRepository) : ViewModel() {

    lateinit var adapter : SearchAdapter

    var searchOption : String? = null

    fun searchCharacter( nameStartsWith : String) = charactersRepository.searchCharacterByName(nameStartsWith)

    fun searchComics( nameStartsWith: String) = comicsRepository.searchComicsNameStartsWith(nameStartsWith)

    fun searchSeries( nameStartsWith: String) = seriesRepository.searchSeriesNameStartsWith(nameStartsWith)

    fun searchCreator( nameStartsWith: String ) = creatorsRepository.searchCreatorByName(nameStartsWith)
}