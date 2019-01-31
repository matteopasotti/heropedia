package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_comics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.repository.SeriesRepository
import com.pasotti.matteo.wikiheroes.utils.Utils
import javax.inject.Inject

class MoreGalleryFragmentViewModel@Inject
constructor(private val comicsRepository: ComicsRepository, private val seriesRepository: SeriesRepository) : ViewModel() {

    lateinit var item : Item

    lateinit var id : String  //id of the item

    lateinit var seriesId : String

    lateinit var section : String

    var seriesThumbnail : String = ""

    var seriesTitle : String = ""


    fun getItems(item : Item, type: String) : LiveData<ApiResponse<DetailResponse>> {

        seriesId = Utils.getIdByResourceURI(item.resourceURI)

        when(type) {
            "Series" -> {
                //return comicsRepository.getComicsBySeriesId(id!!)
            }

            "Comics" -> {
                //get the comics of the same collection
                return comicsRepository.getComicsBySeriesId(seriesId)
            }
        }

        return MutableLiveData()
    }


    fun getSeriesDetails() : LiveData<ApiResponse<DetailResponse>> = seriesRepository.getSeriesBySeriesId(seriesId)
}