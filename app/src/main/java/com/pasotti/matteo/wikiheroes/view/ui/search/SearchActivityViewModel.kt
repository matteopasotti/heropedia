package com.pasotti.matteo.wikiheroes.view.ui.search

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.models.SearchObjectItem
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.repository.CreatorsRepository
import com.pasotti.matteo.wikiheroes.repository.SeriesRepository
import com.pasotti.matteo.wikiheroes.view.adapter.SearchAdapter
import javax.inject.Inject

class SearchActivityViewModel(private val charactersRepository: CharactersRepository,
                              private val comicsRepository: ComicsRepository,
                              private val seriesRepository: SeriesRepository,
                              private val creatorsRepository: CreatorsRepository) : ViewModel() {

    val defaultLimit = 10

    lateinit var adapter: SearchAdapter

    var switchTab = false

    var searchOption: String? = null

    lateinit var searchText: String

    var charactersPageCounter = 0

    var comicsPageCounter = 0

    var seriesPageCounter = 0

    var personPageCounter = 0

    var characters: MutableList<SearchObjectItem>? = null

    var comics: MutableList<SearchObjectItem>? = null

    var series: MutableList<SearchObjectItem>? = null

    var persons: MutableList<SearchObjectItem>? = null

    fun searchCharacter(nameStartsWith: String) = charactersRepository.searchCharacterByName(nameStartsWith)

    fun searchComics(nameStartsWith: String) = comicsRepository.searchComicsNameStartsWith(nameStartsWith)

    fun searchSeries(nameStartsWith: String) = seriesRepository.searchSeriesNameStartsWith(nameStartsWith)

    fun searchCreator(nameStartsWith: String) = creatorsRepository.searchCreatorByName(nameStartsWith)

    fun resetLists() {
        characters = null
        comics = null
        series = null
        persons = null

        charactersPageCounter = 0
        comicsPageCounter = 0
        seriesPageCounter = 0
        personPageCounter = 0

        charactersRepository.offset = 0
        comicsRepository.offset = 0
        seriesRepository.offset = 0
        creatorsRepository.offset = 0
    }

    fun increasePage() {
        if (searchOption != null) {
            when (searchOption) {
                "Character" -> charactersPageCounter += 1
                "Comic" -> comicsPageCounter += 1
                "Series" -> seriesPageCounter += 1
                "Person" -> personPageCounter += 1
            }
        }
    }

    fun increaseOffset() {
        if (searchOption != null) {
            when (searchOption) {
                "Character" -> charactersRepository.offset += defaultLimit
                "Comic" -> comicsRepository.offset += defaultLimit
                "Series" -> seriesRepository.offset += defaultLimit
                "Person" -> creatorsRepository.offset += defaultLimit
            }
        }
    }
}