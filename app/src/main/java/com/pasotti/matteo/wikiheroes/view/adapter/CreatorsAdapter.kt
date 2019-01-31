package com.pasotti.matteo.wikiheroes.view.adapter

import android.view.View
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.view.viewholder.BaseViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.CreatorViewHolder

class CreatorsAdapter (val delegate : CreatorViewHolder.Delegate, private val creators : List<Item>) : BaseAdapter() {

    init {
        addItems(creators)
    }


    override fun layout(item: Any?): Int {
        return R.layout.creator_item
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return CreatorViewHolder(view, delegate, creators)
    }
}