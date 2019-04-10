package com.pasotti.matteo.wikiheroes.view.ui.splash

import androidx.lifecycle.ViewModel
import com.pasotti.matteo.wikiheroes.repository.ComicsRepository
import javax.inject.Inject

class SplashActivityViewModel @Inject
constructor(private val comicsRepository: ComicsRepository) : ViewModel() {

    fun checkDateSyncComics() = comicsRepository.checkSyncComics()
}