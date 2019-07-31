package com.pasotti.matteo.wikiheroes.view.ui.home.comics

import androidx.lifecycle.*
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.HomeComicsAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeComicsViewModel @Inject
constructor(private val comicsRepository: ComicsRepository) : ViewModel() {

    var firstTime = false

    var weekHasChanged = false

    var currentWeek : Utils.WEEK = Utils.WEEK.thisWeek

    lateinit var adapter : HomeComicsAdapter

    private val _comics = MutableLiveData<List<Detail>>()
    val comics: LiveData<List<Detail>> = _comics

    private val week : MutableLiveData<Utils.WEEK> = MutableLiveData()

    fun getComicsOfTheWeek(week: Utils.WEEK) {
        viewModelScope.launch {
            _comics.postValue(comicsRepository.getComicsOfTheWeekCoroutine(week))
        }
    }

    fun changeWeek(newWeek : Utils.WEEK) {
        this.week.value = newWeek
    }
}