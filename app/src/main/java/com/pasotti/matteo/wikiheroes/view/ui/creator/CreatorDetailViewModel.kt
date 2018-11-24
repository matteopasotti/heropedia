package com.pasotti.matteo.wikiheroes.view.ui.creator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.utils.Utils
import javax.inject.Inject

class CreatorDetailViewModel @Inject
constructor(private val comicsRepository: ComicsRepository) : ViewModel() {

    lateinit var creator : Item

    fun getItemsByCreatorId(item : Item, type : String) : LiveData<ApiResponse<DetailResponse>> {

        val id = Utils.getIdByResourceURI(item.resourceURI)

        when (type) {
            "Comics" -> {
                return comicsRepository.getComicsByCreatorId(id)
            }
            /*"Series" -> {
                return charactersRepository.getSeriesByCharacterId(characterId)
            }
            "Stories" -> {
                return charactersRepository.getStoriesByCharacterId(characterId)
            }
            "Events" -> {
                return charactersRepository.getEventsByCharacterId(characterId)
            }*/

        }

        return MutableLiveData()
    }
}