package com.pasotti.matteo.wikiheroes.view.ui.person

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ActivityCreatorDetailBinding
import com.pasotti.matteo.wikiheroes.view.adapter.CreatorViewPagerAdapter
import org.koin.android.architecture.ext.viewModel


class PersonDetailActivity : AppCompatActivity() {

    companion object {

        const val TAG = "DetailActivity"

        const val CREATOR = "creator"

        const val IMAGE = "image"

        const val IMAGE_TYPE = "."
    }

    private val viewModel : PersonDetailActivityViewModel by viewModel()

    private val binding by lazy { DataBindingUtil.setContentView<ActivityCreatorDetailBinding>(this, R.layout.activity_creator_detail) }

    private lateinit var pagerAdapter: CreatorViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()

        observeViewModel()
    }

    private fun initUI() {

        setSupportActionBar(binding.toolbarPersonDetail.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = null
        binding.toolbarPersonDetail.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

       viewModel.image = intent.extras.getString(IMAGE)
        viewModel.creator = intent.extras.getParcelable(CREATOR)

        binding.creatorName.text = viewModel.creator.name

        val requestOptions = RequestOptions()
        requestOptions.circleCrop()

        Glide.with(this)
                .asBitmap()
                .apply(requestOptions)
                .load(viewModel.image)
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

        binding.toolbarPersonDetail.heartCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                viewModel.creator.image = viewModel.image
                viewModel.saveCreator(viewModel.creator)
            } else {
                viewModel.removeCreator(viewModel.creator)
            }
        }
    }

    private fun getImageUri(): String {
        return viewModel.detail.thumbnail?.path + IMAGE_TYPE + viewModel.detail.thumbnail?.extension
    }

    private fun selectTab(index: Int) {
        binding.viewpager.setCurrentItem(index, true)
    }

    private fun observeViewModel() {
        viewModel.getFavCreator(viewModel.creator).observe( this , Observer { response ->
            binding.toolbarPersonDetail.heartCheckBox.isChecked = response != null
        })
    }
}