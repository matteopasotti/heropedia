package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_comics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.databinding.FragmentMoreGalleryBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.MoreGalleryAdapter
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailComicActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.MoreImageViewHolder
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MoreGalleryFragment : Fragment(), MoreImageViewHolder.Delegate {


    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MoreGalleryFragmentViewModel::class.java)}

    lateinit var binding : FragmentMoreGalleryBinding

    lateinit var adapter : MoreGalleryAdapter

    companion object {

        private val ITEM = "item"
        private val TYPE = "type"
        private val ID = "id"

        fun newInstance(id : String, item : Item, type : String) : MoreGalleryFragment {
            val args: Bundle = Bundle()
            args.putParcelable(ITEM , item)
            args.putString(TYPE, type)
            args.putString(ID, id)
            val fragment = MoreGalleryFragment()
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

        binding = DataBindingUtil.inflate(inflater ,  R.layout.fragment_more_gallery, container, false)

        viewModel.item = arguments!!.getParcelable(ITEM)

        viewModel.id = arguments!!.getString(ID)

        initView()

        return binding.root
    }

    private fun initView() {
        adapter = MoreGalleryAdapter(this)
        val linearLayoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        binding.listItems.layoutManager = linearLayoutManager
        binding.listItems.adapter = adapter
    }

    private fun observeViewModel() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getItems(viewModel.item, arguments!!.getString(TYPE)).observe(this, Observer { it?.let { processResponse(it) } })

    }

    private fun processResponse(response: ApiResponse<DetailResponse>) {
        binding.progressBar.visibility = View.GONE
        if(response.isSuccessful && response.body != null) {
            var items : List<Detail> = Utils.checkDetailsImages(response.body.data.results)
            if(response.body.data.results.size != 1) {
                items = Utils.removeItemById(viewModel.id, items)
            }

            renderDataState(items)
        }
    }

    private fun renderDataState ( items : List<Detail>) {
        adapter.updateList(items)
    }

    override fun onItemClick(item: Detail, view: View) {
        if(item.id != viewModel.id.toInt()) {
            val intent = Intent(activity, DetailComicActivity::class.java)
            intent.putExtra(DetailComicActivity.intent_comic , item as Parcelable)
            startActivity(intent)
        }

    }
}