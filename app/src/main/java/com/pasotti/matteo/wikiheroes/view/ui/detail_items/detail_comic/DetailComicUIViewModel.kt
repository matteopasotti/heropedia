package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.pasotti.matteo.wikiheroes.models.Date
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.DetailItemBindingViewModel
import java.text.SimpleDateFormat

class DetailComicUIViewModel(val item: Detail) : DetailItemBindingViewModel() {

    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

    val sdfDateFormat = SimpleDateFormat("dd/MM/YYYY")


    override fun getTitle(): String? = item.title

    override fun getDescription(): String? {

        if (item.description == null || (item.description != null && item.description.equals(""))) {
            return "Description not available"
        } else {
            return item.description
        }

    }

    override fun getUrlImage(): String? {
        return item.thumbnail?.path + "." + item.thumbnail?.extension
    }

    @Bindable
    fun getPublishDate(): String {
        if (item.dates != null && item.dates.size > 0) {
            for (date in item.dates) {
                if (date.type.equals("onsaleDate")) {
                    var convertedDate = java.util.Date()
                    convertedDate = dateFormat.parse(date.date)
                    return sdfDateFormat.format(convertedDate)

                }
            }
        }

        return ""
    }

    @Bindable
    fun getPublishedDateVisibility(): Int = if (!getPublishDate().equals("")) View.VISIBLE else View.GONE

    @Bindable
    fun getPrice(): String {
        if (item.prices != null && item.prices.size > 0) {
            for (price in item.prices) {
                if (price.type.equals("printPrice")) {
                    return "$" + price.price
                }
            }
        }

        return ""
    }

    @Bindable
    fun getPriceVisibility(): Int = if (getPrice().equals("")) View.GONE else View.VISIBLE

    @Bindable
    fun getMoreComicsVisibility(): Int {
        if(item.series != null && item.series.resourceURI != null) {
            return View.VISIBLE
        } else {
            return View.GONE
        }
    }


}