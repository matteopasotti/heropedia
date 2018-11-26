package com.pasotti.matteo.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.pasotti.matteo.wikiheroes.databinding.CreatorItemBinding
import com.pasotti.matteo.wikiheroes.models.Item

class CreatorViewHolder(view: View, val delegate: Delegate ,val items : List<Item>) : BaseViewHolder(view) {

    //here we define actions which we handle
    interface Delegate {
        fun onItemClick(creator: Item, view: View)
    }

    private lateinit var creator : Item

    private val binding by lazy { DataBindingUtil.bind<CreatorItemBinding>(view) }


    override fun bindData(data: Any?) {
        if(data is Item) {
            creator = data
            drawUI()
        }
    }

    private fun drawUI() {
        binding?.let {
            if(items.size > 1) {
                if(!isLastOne(creator)) {
                    binding?.creator = creator.name + " , "
                } else {
                    binding?.creator = creator.name
                }
            } else {
                binding?.creator = creator.name
            }

            binding?.executePendingBindings()
        }
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(creator, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    private fun isLastOne(creator : Item) : Boolean {
        if(items != null && items.isNotEmpty() && items.size > 1) {
            val index = items.indexOfFirst { it.equals(creator) }

            return index == (items.size - 1)
        }

        return false
    }
}