package com.pasotti.matteo.wikiheroes.view.ui.home.comics

import android.view.View
import androidx.databinding.Bindable
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.DetailItemBindingViewModel
import org.joda.time.DateTime


class HomeComicUIViewModel(val item: Detail) : DetailItemBindingViewModel() {


    override fun getTitle(): String? = item.title

    override fun getDescription(): String? = ""

    override fun getUrlImage(): String? {
        return item.thumbnail?.path + "." + item.thumbnail?.extension
    }

    @Bindable
    fun getPublishDate(): String? {
        if (item.dates != null && item.dates.size > 0) {
            for (date in item.dates) {
                if (date.type.equals("onsaleDate")) {
                    return DateTime(date.date).toLocalDate().toString()
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
    fun getNumberPagesVisibility(): Int = if (getNumberPages() == "") View.GONE else View.VISIBLE

    @Bindable
    fun getNumberPages(): String {
        return if (item.pageCount == 0) {
            ""
        } else {
            item.pageCount.toString()
        }
    }
}