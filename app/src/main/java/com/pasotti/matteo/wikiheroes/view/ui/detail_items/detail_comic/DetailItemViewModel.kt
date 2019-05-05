package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.repository.SeriesRepository
import javax.inject.Inject

class DetailItemViewModel @Inject
constructor( private val comicsRepository: ComicsRepository , private val seriesRepository: SeriesRepository) : ViewModel() {

    lateinit var item : Detail

    lateinit var section : String

    fun addToShop() = comicsRepository.addToShop(item)

    fun removeFromShop() = comicsRepository.removeFromShop(item)

    fun getItemFromShop() = comicsRepository.getItemFromShop(item)

    //get series details by id
    fun getSeriesDetailById(seriesId : Int) = seriesRepository.getSeriesDetailById(seriesId.toString())
}