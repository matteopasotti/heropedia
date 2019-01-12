package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import javax.inject.Inject

class DetailComicViewModel @Inject
constructor(private val charactersRepository: CharactersRepository) : ViewModel() {

    lateinit var item : Detail

    lateinit var section : String
}