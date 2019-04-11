package com.pasotti.matteo.wikiheroes.view.ui.home.desk

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import com.pasotti.matteo.wikiheroes.view.adapter.DetailAdapter
import javax.inject.Inject

class HomeDeskViewModel @Inject
constructor(private val comicsRepository: ComicsRepository) : ViewModel() {

    lateinit var adapter : DetailAdapter

    fun getItemsFromShop() = comicsRepository.getItemsFromShop()
}