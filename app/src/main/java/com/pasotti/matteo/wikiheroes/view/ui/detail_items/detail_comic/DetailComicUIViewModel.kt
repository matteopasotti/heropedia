package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.pasotti.matteo.wikiheroes.models.Date
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.DetailItemBindingViewModel
import java.text.SimpleDateFormat

class DetailComicUIViewModel(val item: Detail, val section : String) : DetailItemBindingViewModel() {

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

    @SuppressLint("SimpleDateFormat")
    private val sdfDateFormat = SimpleDateFormat("dd/MM/YYYY")


    override fun getTitle(): String? = item.title

    override fun getDescription(): String? {

        return if (item.description == null || (item.description == "")) {
            "Description not available"
        } else {
            item.description
        }

    }

    override fun getUrlImage(): String? {
        return item.thumbnail?.path + "." + item.thumbnail?.extension
    }

    @Bindable
    fun getPublishDate(): String? {
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
    fun getPriceVisibility(): Int = if (getPrice() == "") View.GONE else View.VISIBLE

    @Bindable
    fun getMoreComicsVisibility(): Int {
        return if (item.series?.resourceURI != null && item.issueNumber != null && item.issueNumber != "0") {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    @Bindable
    fun getNumberPagesVisibility() : Int = if(getNumberPages() == "") View.GONE else View.VISIBLE

    @Bindable
    fun getNumberPages(): String {
        return if (item.pageCount == 0) {
            ""
        }
        else {
            item.pageCount.toString()
        }
    }


}