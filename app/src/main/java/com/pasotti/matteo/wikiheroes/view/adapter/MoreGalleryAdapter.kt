package com.pasotti.matteo.wikiheroes.view.adapter

import android.view.View
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.view.viewholder.BaseViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.MoreImageViewHolder

class MoreGalleryAdapter (val delegate : MoreImageViewHolder.Delegate) : BaseAdapter() {


    init {
        addItems(ArrayList<Detail>())
    }

    fun updateList( items : List<Detail>) {
        addItems(items)
        notifyItemInserted(items.size)
    }

    override fun layout(item: Any?): Int {
        return R.layout.item_more_gallery
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return MoreImageViewHolder(view, delegate)
    }
}