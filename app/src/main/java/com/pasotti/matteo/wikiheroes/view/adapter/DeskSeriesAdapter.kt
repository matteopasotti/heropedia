package com.pasotti.matteo.wikiheroes.view.adapter

import android.view.View
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.view.viewholder.BaseViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.DeskSeriesViewHolder

class DeskSeriesAdapter ( val delegate : DeskSeriesViewHolder.Delegate) : BaseAdapter() {

    init {
        addItems(ArrayList<Detail>())
    }

    fun updateList( newItems : List<Detail>) {
        clearItems()
        addItems(newItems)
        notifyDataSetChanged()
    }

    override fun layout(item: Any?): Int {
        return R.layout.desk_series_row
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return DeskSeriesViewHolder(view , delegate)
    }
}