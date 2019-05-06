package com.pasotti.matteo.wikiheroes.view.ui.detail_items

import android.graphics.drawable.Drawable
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.databinding.FragmentItemDetailBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.CreatorRowAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.CreatorsAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.MoreGalleryAdapter
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailComicUIViewModel
import com.pasotti.matteo.wikiheroes.view.viewholder.CreatorViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.MoreImageViewHolder
import javax.inject.Inject

class FragmentDetailItem : Fragment(), CreatorViewHolder.Delegate, MoreImageViewHolder.Delegate {
    override fun onCreatorClicked(creator: Item, view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClick(item: Detail, view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(FragmentDetailItemViewModel::class.java) }

    lateinit var binding : FragmentItemDetailBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate( inflater , R.layout.fragment_item_detail , container , false)

        val args = FragmentDetailItemArgs.fromBundle(arguments!!)

        viewModel.item = args.item

        viewModel.section = args.section

        viewModel.creators = ArrayList(viewModel.item.creators?.items)

        initUI()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun initUI() {

        binding.viewModel = DetailComicUIViewModel(viewModel.item , viewModel.section)

        binding.titleDetail.text = viewModel.item.title

        binding.shopButton.addIcon.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // add to shop list
                viewModel.addToShop()
            } else {
                // remove from shop list
                viewModel.removeFromShop()
            }
        }

        Glide.with(this)
                .load(viewModel.getImageUri(viewModel.item))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        //supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        //supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(binding.imageDetail)

        // MORE INFO SECTION
        viewModel.creatorAdapter = CreatorRowAdapter(this , viewModel.getCreatorsMap(viewModel.creators))
        binding.layoutMoreInfo.listRowsCreators.adapter = viewModel.creatorAdapter

        // MORE COMICS SECTION
        viewModel.moreComicsAdapter = MoreGalleryAdapter(this)
        val linearLayoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        binding.layoutMoreComics.listItems.layoutManager = linearLayoutManager
        binding.layoutMoreComics.listItems.adapter = viewModel.moreComicsAdapter

        if (binding!!.viewModel!!.getMoreComicsVisibility() == View.VISIBLE) {
            // SHOW OTHER ITEMS OF THAT COLLECTION
            viewModel.resourceURI = ""
            if(viewModel.section == "Series") {
                viewModel.resourceURI = viewModel.item.resourceURI
            } else if( viewModel.section == "Comics") {
                viewModel.resourceURI = viewModel.item.series!!.resourceURI
            }
        }
    }


    private fun observeViewModel() {

        viewModel.getItemFromShop().observe(this, Observer { response ->
            binding.shopButton.addIcon.isChecked = response != null
        })

        // GET MORE COMICS
        viewModel.getItems(viewModel.resourceURI, viewModel.section).observe(this, Observer { it -> it?.let { processMoreItemsResponse(it) } })
    }

    private fun processMoreItemsResponse(response: ApiResponse<DetailResponse>) {
        if(response.isSuccessful && response.body != null) {
            var items : List<Detail> = Utils.checkDetailsImages(response.body.data.results)
            if(response.body.data.results.size != 1) {
                items = Utils.removeItemById(viewModel.id, items)
            }

            items.forEach {
                it.week = Utils.WEEK.none
            }

            viewModel.moreComicsAdapter.updateList(items)
        }
    }



}