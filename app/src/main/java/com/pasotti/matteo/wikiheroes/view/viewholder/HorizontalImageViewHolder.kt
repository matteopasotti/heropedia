package com.pasotti.matteo.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.pasotti.matteo.wikiheroes.databinding.ItemSmallImageBinding
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.Item

class HorizontalImageViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    //here we define actions which we handle
    interface Delegate {
        fun onItemClick(item: Detail, view: View)
    }


    private lateinit var item: Detail

    private val binding by lazy { DataBindingUtil.bind<ItemSmallImageBinding>(view) }


    override fun bindData(data: Any?) {
        if (data is Detail) {
            item = data
            drawUI()
        }
    }

    private fun drawUI() {
        binding?.url = item.thumbnail.path + "." + item.thumbnail.extension
        binding?.executePendingBindings()
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(item, itemView)
    }


    override fun onLongClick(v: View?): Boolean {
        return false
    }
}