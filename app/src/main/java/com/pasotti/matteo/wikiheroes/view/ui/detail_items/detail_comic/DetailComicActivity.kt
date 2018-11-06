package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ComicDetailBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import dagger.android.AndroidInjection
import javax.inject.Inject

class DetailComicActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val binding by lazy { DataBindingUtil.setContentView<ComicDetailBinding>( this, R.layout.comic_detail )}

    lateinit var item : Detail


    companion object {

        const val TAG = "DetailComicActivity"

        const val intent_comic = "comic"

        const val intent_character_id = "chatacter_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        supportPostponeEnterTransition()

        initUI()


    }


    private fun initUI() {

        item = intent.extras.getParcelable(intent_comic)
        binding.viewModel = DetailComicUIViewModel(item)

        setSupportActionBar(binding.toolbarComicDetail)

        if(supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        binding.toolbarComicDetail.setNavigationOnClickListener { onBackPressed() }

        Glide.with(this)
                .load(getImageUri())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        //observeViewModel()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        //observeViewModel()
                        return false
                    }
                })
                .into(binding.imageDetail)

        binding.titleDetail.setText(item.title)
    }

    private fun getImageUri(): String {
        return item.thumbnail?.path + "." + item.thumbnail?.extension
    }
}