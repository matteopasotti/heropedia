package com.pasotti.matteo.wikiheroes.view.ui.person

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.repository.CreatorsRepository
import javax.inject.Inject

class PersonDetailActivityViewModel @Inject
constructor(private val creatorsRepository: CreatorsRepository) : ViewModel() {

    fun getCreatorDetailsById( id : String) = creatorsRepository.getCreatorDetailById(id)
}