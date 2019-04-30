package com.pasotti.matteo.wikiheroes.view.ui.person

import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ActivityCreatorDetailBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.view.adapter.CreatorViewPagerAdapter
import com.pasotti.matteo.wikiheroes.view.ui.person.comics.CreatorComicsFragment
import com.pasotti.matteo.wikiheroes.view.ui.person.series.CreatorSeriesFragment
import dagger.android.AndroidInjection
import org.jetbrains.anko.backgroundDrawable
import javax.inject.Inject

class PersonDetailActivity : AppCompatActivity() {

    companion object {

        const val TAG = "DetailActivity"

        const val CREATOR = "creator"

        const val IMAGE = "image"

        const val IMAGE_TYPE = "."
    }

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(PersonDetailActivityViewModel::class.java) }

    private val binding by lazy { DataBindingUtil.setContentView<ActivityCreatorDetailBinding>(this, R.layout.activity_creator_detail) }

    private lateinit var pagerAdapter: CreatorViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) //This line initialise our dependencies
        super.onCreate(savedInstanceState)

        initUI()
    }

    private fun initUI() {

        setSupportActionBar(binding.toolbarCharacterDetail.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = null
        binding.toolbarCharacterDetail.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

       val image = intent.extras.getString(IMAGE)
        viewModel.creator = intent.extras.getParcelable(CREATOR)

        binding.creatorName.text = viewModel.creator.name

        val requestOptions = RequestOptions()
        requestOptions.circleCrop()

        Glide.with(this)
                .asBitmap()
                .apply(requestOptions)
                .load(image)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        //observeViewModel()
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        return false
                    }
                }).into(binding.circularImage)


        pagerAdapter = CreatorViewPagerAdapter(supportFragmentManager, viewModel.creator)
        binding.viewpager.adapter = pagerAdapter
        binding.viewpager.offscreenPageLimit = (pagerAdapter.count - 1)

        binding.tabs.setupWithViewPager(binding.viewpager)

        val tabOne = LayoutInflater.from(this).inflate(R.layout.custom_tab_creator_comics, null)
        binding.tabs.getTabAt(0)!!.customView = tabOne

        val tabTwo = LayoutInflater.from(this).inflate(R.layout.custom_tab_creator_series, null)
        binding.tabs.getTabAt(1)!!.customView = tabTwo

        selectTab(0)
    }

    private fun getImageUri(): String {
        return viewModel.detail.thumbnail?.path + IMAGE_TYPE + viewModel.detail.thumbnail?.extension
    }

    private fun selectTab(index: Int) {
        binding.viewpager.setCurrentItem(index, true)
    }
}