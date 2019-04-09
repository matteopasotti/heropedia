package com.pasotti.matteo.wikiheroes.view.adapter

import android.view.View
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.view.viewholder.BaseViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.HomeComicsViewHolder

class HomeComicsAdapter ( val delegate : HomeComicsViewHolder.Delegate) : BaseAdapter() {

    init {
        addItems(ArrayList<Detail>())
    }

    fun addList( comics: List<Detail>) {
        clearItems()
        addItems(comics)
        notifyDataSetChanged()
    }

    fun updateList( comics : List<Detail>) {
        addItems(comics)
        notifyItemInserted(items.size)
    }


    override fun layout(item: Any?): Int {
        return R.layout.home_item_comic
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
       return HomeComicsViewHolder(view , delegate)
    }
}