package com.pasotti.matteo.wikiheroes.view.ui.detail

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.repository.MainRepository
import javax.inject.Inject

class DetailActivityViewModel @Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

    lateinit var character : Character

    fun saveDominantColor(color : Int) = mainRepository.saveSaveDominantColor(color)
}