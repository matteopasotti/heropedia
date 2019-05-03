package com.pasotti.matteo.wikiheroes.view.adapter

import android.view.View
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.view.viewholder.BaseViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.FavCreatorViewHolder

class FavCreatorAdapter( val delegate : FavCreatorViewHolder.Delegate) : BaseAdapter() {


    init {
        addItems(ArrayList<Item>())
    }

    fun updateItems( newItems : List<Item>) {
        clearItems()
        addItems(newItems)
        notifyDataSetChanged()
    }

    override fun layout(item: Any?): Int {
        return R.layout.fav_creator_row
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return FavCreatorViewHolder( view , delegate)
    }
}