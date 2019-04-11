package com.pasotti.matteo.wikiheroes.view.ui.home.desk

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.FragmentHomeDeskBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.ShopItem
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.DetailAdapter
import com.pasotti.matteo.wikiheroes.view.viewholder.DetailViewHolder
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class HomeDeskFragment : Fragment() , DetailViewHolder.Delegate {



    lateinit var binding : FragmentHomeDeskBinding

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HomeDeskViewModel::class.java) }



    companion object {
        fun newInstance() : HomeDeskFragment {
            val args = Bundle()
            val fragment = HomeDeskFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater ,  R.layout.fragment_home_desk, container, false)

        initView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun initView() {
        binding.text.text = "Desk Fragment"

        viewModel.adapter = DetailAdapter(this)
        val linearLayoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        binding.listShopItems.layoutManager = linearLayoutManager
        binding.listShopItems.adapter = viewModel.adapter
    }

    fun observeViewModel() {
        viewModel.getItemsFromShop().observe(this , Observer { response -> processShopItemsResponse(response) })
    }


    private fun processShopItemsResponse( response : List<ShopItem>) {
        if(response.isNotEmpty()) {
            binding.shopSection.visibility = View.VISIBLE

            var listItems : MutableList<Detail> = mutableListOf()

            response.forEach {
                it.item.week = Utils.WEEK.none
                listItems.add(it.item)
            }
            viewModel.adapter.updateList(listItems)
        } else {

        }
    }

    override fun onItemClick(item: Detail, view: View) {
    }
}