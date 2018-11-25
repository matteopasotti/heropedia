package com.pasotti.matteo.wikiheroes.view.ui.creator

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Pair
import android.view.View
import android.view.WindowManager
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
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailComicActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.DetailViewHolder
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.item_small_image.view.*
import javax.inject.Inject

class CreatorDetailActivity : AppCompatActivity(), DetailViewHolder.Delegate {


    companion object {
        val TITLE_SECTION = "TITLE_SECTION"
        val CREATOR = "CREATOR"
    }

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(CreatorDetailViewModel::class.java) }

    private val binding by lazy { DataBindingUtil.setContentView<ActivityCreatorBinding>(this, R.layout.activity_creator) }

    lateinit var title_section : String

    lateinit var adapter : DetailAdapter

    private var firstTime = false

    private var page = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        firstTime = true

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //Comics, Series, Events
        title_section = intent.extras.getString(TITLE_SECTION)

        viewModel.creator = intent.getParcelableExtra(CREATOR) as Item
        viewModel.type = title_section

        initUI()

        observeViewModel()
    }

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
        adapter = DetailAdapter(this)
        binding.listCreatorItems.adapter = adapter

        binding.listCreatorItems.addOnScrollListener(Utils.InfiniteScrollListenerGrid({
            page = page + 1
            loadMore(page) }, layoutManager))
    }

    private fun observeViewModel() {
        viewModel.itemsLiveData.observe(this, Observer { it?.let { processResponse(it) } })
        loadMore(page++)
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

        adapter.updateList(items)
        if(firstTime) {
            binding.listCreatorItems.scheduleLayoutAnimation()
            firstTime = false
        }
    }

    private fun loadMore(page : Int) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.postPage(page)
    }

    override fun onItemClick(item: Detail, view: View) {
        val img = Pair.create(view.image_gallery as View, resources.getString(R.string.transition_detail_image))

        val txt = Pair.create(view.title_gallery as View, resources.getString(R.string.transition_detail_title))

        val options = ActivityOptions.makeSceneTransitionAnimation(this, img, txt)

        val intent = Intent(this, DetailComicActivity::class.java)
        intent.putExtra(DetailComicActivity.intent_comic , item as Parcelable)
        startActivity(intent, options.toBundle())
    }
}