package com.pasotti.matteo.wikiheroes.view.adapter

import android.view.View
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.view.viewholder.BaseViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.FavCharacterViewHolder

class FavCharacterAdapter(val delegate: FavCharacterViewHolder.Delegate) : BaseAdapter() {

    init {
        addItems(ArrayList<Character>())
    }

    fun updateList(characters: List<Character>) {
        clearItems()
        addItems(characters)
        notifyDataSetChanged()
    }

    override fun layout(item: Any?): Int {
        return R.layout.fav_character_row
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return FavCharacterViewHolder(view, delegate)
    }
}