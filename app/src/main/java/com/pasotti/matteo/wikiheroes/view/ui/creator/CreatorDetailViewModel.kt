package com.pasotti.matteo.wikiheroes.view.ui.creator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
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

    lateinit var type : String

    private val defaultLimit = 20

    var offset = 0

    var itemsLiveData: LiveData<ApiResponse<DetailResponse>> = MutableLiveData()

    private val page: MutableLiveData<Int> = MutableLiveData()


    init {
        itemsLiveData = Transformations.switchMap(page , { getItemsByCreatorId(creator) })
    }

    /**
     * Use this method in case of positive response
     */
    fun increaseOffset() {
        offset += defaultLimit
    }

    /**
     * Use this method to increase the page and make a call automatically
     */
    fun postPage(page: Int) { this.page.value = page }



    private fun getItemsByCreatorId(item : Item) : LiveData<ApiResponse<DetailResponse>> {

        val id = Utils.getIdByResourceURI(item.resourceURI)

        when (type) {
            "Comics" -> {
                return comicsRepository.getComicsByCreatorId(id!!, offset)
            }
            "Series" -> {
                return comicsRepository.getSeriesByCreatorId(id!!, offset)
            }

            "Events" -> {
                return comicsRepository.getEventsByCreatorId(id!!, offset)
            }

        }

        return MutableLiveData()
    }
}