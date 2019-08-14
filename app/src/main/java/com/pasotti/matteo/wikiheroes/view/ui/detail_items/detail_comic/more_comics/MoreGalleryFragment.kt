package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_comics

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.databinding.FragmentMoreGalleryBinding
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.MoreGalleryAdapter
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailItemActivity
import com.pasotti.matteo.wikiheroes.view.ui.seeall.series.SeriesSeeAllActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.MoreImageViewHolder
import org.koin.android.architecture.ext.viewModel


class MoreGalleryFragment : Fragment(), MoreImageViewHolder.Delegate {

    private val viewModel : MoreGalleryFragmentViewModel by viewModel()

    lateinit var binding : FragmentMoreGalleryBinding

    lateinit var adapter : MoreGalleryAdapter

    companion object {

        private const val RESOURCE_URI = "resourceUri"
        private const val TYPE = "type"
        private const val ID = "id"

        fun newInstance(id : String, resourceURI : String, type : String) : MoreGalleryFragment {
            val args: Bundle = Bundle()
            args.putString(RESOURCE_URI , resourceURI)
            args.putString(TYPE, type)
            args.putString(ID, id)
            val fragment = MoreGalleryFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater ,  R.layout.fragment_more_gallery, container, false)

        viewModel.resourceURI = this.arguments!!.getString(RESOURCE_URI)

        viewModel.id = arguments!!.getString(ID)

        viewModel.section = arguments!!.getString(TYPE)

        initView()

        return binding.root
    }

    private fun initView() {
        adapter = MoreGalleryAdapter(this)
        val linearLayoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        binding.listItems.layoutManager = linearLayoutManager
        binding.listItems.adapter = adapter

        binding.seeallLabel.setOnClickListener {
            val intent = Intent(activity, SeriesSeeAllActivity::class.java)
            intent.putExtra(SeriesSeeAllActivity.SERIES_ID, viewModel.seriesId)  //19679
            intent.putExtra(SeriesSeeAllActivity.SERIES_TITLE, viewModel.seriesTitle)
            intent.putExtra(SeriesSeeAllActivity.SERIES_IMAGE, viewModel.seriesThumbnail)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getItems(viewModel.resourceURI, arguments!!.getString(TYPE)).observe(this, Observer { it -> it?.let { processResponse(it) } })
        viewModel.getSeriesDetails().observe(this , Observer { it -> it?.let { saveSeriesDetail(it) } })
    }

    private fun processResponse(response: ApiResponse<DetailResponse>) {
        binding.progressBar.visibility = View.GONE
        if(response.isSuccessful && response.body != null) {
            var items : List<Detail> = Utils.checkDetailsImages(response.body.data.results)
            if(response.body.data.results.size != 1) {
                items = Utils.removeItemById(viewModel.id, items)
            }

            items.forEach {
                it.week = Utils.WEEK.none
            }

            renderDataState(items)
        }
    }

    private fun saveSeriesDetail(response: ApiResponse<DetailResponse>) {
        if(response.isSuccessful && response.body != null) {
            viewModel.seriesThumbnail = response.body.data.results[0].thumbnail?.path + "." + response.body.data.results[0].thumbnail?.extension
            viewModel.seriesTitle = response.body.data.results[0].title!!
        }
    }

    private fun renderDataState ( items : List<Detail>) {
        adapter.updateList(items)
    }

    override fun onItemClick(item: Detail, view: View) {
        if(item.id != viewModel.id.toInt()) {
            val intent = Intent(activity, DetailItemActivity::class.java)
            intent.putExtra(DetailItemActivity.INTENT_ITEM , item as Parcelable)
            intent.putExtra(DetailItemActivity.INTENT_SECTION, "Comics")
            startActivity(intent)
        }

    }
}