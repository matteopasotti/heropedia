package com.pasotti.matteo.wikiheroes.view.ui.detail

import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ActivityDetailBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.utils.CustomScrollView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject
import android.support.v4.view.ViewCompat.setTranslationY
import android.util.Log
import com.bumptech.glide.load.engine.DiskCacheStrategy


class DetailActivity : AppCompatActivity(), CustomScrollView.ScrollViewListener {

    companion object {

        const val TAG = "DetailActivity"

        const val intent_character = "character"

        const val IMAGE_TYPE = "."
    }

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val binding by lazy { DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail) }

    lateinit var char: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        supportPostponeEnterTransition()

        initUI()
    }

    private fun initUI() {

        getCharacterFromIntent()


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
                .into(binding.imageCharacter)

        binding.name.text = char.name


        binding.scrollView.setScrollViewListener(this)
    }


    private fun getImageUri(): String {
        return char.thumbnail.path + IMAGE_TYPE + char.thumbnail.extension
    }


    private fun getCharacterFromIntent() {
        char = intent.extras.getParcelable(intent_character)

    }

    override fun onScrollChanged(scrollView: CustomScrollView, x: Int, y: Int, oldx: Int, oldy: Int) {
        val image = binding.imageCharacter

        if (image != null) {
            Log.d(TAG , "onScrollChanged")
            image!!.setTranslationY(binding.scrollView.scrollY / 2.0f)
        }
    }
}