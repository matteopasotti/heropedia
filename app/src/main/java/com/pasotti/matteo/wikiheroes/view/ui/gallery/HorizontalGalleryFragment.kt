package com.pasotti.matteo.wikiheroes.view.ui.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.FragmentHorizontalGalleryBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.view.adapter.HorizontalGalleryAdapter
import com.pasotti.matteo.wikiheroes.view.viewholder.HorizontalImageViewHolder
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HorizontalGalleryFragment : Fragment() , HorizontalImageViewHolder.Delegate {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    lateinit var binding : FragmentHorizontalGalleryBinding

    lateinit var adapter : HorizontalGalleryAdapter

    companion object {

        private val TITLE = "title"
        private val ITEMS = "items"

        fun newInstance( title : String ,  items : ArrayList<Detail>) : HorizontalGalleryFragment {
            val args: Bundle = Bundle()
            args.putString(TITLE , title)
            args.putParcelableArrayList(ITEMS , items)
            val fragment = HorizontalGalleryFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater ,  R.layout.fragment_horizontal_gallery, container, false)

        initView()

        return binding.root
    }

    private fun initView() {

        adapter = HorizontalGalleryAdapter(this)

        /*
        LinearLayoutManager layoutManager
    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
         */
        val linearLayoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        binding.listItems.layoutManager = linearLayoutManager
        binding.listItems.adapter = adapter

        adapter.updateList(arguments!!.getParcelableArrayList(ITEMS))

        binding.sectionTitle.text = arguments!!.getString(TITLE)

    }

    override fun onItemClick(item: Detail, view: View) {
    }

}