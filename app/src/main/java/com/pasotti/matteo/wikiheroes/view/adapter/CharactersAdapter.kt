package com.pasotti.matteo.wikiheroes.view.adapter

import android.arch.lifecycle.ViewModel
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ItemCharacterBinding
import com.pasotti.matteo.wikiheroes.view.ui.home.ItemCharacterViewModel
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.CharacterResponse

class CharactersAdapter(var characterResponse: CharacterResponse) : RecyclerView.Adapter<CharactersAdapter.ItemCharacterViewHolder>() {


    override fun getItemCount(): Int = characterResponse.data.results.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersAdapter.ItemCharacterViewHolder {
        val itemCharacterBinding = DataBindingUtil.inflate<ItemCharacterBinding>(LayoutInflater.from(parent.context), R.layout.item_character, parent, false)

        return CharactersAdapter.ItemCharacterViewHolder(itemCharacterBinding)
    }

    override fun onBindViewHolder(holder: ItemCharacterViewHolder, position: Int) {
        holder.bindItemCharacter(characterResponse.data.results[position])
    }


    class ItemCharacterViewHolder(val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.cardView) {
        fun bindItemCharacter(character: Character) {
            var viewmodel = ItemCharacterViewModel(character)
           // binding.cardView.setOnClickListener({ viewmodel.openDetailActivity() })
            binding.vModel = viewmodel

        }

    }
}