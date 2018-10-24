package com.pasotti.matteo.wikiheroes.view.viewholder

import androidx.databinding.DataBindingUtil
import android.view.View
import com.pasotti.matteo.wikiheroes.databinding.ItemCharacterBinding
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.view.ui.ItemCharacterViewModel

class CharacterViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    //here we define actions which we handle
    interface Delegate {
        fun onItemClick(character: Character, view: View)
    }

    private lateinit var character: Character

    private val binding by lazy { DataBindingUtil.bind<ItemCharacterBinding>(view) }


    override fun bindData(data: Any?) {
        if (data is Character) {
            character = data
            drawUI()
        }

    }

    private fun drawUI() {

        binding?.setVModel(ItemCharacterViewModel(character))
        binding?.executePendingBindings()
    }

    override fun onClick(view: View?) {
        delegate.onItemClick(character, itemView)
    }

    override fun onLongClick(view: View?): Boolean {
        return false
    }


}