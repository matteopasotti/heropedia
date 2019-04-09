package com.pasotti.matteo.wikiheroes.view.ui.home.comics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.HomeComicsAdapter
import javax.inject.Inject

class HomeComicsViewModel @Inject
constructor(private val comicsRepository: ComicsRepository) : ViewModel() {

    var comicsLiveData: LiveData<Resource<List<Detail>>> = MutableLiveData()

    var pageCounter = 0

    var firstTime = false

    var weekHasChanged = false

    var currentWeek : Utils.WEEK = Utils.WEEK.thisWeek

    lateinit var comicsThisWeek : List<Detail>

    lateinit var comicsLastWeek : List<Detail>

    lateinit var comicsNextWeek : List<Detail>

    lateinit var adapter : HomeComicsAdapter

    private val page: MutableLiveData<Int> = MutableLiveData()

    init {
        comicsLiveData = Transformations.switchMap(page) { comicsRepository.getComicsOfTheWeek(page.value!! , currentWeek)}
    }

    fun postPage(page: Int) { this.page.value = page }
}