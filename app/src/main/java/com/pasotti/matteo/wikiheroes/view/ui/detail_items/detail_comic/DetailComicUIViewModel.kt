package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic

import androidx.databinding.BaseObservable
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.DetailItemBindingViewModel

class DetailComicUIViewModel(val item: Detail) : DetailItemBindingViewModel() {


    override fun getTitle(): String? = item.title

    override fun getDescription(): String? {

        if(item.description == null || (item.description != null && item.description.equals(""))) {
            return "Description not available"
        } else {
            return item.description
        }

    }

    override fun getUrlImage(): String? {
        return item.thumbnail?.path + "." + item.thumbnail?.extension
    }


}