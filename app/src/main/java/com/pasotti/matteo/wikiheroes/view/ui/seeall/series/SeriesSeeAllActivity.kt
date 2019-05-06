package com.pasotti.matteo.wikiheroes.view.ui.seeall.series

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.databinding.ActivitySeriesSeeAllBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.DetailAdapter
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailItemActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.DetailViewHolder
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.item_small_image.view.*
import org.jetbrains.anko.backgroundDrawable
import javax.inject.Inject

class SeriesSeeAllActivity : AppCompatActivity(), DetailViewHolder.Delegate {
    override fun onItemClick(item: Detail, view: View, section: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this , viewModelFactory).get(SeriesSeeAllViewModel::class.java) }

    private val binding by lazy { DataBindingUtil.setContentView<ActivitySeriesSeeAllBinding>(this, R.layout.activity_series_see_all) }

    companion object {
        const val SERIES_ID = "series_id"
        const val SERIES_TITLE = "series_title"
        const val SERIES_IMAGE = "series_image"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {

            initUI()
            observeViewModel()
        }
    }

    private fun initUI() {

        viewModel.firstTime = true

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        viewModel.seriesId = intent.extras.getString(SERIES_ID)
        viewModel.seriesImage = intent.extras.getString(SERIES_IMAGE)
        viewModel.seriesTitle = intent.extras.getString(SERIES_TITLE)

        binding.testText.text = viewModel.seriesTitle
        val requestOptions = RequestOptions()
        requestOptions.circleCrop()

        Glide.with(this)
                .load(viewModel.seriesImage)
                .apply(requestOptions)
                .into(binding.circleImage)

        //setGradientBackground(viewModel.getStandardBackgroundColor())


        //LIST
        val layoutManager = GridLayoutManager(this , 3)
        binding.listSerieComics.layoutManager = layoutManager
        viewModel.adapter = DetailAdapter(this , "Section")
        binding.listSerieComics.adapter = viewModel.adapter

        binding.listSerieComics.addOnScrollListener(Utils.InfiniteScrollListenerGrid({
            viewModel.pageCounter += 1
            loadMore(viewModel.pageCounter) }, layoutManager))
    }

    private fun observeViewModel() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.itemsLiveData.observe(this , Observer { it?.let { processResponse(it) } })
        loadMore(viewModel.pageCounter++)
    }

    private fun loadMore(page : Int) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.postPage(page)
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


        items.forEach { it.week = Utils.WEEK.none }

        viewModel.adapter.updateList(items)
        if(viewModel.firstTime) {
            binding.listSerieComics.scheduleLayoutAnimation()
            viewModel.firstTime = false
        }
    }


    private fun setGradientBackground(dominantColor : Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val colors: IntArray = intArrayOf(resources.getColor(R.color.black, null), dominantColor)
            val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors)
            gradientDrawable.cornerRadius = 0f
            binding.root.backgroundDrawable = gradientDrawable
        } else {

        }

    }

    /*override fun onItemClick(item: Detail, view: View) {
        val img = Pair.create(view.image_gallery as View, resources.getString(R.string.transition_detail_image))

        val txt = Pair.create(view.title_gallery as View, resources.getString(R.string.transition_detail_title))

        val options = ActivityOptions.makeSceneTransitionAnimation(this, img, txt)

        val intent = Intent(this, DetailItemActivity::class.java)
        intent.putExtra(DetailItemActivity.INTENT_ITEM , item as Parcelable)
        intent.putExtra(DetailItemActivity.INTENT_SECTION, "Comics")
        startActivity(intent)
    }*/

}