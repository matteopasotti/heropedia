package com.pasotti.matteo.wikiheroes.view.adapter

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.viewholder.BaseViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.DetailViewHolder

class DetailAdapter(val delegate : DetailViewHolder.Delegate) : BaseAdapter(){

    init {
        addItems(ArrayList<Detail>())
    }

    fun updateList( newItems : List<Detail>) {
        clearItems()
        addItems(newItems)
        notifyDataSetChanged()
    }

    /*fun dispatch(newList: List<Detail>) {
        val userDiffCallback = Utils.DetailsDiffCallback(newList, items as List<Detail>)
        val diffResult = DiffUtil.calculateDiff(userDiffCallback)
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }*/


    override fun layout(item: Any?): Int {
        return R.layout.item_small_image
    }

    override fun viewHolder(layout: Int, view: View): BaseViewHolder {
        return DetailViewHolder(view , delegate)
    }
}