package com.pasotti.matteo.wikiheroes.view.adapter

import android.view.View
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.view.viewholder.BaseViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.CreatorRowViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.CreatorViewHolder

class CreatorRowAdapter (val delegate : CreatorViewHolder.Delegate ,val  map : List<Pair<String? , List<Item>>>) : BaseAdapter() {

    init {
        addItems(map)
    }


    override fun layout(item: Any?): Int {
        return R.layout.creator_row
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return CreatorRowViewHolder(view , delegate)
    }


}