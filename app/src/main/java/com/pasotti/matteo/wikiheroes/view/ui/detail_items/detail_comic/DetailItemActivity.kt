package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic

import android.animation.Animator
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ComicDetailBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_comics.MoreGalleryFragment
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_info.MoreInfoFragment
import dagger.android.AndroidInjection
import javax.inject.Inject

class DetailItemActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DetailItemViewModel::class.java) }

    private val binding by lazy { DataBindingUtil.setContentView<ComicDetailBinding>(this, R.layout.comic_detail) }

    companion object {

        const val TAG = "DetailItemActivity"

        const val INTENT_ITEM = "resourceURI"

        const val INTENT_SECTION = "section"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportPostponeEnterTransition()
            initUI()

            observeViewModel()
        }
    }


    private fun initUI() {

        viewModel.item = intent.extras.getParcelable(INTENT_ITEM)
        viewModel.section = intent.extras.getString(INTENT_SECTION)
        binding.viewModel = DetailComicUIViewModel(viewModel.item, intent.getStringExtra(INTENT_SECTION))

        setSupportActionBar(binding.toolbarComicDetail.toolbar)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }

        binding.shopButton.addIcon.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // add to shop list
                viewModel.addToShop()
            } else {
                // remove from shop list
                viewModel.removeFromShop()
            }
        }

        binding.toolbarComicDetail.toolbar.setNavigationOnClickListener { onBackPressed() }

        Glide.with(this)
                .load(getImageUri())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(binding.imageDetail)

        binding.titleDetail.text = viewModel.item.title

        binding.imageDetail.setOnClickListener {
            supportFragmentManager
                    .beginTransaction()
                    .addSharedElement(binding.imageDetail, "transition_avatar_dest")
                    .replace(binding.container.id, DetailImageFragment.newInstance(getImageUri()), "DetailImageFragment")
                    .addToBackStack(null)
                    .commit()

        }

        Utils.addFragmentToActivity(supportFragmentManager, MoreInfoFragment.newInstance(ArrayList(viewModel.item.creators?.items), viewModel.section), binding.containerMoreDetails.id)


        if (binding!!.viewModel!!.getMoreComicsVisibility() == View.VISIBLE) {
            // SHOW OTHER ITEMS OF THAT COLLECTION
            var resourceUri : String = ""
            if(viewModel.section == "Series") {
                resourceUri = viewModel.item.resourceURI
            } else if( viewModel.section == "Comics") {
                resourceUri = viewModel.item.series!!.resourceURI
            }

            Utils.addFragmentToActivity(supportFragmentManager, MoreGalleryFragment.newInstance(viewModel.item.id.toString(), resourceUri, viewModel.section), binding.containerMoreComics.id)
        }
    }

    private fun getImageUri(): String {
        return viewModel.item.thumbnail?.path + "." + viewModel.item.thumbnail?.extension
    }


    private fun observeViewModel() {
        viewModel.getItemFromShop().observe(this, Observer { response ->
            binding.shopButton.addIcon.isChecked = response != null
        })

        if(viewModel.section == "Series") {
            viewModel.getSeriesDetailById(viewModel.item.id).observe( this , Observer { response ->
                if(response.isSuccessful) {

                } else {
                    renderErrorState(response.error)
                }
            })
        }
    }

    private fun renderErrorState(throwable: Throwable?) {
        binding.progressBar.visibility = View.GONE
        throwable?.message?.let { Utils.showAlert(this, it) }
    }
}