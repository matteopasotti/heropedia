package com.pasotti.matteo.wikiheroes.view.adapter

import android.view.View
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.view.viewholder.BaseViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.CharacterViewHolder

class CharactersAdapter(val delegate: CharacterViewHolder.Delegate) : BaseAdapter() {


    init {
        addItems(ArrayList<Character>())
    }

    fun updateList( characters : List<Character>) {
        addItems(characters)
        notifyDataSetChanged()
    }


    fun clearAll() {
        items.clear()
        notifyDataSetChanged()
    }


    override fun viewHolder(layout: Int, view: View): BaseViewHolder {

        return CharacterViewHolder(view, delegate)

    }

    override fun layout(item: Any): Int {
        if(item == null) return R.layout.item_loading else return R.layout.item_character
    }



}