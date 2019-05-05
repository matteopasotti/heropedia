package com.pasotti.matteo.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.pasotti.matteo.wikiheroes.databinding.ItemSeriesBinding
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.SearchObjectItem
import com.pasotti.matteo.wikiheroes.view.ui.search.SeriesUIViewModel

class SearchObjectSeriesViewHolder (view: View, val delegate: Delegate) : BaseViewHolder(view) {

    private val binding by lazy { DataBindingUtil.bind<ItemSeriesBinding>(view) }


    lateinit var series : Detail


    override fun bindData(data: Any?) {
        if( data is Detail && data.getType() == SearchObjectItem.TYPE_SERIES) {
            series = data
            bindUI()
        }
    }

    private fun bindUI() {
        binding?.viewModel = SeriesUIViewModel(series)
        binding?.executePendingBindings()
    }

    override fun onClick(v: View?) {
        return delegate.onSeriesClicked(series, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    interface Delegate {
        fun onSeriesClicked(item: Detail, view: View)
    }
}