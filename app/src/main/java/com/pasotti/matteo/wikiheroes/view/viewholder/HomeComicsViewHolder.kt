package com.pasotti.matteo.wikiheroes.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.pasotti.matteo.wikiheroes.databinding.HomeItemComicBinding
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.view.ui.home.comics.HomeComicUIViewModel
import timber.log.Timber

class HomeComicsViewHolder (view: View, val delegate: Delegate) : BaseViewHolder(view) {

    private val binding by lazy { DataBindingUtil.bind<HomeItemComicBinding>(view) }

    private lateinit var item: Detail


    override fun bindData(data: Any?) {
        if (data is Detail) {
            item = data
            drawUI()
        }
    }

    private fun drawUI() {

        binding?.viewModel = HomeComicUIViewModel(item)
        val pages = binding!!.viewModel!!.getNumberPages()
        val title = binding!!.viewModel!!.getTitle()
        val image = binding!!.viewModel!!.getUrlImage()

        Timber.i("title : $title pages: $pages image: $image")

        binding?.executePendingBindings()
    }

    override fun onClick(v: View?) {
        delegate.onItemClick(item, itemView)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }

    interface Delegate {
        fun onItemClick(item: Detail, view: View)
    }
}