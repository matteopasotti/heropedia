package com.pasotti.matteo.wikiheroes.view.ui.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.databinding.FragmentHorizontalGalleryBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.HorizontalGalleryAdapter
import com.pasotti.matteo.wikiheroes.view.viewholder.HorizontalImageViewHolder
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HorizontalGalleryFragment : Fragment() , HorizontalImageViewHolder.Delegate {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HorizontalGalleryViewModel::class.java)}

    lateinit var binding : FragmentHorizontalGalleryBinding

    lateinit var adapter : HorizontalGalleryAdapter

    companion object {

        private val TITLE = "title"
        private val CHARACTER_ID = "character_id"

        fun newInstance( title : String , characterId : Int) : HorizontalGalleryFragment {
            val args: Bundle = Bundle()
            args.putString(TITLE , title)
            args.putInt(CHARACTER_ID, characterId)
            val fragment = HorizontalGalleryFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater ,  R.layout.fragment_horizontal_gallery, container, false)

        initView()

        return binding.root
    }

    private fun initView() {

        adapter = HorizontalGalleryAdapter(this)
        val linearLayoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        binding.listItems.layoutManager = linearLayoutManager
        binding.listItems.adapter = adapter
        binding.sectionTitle.text = arguments!!.getString(TITLE)

    }

    private fun observeViewModel() {
        viewModel.getItems(arguments!!.getInt(CHARACTER_ID), arguments!!.getString(TITLE)).observe(this, Observer { it?.let { processResponse(it) } })

    }

    private fun processResponse(response: ApiResponse<DetailResponse>) {
        if(response.isSuccessful && response.body != null) {
            renderDataState(Utils.checkDetailsImages(response.body.data.results))
        }
    }

    private fun renderDataState ( items : List<Detail>) {
        adapter.updateList(items)
    }


    override fun onItemClick(item: Detail, view: View) {
    }

}