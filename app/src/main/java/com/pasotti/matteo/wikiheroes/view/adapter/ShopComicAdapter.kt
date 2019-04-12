package com.pasotti.matteo.wikiheroes.view.adapter

import android.view.View
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.view.viewholder.BaseViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.ShopComicViewHolder

class ShopComicAdapter (val delegate : ShopComicViewHolder.Delegate, private val thisWeekDate : String?) : BaseAdapter() {

    init {
        addItems(ArrayList<Detail>())
    }

    fun updateList( newItems : List<Detail>) {
        clearItems()
        addItems(newItems)
        notifyDataSetChanged()
    }


    override fun layout(item: Any?): Int {
        return R.layout.shop_item_row
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return if(thisWeekDate == null) {
            ShopComicViewHolder( view, delegate, "")
        } else {
            ShopComicViewHolder( view, delegate, thisWeekDate)
        }
    }
}