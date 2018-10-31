package com.pasotti.matteo.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.pasotti.matteo.wikiheroes.databinding.ItemSmallImageBinding
import com.pasotti.matteo.wikiheroes.models.Item

class HorizontalImageViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    //here we define actions which we handle
    interface Delegate {
        fun onItemClick(item: Item, view: View)
    }


    private lateinit var item: Item

    private val binding by lazy { DataBindingUtil.bind<ItemSmallImageBinding>(view) }


    override fun bindData(data: Any?) {
        if (data is Item) {
            item = data
            drawUI()
        }
    }

    private fun drawUI() {

        //character.thumbnail.path + IMAGE_TYPE + character.thumbnail.extension
        binding?.url = item.resourceURI
        binding?.executePendingBindings()
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(item, itemView)
    }


    override fun onLongClick(v: View?): Boolean {
        return false
    }
}