package com.pasotti.matteo.wikiheroes.view.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.pasotti.matteo.wikiheroes.databinding.ItemSliderBinding

class SliderAdapter (private val mContext: Context, private val images: List<String>) : PagerAdapter() {


    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(mContext)
        val binding = ItemSliderBinding.inflate(inflater, container, false)
        binding.url = images[position]

        container.addView(binding.root)
        return binding.root

    }

    override fun destroyItem(collection: ViewGroup, position: Int, view : Any) {
        collection.removeView(view as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return images.size
    }
}