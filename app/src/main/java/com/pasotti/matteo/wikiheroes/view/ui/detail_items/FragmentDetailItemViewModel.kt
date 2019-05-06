package com.pasotti.matteo.wikiheroes.view.ui.detail_items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.repository.CreatorsRepository
import com.pasotti.matteo.wikiheroes.repository.SeriesRepository
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.CreatorRowAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.CreatorsAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.MoreGalleryAdapter
import javax.inject.Inject

class FragmentDetailItemViewModel @Inject
constructor(private val comicsRepository: ComicsRepository, private val seriesRepository: SeriesRepository , private val creatorsRepository: CreatorsRepository) : ViewModel() {

    lateinit var resourceURI : String

    lateinit var id : String  //id of the resourceURI

    lateinit var seriesId : String

    lateinit var section : String

    lateinit var creatorAdapter : CreatorRowAdapter

    lateinit var moreComicsAdapter : MoreGalleryAdapter

    var seriesThumbnail : String = ""

    var seriesTitle : String = ""

    private var goodItems: MutableList<Pair<String?, MutableList<Item>>> = mutableListOf()

    lateinit var creators: List<Item>

    lateinit var item : Detail

    fun addToShop() = comicsRepository.addToShop(item)

    fun removeFromShop() = comicsRepository.removeFromShop(item)

    fun getItemFromShop() = comicsRepository.getItemFromShop(item)

    //get series details by id
    fun getSeriesDetailById(seriesId : Int) = seriesRepository.getSeriesDetailById(seriesId.toString())


    fun getCreatorsMap(creators: List<Item>): MutableList<Pair<String?, MutableList<Item>>> {

        if (creators != null && creators.isNotEmpty()) {
            for (creator in creators) {
                if (creator.role != null && creator.name != null) {
                    //check if we already have this role and this creator in out list in our list
                    if(!isRoleAlreadyAdded(creator.role)) {
                        addCreatorAndRole(creator)
                    } else if (!isCreatorAlreadyAdded(creator)) {
                        //if false, we add it to the list
                        addCreator(creator)
                    }
                }
            }
        }

        return goodItems
    }

    private fun isRoleAlreadyAdded(role: String): Boolean {
        return goodItems.count { it.first.equals(role) } > 0
    }

    private fun isCreatorAlreadyAdded(creator: Item): Boolean {
        if (goodItems.isNotEmpty()) {
            return goodItems.count { it.first.equals(creator.role) && it.second.contains(creator) } > 0
        } else {
            return false
        }

        return false
    }

    private fun addCreatorAndRole(creator : Item) {
        goodItems.add(Pair(creator.role , mutableListOf(creator)))
    }

    private fun addCreator(creator : Item) {
        var creators : MutableList<Item> = mutableListOf()

        creators = goodItems.filter { it.first.equals(creator.role) }.get(0).second

        val index = goodItems.indexOfFirst { it.first.equals(creator.role) }

        creators.add(creator)

        goodItems[index] = Pair(creator.role , creators)
    }

    fun getCreatorDetail( creator: Item) = creatorsRepository.getCreatorDetailById(Utils.getIdByResourceURI(creator.resourceURI))

    fun getItems(resourceUri : String, type: String) : LiveData<ApiResponse<DetailResponse>> {

        seriesId = Utils.getIdByResourceURI(resourceUri)

        when(type) {
            "Series" -> {
                return comicsRepository.getComicsBySeriesId(id!!)
            }

            "Comics" -> {
                //get the comics of the same collection
                return comicsRepository.getComicsBySeriesId(seriesId)
            }
        }

        return MutableLiveData()
    }


    fun getSeriesDetails() : LiveData<ApiResponse<DetailResponse>> = seriesRepository.getSeriesBySeriesId(seriesId)

    fun getImageUri(item : Detail): String {
        return item.thumbnail?.path + "." + item.thumbnail?.extension
    }
}