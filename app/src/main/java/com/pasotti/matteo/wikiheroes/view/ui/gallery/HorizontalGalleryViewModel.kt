package com.pasotti.matteo.wikiheroes.view.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.repository.SeriesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HorizontalGalleryViewModel (private val charactersRepository: CharactersRepository, private val comicsRepository: ComicsRepository, private val seriesRepository: SeriesRepository) : ViewModel() {

    lateinit var section : String

    var characterId : Int = 0

    lateinit var characterName : String

    private val _items = MutableLiveData<DetailResponse>()
    val items: LiveData<DetailResponse> = _items

    fun getItems(characterId: Int, type: String): LiveData<ApiResponse<DetailResponse>> {

        when (type) {
            "Comics" -> {
                return comicsRepository.getComicsByCharacterId(characterId)
            }
            "Series" -> {
                return seriesRepository.getSeriesByCharacterId(characterId)
            }

        }

        return MutableLiveData()
    }

    fun getComicsByCharacterId(characterId: Int) {
        viewModelScope.launch {
            _items.postValue(comicsRepository.getComicsByCharacterIdCorotuine(characterId))
        }
    }

    fun getSeriesByCharacterId(characterId: Int) {
        viewModelScope.launch {
            _items.postValue(seriesRepository.getSeriesByCharacterIdCoroutine(characterId))
        }
    }

}