package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DetailImageViewModel() : ViewModel() {

    // PRIVATE DATA
    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> get() = _imageUrl

    // PUBLIC ACTIONS ---
    fun loadDataWhenFragmentStarts(imageUrl: String) {
        _imageUrl.value = imageUrl
    }
}