package com.pasotti.matteo.wikiheroes.view.ui.search

import androidx.databinding.Bindable
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.DetailItemBindingViewModel
import java.util.*

class SeriesUIViewModel (val item: Detail) : DetailItemBindingViewModel() {


    override fun getTitle(): String? = item.title.toString()

    override fun getDescription(): String? = ""

    override fun getUrlImage(): String? {
        return item.thumbnail?.path + "." + item.thumbnail?.extension
    }

    @Bindable
    fun getStartYearValue() : String {
        return item.startYear.toString()
    }

    @Bindable
    fun getEndYearValue() : String {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        if(item.endYear != 0 && item.endYear > currentYear) {
            return "Present"
        }

        return item.endYear.toString()
    }

}