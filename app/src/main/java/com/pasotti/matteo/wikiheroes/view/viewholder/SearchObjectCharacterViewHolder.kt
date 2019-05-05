package com.pasotti.matteo.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.pasotti.matteo.wikiheroes.databinding.ItemCharacterBinding
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.view.ui.home.ItemCharacterViewModel

class SearchObjectCharacterViewHolder (view: View, val delegate: Delegate) : BaseViewHolder(view) {


    private val bindingCharacters by lazy { DataBindingUtil.bind<ItemCharacterBinding>(view) }

    lateinit var character: Character


    override fun bindData(data: Any?) {
        if( data is Character) {
            character = data
            bindUI()
        }
    }

    private fun bindUI() {
        bindingCharacters.apply {
            bindingCharacters?.vModel = ItemCharacterViewModel(character)
            bindingCharacters?.executePendingBindings()
        }
    }

    override fun onClick(v: View?) {
        return delegate.onCharacterClicked(character , itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    interface Delegate {
        fun onCharacterClicked(character : Character, view : View)
    }
}