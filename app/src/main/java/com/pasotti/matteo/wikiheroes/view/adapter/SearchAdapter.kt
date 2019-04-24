package com.pasotti.matteo.wikiheroes.view.adapter

import android.view.View
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.SearchObjectItem
import com.pasotti.matteo.wikiheroes.view.viewholder.BaseViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.SearchObjectCharacterViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.SearchObjectComicViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.SearchObjectSeriesViewHolder

class SearchAdapter ( val delegateComic : SearchObjectComicViewHolder.Delegate ,
                      val delegateCharacter : SearchObjectCharacterViewHolder.Delegate,
                      val delegateSeries : SearchObjectSeriesViewHolder.Delegate) : BaseAdapter() {


    init {
        addItems(ArrayList<SearchObjectItem>())
    }

    fun createList( newItems : List<SearchObjectItem>) {
        clearItems()
        addItems(newItems)
        notifyDataSetChanged()
    }

    fun addNewItems(newItems : List<SearchObjectItem>) {
        addItems(newItems)
        notifyItemInserted(items.size)
    }


    override fun layout(item: Any?): Int {
        if( item is Character) {
            return R.layout.item_character
        }

        if(item is Detail && item.getType() == SearchObjectItem.TYPE_COMIC) {
            return R.layout.home_item_comic
        }

        if(item is Detail && item.getType() == SearchObjectItem.TYPE_SERIES) {
            return R.layout.item_series
        }

        return R.layout.home_item_comic
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return when (layout) {
            R.layout.item_character -> SearchObjectCharacterViewHolder(view , delegateCharacter)
            R.layout.home_item_comic -> SearchObjectComicViewHolder(view, delegateComic)
            else -> SearchObjectSeriesViewHolder(view, delegateSeries)
        }
    }
}