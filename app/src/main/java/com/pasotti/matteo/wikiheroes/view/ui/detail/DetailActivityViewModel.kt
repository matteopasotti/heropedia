package com.pasotti.matteo.wikiheroes.view.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import javax.inject.Inject

class DetailActivityViewModel @Inject
constructor(private val charactersRepository: CharactersRepository) : ViewModel() {

    fun getComicsByCharacterId(id : Int) : LiveData<ApiResponse<DetailResponse>> = charactersRepository.getComicsByCharacterId(id)
}