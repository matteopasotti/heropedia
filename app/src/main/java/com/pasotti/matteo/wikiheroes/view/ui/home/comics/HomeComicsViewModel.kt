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

    var firstTime = false

    var weekHasChanged = false

    var currentWeek : Utils.WEEK = Utils.WEEK.thisWeek

    lateinit var adapter : HomeComicsAdapter

    private val week : MutableLiveData<Utils.WEEK> = MutableLiveData()

    init {
        comicsLiveData = Transformations.switchMap(week) { comicsRepository.getComicsOfTheWeek(week.value!!)}
    }

    fun changeWeek(newWeek : Utils.WEEK) {
        this.week.value = newWeek
    }
}