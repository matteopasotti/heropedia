package com.pasotti.matteo.wikiheroes.view.ui.home.comics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.view.adapter.HomeComicsAdapter
import javax.inject.Inject

class HomeComicsViewModel @Inject
constructor(private val comicsRepository: ComicsRepository) : ViewModel() {

    var pageCounter = 0

    var firstTime = false

    lateinit var adapter : HomeComicsAdapter

    private val page: MutableLiveData<Int> = MutableLiveData()

    fun getComicsOfTheWeek() = comicsRepository.getComicsOfTheWeek(0)

    fun postPage(page: Int) { this.page.value = page }
}