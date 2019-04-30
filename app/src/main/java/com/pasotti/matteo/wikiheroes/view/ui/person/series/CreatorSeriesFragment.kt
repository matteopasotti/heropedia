package com.pasotti.matteo.wikiheroes.view.ui.person.series

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.databinding.FragmentCreatorSeriesBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.HomeComicsAdapter
import com.pasotti.matteo.wikiheroes.view.ui.person.comics.CreatorFragmentsViewModel
import com.pasotti.matteo.wikiheroes.view.viewholder.HomeComicsViewHolder
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class CreatorSeriesFragment : Fragment(),  HomeComicsViewHolder.Delegate {
    override fun onItemClick(item: Detail, view: View) {
    }

    lateinit var binding : FragmentCreatorSeriesBinding

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(CreatorFragmentsViewModel::class.java) }

    companion object {

        private const val CREATOR = "creator"

        fun newInstance( creator  : Item) : CreatorSeriesFragment {
            val args = Bundle()
            val fragment = CreatorSeriesFragment()
            fragment.arguments = args
            args.putParcelable(CREATOR , creator)
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater ,  R.layout.fragment_creator_series, container, false)

        viewModel.creator = arguments?.getParcelable(CREATOR)!!

        initView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun observeViewModel() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getSeriesByCreatorId(viewModel.getCreatorId(viewModel.creator)).observe( this , Observer { response ->
            processResponse(response)
        })
    }

    private fun processResponse(response: ApiResponse<DetailResponse>) {
        if(response.isSuccessful) {
            renderDataState(response.body?.data!!.results)
        } else {
            renderErrorState(response.error)
        }
    }

    private fun renderDataState( items : List<Detail>) {
        binding.progressBar.visibility = View.GONE

        if(items.isNotEmpty()) {
            val newItems : MutableList<Detail> = mutableListOf()
            items.forEach {
                it.week = Utils.WEEK.none
                newItems.add(it)
            }
            viewModel.adapter.updateList(newItems)
        } else {
            binding.txtNoItems.visibility = View.VISIBLE
        }


    }

    private fun renderErrorState(throwable: Throwable?) {
        binding.progressBar.visibility = View.GONE
        throwable?.message?.let { Utils.showAlert(activity, it) }
    }

    private fun initView() {
        viewModel.adapter = HomeComicsAdapter(this)

        binding.rvCreatorSeries.adapter = viewModel.adapter
    }
}