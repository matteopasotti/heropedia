package com.pasotti.matteo.wikiheroes.view.ui.creator

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.databinding.ActivityCreatorBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.DetailAdapter
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailItemActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.DetailViewHolder
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.item_small_image.view.*
import javax.inject.Inject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CreatorDetailActivity : AppCompatActivity(), DetailViewHolder.Delegate {
    override fun onItemClick(item: Detail, view: View, section: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        const val TITLE_SECTION = "TITLE_SECTION"
        const val CREATOR = "CREATOR"
    }

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(CreatorDetailViewModel::class.java) }

    private val binding by lazy { DataBindingUtil.setContentView<ActivityCreatorBinding>(this, R.layout.activity_creator) }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if( savedInstanceState == null ) {
            viewModel.firstTime = true
            //Comics, Series, Events
            viewModel.creator = intent.getParcelableExtra(CREATOR) as Item
            viewModel.type = intent.extras.getString(TITLE_SECTION)

            initUI()

            observeViewModel()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {

        setSupportActionBar(binding.toolbarCreatorDetail.toolbar)
        if(supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }

        binding.toolbarCreatorDetail.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.creatorNameText.text = viewModel.creator.name
        binding.titleCreatorSection.text = " : " + viewModel.type

        //LIST
        val layoutManager = GridLayoutManager(this , 3)
        binding.listCreatorItems.layoutManager = layoutManager
        viewModel.adapter = DetailAdapter(this , "Section")
        binding.listCreatorItems.adapter = viewModel.adapter

        binding.listCreatorItems.addOnScrollListener(Utils.InfiniteScrollListenerGrid({
            viewModel.pageCounter += 1
            loadMore(viewModel.pageCounter) }, layoutManager))
    }

    private fun observeViewModel() {
        viewModel.itemsLiveData.observe(this, Observer { it?.let { processResponse(it) } })
        loadMore(viewModel.pageCounter++)
    }

    private fun processResponse(response: ApiResponse<DetailResponse>) {
        binding.progressBar.visibility = View.GONE
        if(response.isSuccessful && response.body != null) {
            viewModel.increaseOffset()
            renderDataState(response.body.data.results)
        } else {
            //show error
            //todo
        }
    }

    private fun renderDataState( items : List<Detail>) {

        val newItems : MutableList<Detail> = mutableListOf()
        items.forEach {
            it.week = Utils.WEEK.none
            newItems.add(it)
        }
        viewModel.adapter.updateList(newItems)
        if(viewModel.firstTime) {
            binding.listCreatorItems.scheduleLayoutAnimation()
            viewModel.firstTime = false
        }
    }

    private fun loadMore(page : Int) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.postPage(page)
    }

    /*override fun onItemClick(item: Detail, view: View) {
        val img = Pair.create(view.image_gallery as View, resources.getString(R.string.transition_detail_image))

        val txt = Pair.create(view.title_gallery as View, resources.getString(R.string.transition_detail_title))

        val options = ActivityOptions.makeSceneTransitionAnimation(this, img, txt)

        val intent = Intent(this, DetailItemActivity::class.java)
        intent.putExtra(DetailItemActivity.INTENT_ITEM , item as Parcelable)
        intent.putExtra(DetailItemActivity.INTENT_SECTION, viewModel.type)
        startActivity(intent)
    }*/
}