package com.pasotti.matteo.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.pasotti.matteo.wikiheroes.databinding.HomeItemComicBinding
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.view.ui.home.comics.HomeComicUIViewModel

class SearchObjectComicViewHolder(view: View, val delegate: Delegate) : BaseViewHolder(view) {

    private val bindingComics by lazy { DataBindingUtil.bind<HomeItemComicBinding>(view) }

    lateinit var comic: Detail

    override fun bindData(data: Any?) {
        if (data is Detail) {
            comic = data
            drawComicUI()
        }
    }

    private fun drawComicUI() {
        bindingComics?.viewModel = HomeComicUIViewModel(comic)
        bindingComics?.executePendingBindings()
    }

    override fun onClick(v: View?) {
        return delegate.onComicClicked(comic, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    interface Delegate {
        fun onComicClicked(item: Detail, view: View)
    }
}