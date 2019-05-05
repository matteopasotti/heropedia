package com.pasotti.matteo.wikiheroes.view.ui.person.comics

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.repository.SeriesRepository
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.HomeComicsAdapter
import javax.inject.Inject

class CreatorFragmentsViewModel @Inject
constructor(private val comicsRepository: ComicsRepository, private val seriesRepository: SeriesRepository) : ViewModel() {

    lateinit var creator : Item

    lateinit var adapter : HomeComicsAdapter

    fun getComicsByCreatorId( id : String) = comicsRepository.getComicsByCreatorId(id)

    fun getSeriesByCreatorId( id : String) = seriesRepository.getSeriesByCreatorId(id)

    fun getCreatorId( creator : Item) : String {
        return Utils.getIdByResourceURI(creator.resourceURI)
    }
}