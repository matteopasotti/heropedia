package com.pasotti.matteo.wikiheroes.view.adapter

import android.view.View
import android.widget.Filter
import android.widget.Filterable
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.view.viewholder.BaseViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.CharacterViewHolder

class CharactersAdapter(val delegate: CharacterViewHolder.Delegate) : BaseAdapter(), Filterable {

    init {
        addItems(ArrayList<Character>())
    }

    fun updateList( characters : List<Character>) {
        addItems(characters)
        notifyItemInserted(items.size)
    }


    fun clearAll() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return CharacterViewHolder(view, delegate)
    }

    override fun layout(item: Any?): Int {
        if(item == null) return R.layout.item_loading else return R.layout.item_character
    }

    override fun getFilter(): Filter {
        return 
    }


    fun getItemByIndex(position : Int) : Any? {
        return getItemByPosition(position)
    }


}