package com.pasotti.matteo.wikiheroes.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pasotti.matteo.wikiheroes.BuildConfig
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.MarvelApi
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.CharacterResponse
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.FavCharacter
import com.pasotti.matteo.wikiheroes.room.CharacterDao
import com.pasotti.matteo.wikiheroes.room.FavCharacterDao
import com.pasotti.matteo.wikiheroes.utils.PreferenceManager
import com.pasotti.matteo.wikiheroes.utils.Utils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class CharactersRepository @Inject
constructor(val characterDao: CharacterDao, val favCharacterDao: FavCharacterDao, val marvelApi: MarvelApi, val preferenceManager: PreferenceManager) : BaseRepository() {

    val defaultLimit = 10

    var offset = 0

    val timestamp = Date().time


    suspend fun getCharactersCoroutine(page: Int): MutableList<Character>? {
        val characterResponse = safeApiCall(
                call = { marvelApi.getCharacters("-modified", offset, defaultLimit).await() },
                errorMessage = "Error fetching Characters"
        )

        offset += defaultLimit


        return characterResponse?.data?.results
    }

    fun checkSyncCharacters() {

        val todayDate = Utils.getCurrentDate()
        val lastSynchDate = preferenceManager.getString(PreferenceManager.LAST_DATE_SYNC, "")

        // refresh comics every 5 days
        if (lastSynchDate != null && lastSynchDate != "" && Utils.getDifferenceBetweenDates(lastSynchDate, todayDate) == 2L) {
            thread {
                characterDao.deleteCharacters()
            }

        }
    }

    fun getFavCharacters(): LiveData<List<FavCharacter>> {
        return favCharacterDao.getFavCharacters()
    }

    fun addFavCharacter(character: Character) {
        thread {
            val favCharacter = FavCharacter(character.id, character)
            favCharacterDao.insertFavCharacter(favCharacter)
        }
    }

    fun removeFavCharacter(character: Character) {
        thread {
            favCharacterDao.removeFavCharacter(character.id)
        }
    }

    fun getFavCharacterById(id: Int): LiveData<FavCharacter> {
        return favCharacterDao.getFavCharacterById(id)
    }


    fun searchCharacterByName(nameStartsWith: String): LiveData<ApiResponse<CharacterResponse>> {
        return marvelApi.searchCharacterNameStartsWith(nameStartsWith, "name", offset, defaultLimit)
    }
}
