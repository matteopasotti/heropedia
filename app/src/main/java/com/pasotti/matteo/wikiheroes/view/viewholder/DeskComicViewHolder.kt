package com.pasotti.matteo.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.pasotti.matteo.wikiheroes.databinding.DeskComicRowBinding
import com.pasotti.matteo.wikiheroes.models.Detail

class DeskComicViewHolder (view: View, val delegate: Delegate, val thisWeek : String) : BaseViewHolder(view) {

    private lateinit var item: Detail

    private val binding by lazy { DataBindingUtil.bind<DeskComicRowBinding>(view) }


    //here we define actions which we handle
    interface Delegate {
        fun onItemClick(item: Detail, view: View)
    }

    override fun bindData(data: Any?) {
        if( data is Detail ) {
            item = data
            bindUI()
        }
    }

    private fun bindUI() {
        binding?.detail = item
        binding?.url = item.thumbnail?.path + "." + item.thumbnail?.extension

        if(thisWeek == getPublishedDate(item)) {
            binding?.clockImage?.visibility = View.VISIBLE
        } else {
            binding?.clockImage?.visibility = View.GONE
        }


        binding?.executePendingBindings()
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(item, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    fun getPublishedDate( item : Detail) : String {
        if (item.dates != null && item.dates.size > 0) {
            for (date in item.dates) {
                if (date.type.equals("onsaleDate")) {
                    return date.date.toString()
                }
            }
        }

        return ""
    }


}