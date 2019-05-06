package com.pasotti.matteo.wikiheroes.view.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.repository.MainRepository
import com.pasotti.matteo.wikiheroes.repository.SeriesRepository
import com.pasotti.matteo.wikiheroes.view.adapter.DetailAdapter
import javax.inject.Inject

class FragmentCharacterDetailViewModel @Inject
constructor(private val mainRepository: MainRepository,
            private val charactersRepository: CharactersRepository,
            private val comicsRepository: ComicsRepository,
            private val seriesRepository: SeriesRepository) : ViewModel() {

    var character : Character? = null

    lateinit var comicsAdapter : DetailAdapter

    lateinit var seriesAdapter : DetailAdapter

    fun saveDominantColor(color : Int) = mainRepository.saveSaveDominantColor(color)

    fun getFavCharacterById( id : Int) = charactersRepository.getFavCharacterById(id)

    fun addFavCharacter( character: Character) = charactersRepository.addFavCharacter(character)

    fun removeFavCharacter( character: Character) = charactersRepository.removeFavCharacter(character)

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

    fun getImageUri(character : Character) : String {
        return character.thumbnail.path + "." + character.thumbnail.extension
    }
}