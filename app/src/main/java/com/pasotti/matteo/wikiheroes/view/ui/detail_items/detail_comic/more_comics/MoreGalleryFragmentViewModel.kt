package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_comics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.utils.Utils
import javax.inject.Inject

class MoreGalleryFragmentViewModel@Inject
constructor(private val comicsRepository: ComicsRepository) : ViewModel() {

    lateinit var item : Item

    lateinit var id : String  //id of comic


    fun getItems(item : Item, type: String) : LiveData<ApiResponse<DetailResponse>> {

        val id = Utils.getIdByResourceURI(item.resourceURI)

        when(type) {
            "Series" -> {
                return comicsRepository.getComicsBySeriesId(id!!)
            }
        }

        return MutableLiveData()
    }
}