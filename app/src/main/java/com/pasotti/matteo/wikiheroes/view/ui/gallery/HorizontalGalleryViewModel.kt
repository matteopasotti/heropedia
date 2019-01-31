package com.pasotti.matteo.wikiheroes.view.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.repository.SeriesRepository
import javax.inject.Inject

class HorizontalGalleryViewModel @Inject
constructor(private val charactersRepository: CharactersRepository, private val comicsRepository: ComicsRepository, private val seriesRepository: SeriesRepository) : ViewModel() {

    lateinit var section : String

    var characterId : Int = 0

    lateinit var characterName : String

    fun getItems(characterId: Int, type: String): LiveData<ApiResponse<DetailResponse>> {

        when (type) {
            "Comics" -> {
                return comicsRepository.getComicsByCharacterId(characterId)
            }
            "Series" -> {
                return seriesRepository.getSeriesByCharacterId(characterId)
            }
            "Stories" -> {
                return charactersRepository.getStoriesByCharacterId(characterId)
            }
            "Events" -> {
                return charactersRepository.getEventsByCharacterId(characterId)
            }

        }

        return MutableLiveData()
    }

}