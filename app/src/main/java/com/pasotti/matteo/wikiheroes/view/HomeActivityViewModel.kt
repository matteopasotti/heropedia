package com.pasotti.matteo.wikiheroes.view

import android.arch.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.repository.CharactersRepository
import javax.inject.Inject

class HomeActivityViewModel @Inject
constructor(private val charactersRepository: CharactersRepository) : ViewModel() {
}