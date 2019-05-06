package com.pasotti.matteo.wikiheroes.view.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.databinding.FragmentCharacterDetailBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.DetailAdapter
import com.pasotti.matteo.wikiheroes.view.viewholder.DetailViewHolder
import javax.inject.Inject

class FragmentCharacterDetail : Fragment(), DetailViewHolder.Delegate {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(FragmentCharacterDetailViewModel::class.java) }

    lateinit var binding : FragmentCharacterDetailBinding

    companion object {
        const val COMICS_SECTION = "Comics"
        const val SERIES_SECTION = "Series"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate( inflater , R.layout.fragment_character_detail , container , false)

        /*val args = FragmentCharacterDetailArgs.fromBundle(arguments!!)

        viewModel.character = args.character

        initUI()*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun initUI() {

        viewModel.comicsAdapter = DetailAdapter(this , COMICS_SECTION)
        viewModel.seriesAdapter = DetailAdapter(this , SERIES_SECTION)

        val comicsLayoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        binding.listComics.layoutManager = comicsLayoutManager
        binding.listComics.adapter = viewModel.comicsAdapter
        binding.comicsSectionTitle.text = COMICS_SECTION

        val seriesLayoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        binding.listSeries.layoutManager = seriesLayoutManager
        binding.listSeries.adapter = viewModel.seriesAdapter
        binding.seriesSectionTitle.text = SERIES_SECTION


        binding.comicsSectionSeeallLabel.setOnClickListener {
            //TODO put action
        }

        binding.seriesSectionSeeallLabel.setOnClickListener {
            //TODO put action
        }
    }


    private fun observeViewModel() {
        if(viewModel.character != null) {

            // CHECK IF IS IN DESK CHARACTERS
            viewModel.getFavCharacterById(viewModel.character!!.id).observe( this , Observer { response ->
                binding.toolbarCharacterDetail.heartCheckBox.isChecked = response != null
            })

            // GET THE LAST COMICS
            viewModel.getItems(viewModel.character!!.id , COMICS_SECTION).observe(this, Observer { it?.let { processComicsResponse(it) } })

            // GET THE LAST SERIES
            viewModel.getItems(viewModel.character!!.id , SERIES_SECTION).observe(this, Observer { it?.let { processSeriesResponse(it) } })
        }

    }

    private fun processComicsResponse(response: ApiResponse<DetailResponse>) {
        if(response.isSuccessful && response.body != null && !response.body.data.results.isNullOrEmpty()) {

            binding.comicsSection.visibility = View.VISIBLE
            val comics : MutableList<Detail> = mutableListOf()
            response.body.data.results.forEach {
                it.week = Utils.WEEK.none
                comics.add(it)
            }

            viewModel.comicsAdapter.updateList(comics)
        } else {
            binding.comicsSection.visibility = View.GONE
        }
    }

    private fun processSeriesResponse(response: ApiResponse<DetailResponse>) {
        if(response.isSuccessful && response.body != null && !response.body.data.results.isNullOrEmpty()) {
            binding.seriesSection.visibility = View.VISIBLE
            val series : MutableList<Detail> = mutableListOf()
            response.body.data.results.forEach {
                it.week = Utils.WEEK.none
                series.add(it)
            }

            viewModel.seriesAdapter.updateList(series)
        } else {
            binding.comicsSection.visibility = View.GONE
        }
    }


    override fun onItemClick(item: Detail, view: View, section : String?) {
        //TODO put action
        // OPEN THE DETAIL OF A COMIC OR SERIES
    }
}