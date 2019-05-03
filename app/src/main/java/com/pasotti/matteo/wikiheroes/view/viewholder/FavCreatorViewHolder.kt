package com.pasotti.matteo.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.databinding.FavCreatorRowBinding
import com.pasotti.matteo.wikiheroes.view.ui.home.desk.CreatorUIViewModel

class FavCreatorViewHolder (view: View, val delegate: Delegate) : BaseViewHolder(view){

    private lateinit var creator : Item

    private val binding by lazy { DataBindingUtil.bind<FavCreatorRowBinding>(view) }

    override fun bindData(data: Any?) {
        if(data is Item) {
            creator = data
            initUI()
        }
    }

    private fun initUI() {
        binding?.vModel = CreatorUIViewModel(creator)
        binding?.executePendingBindings()
    }

    override fun onClick(v: View?) {
        delegate.onCreatorClicked(creator, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    //here we define actions which we handle
    interface Delegate {
        fun onCreatorClicked(creator: Item, view: View)
    }


}