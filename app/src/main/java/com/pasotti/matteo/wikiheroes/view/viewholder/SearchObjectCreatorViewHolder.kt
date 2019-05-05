package com.pasotti.matteo.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.pasotti.matteo.wikiheroes.databinding.ItemCreatorBinding
import com.pasotti.matteo.wikiheroes.models.Detail

class SearchObjectCreatorViewHolder (view: View, val delegate: Delegate) : BaseViewHolder(view) {

    private val binding by lazy { DataBindingUtil.bind<ItemCreatorBinding>(view) }

    lateinit var creator: Detail

    interface Delegate {
        fun onCreatorClicked(item: Detail, view: View)
    }


    override fun bindData(data: Any?) {
        if (data is Detail) {
            creator = data
            drawUI()
        }
    }

    private fun drawUI() {
        binding?.creator = creator.fullName
    }

    override fun onClick(v: View?) {
        return delegate.onCreatorClicked(creator, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }


}