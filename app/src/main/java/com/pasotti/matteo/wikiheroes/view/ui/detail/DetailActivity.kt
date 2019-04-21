package com.pasotti.matteo.wikiheroes.view.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ActivityDetailBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.ui.gallery.HorizontalGalleryFragment
import dagger.android.AndroidInjection
import org.jetbrains.anko.backgroundDrawable
import javax.inject.Inject


@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {


    companion object {

        const val TAG = "DetailActivity"

        const val intent_character = "character"

        const val IMAGE_TYPE = "."
    }

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DetailActivityViewModel::class.java) }

    private val binding by lazy { DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail) }

    lateinit var gradientDrawable : GradientDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            supportPostponeEnterTransition()

            initUI()

            observeViewModel()
        }

    }

    private fun initUI() {

        getCharacterFromIntent()

        // ------------------     TOOLBAR  -------------------------------------------
        setSupportActionBar(binding.toolbarCharacterDetail.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = null
        binding.toolbarCharacterDetail.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.character = viewModel.character

        val requestOptions = RequestOptions()
        requestOptions.circleCrop()

        Glide.with(this)
                .asBitmap()
                .apply(requestOptions)
                .load(getImageUri())
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        //observeViewModel()
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        Palette.from(resource!!).generate {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                val dominantColor = it?.getDominantColor(resources.getColor(R.color.black, null))!!
                                val colors: IntArray = intArrayOf(resources.getColor(R.color.black, null), dominantColor)
                                viewModel.saveDominantColor(dominantColor)
                                gradientDrawable = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors)
                                gradientDrawable.cornerRadius = 0f
                                binding.viewGradient.backgroundDrawable = gradientDrawable

                            } else {
                                it?.getDominantColor(resources.getColor(R.color.black))
                            }
                        }

                        return false
                    }
                }).into(binding.circularImage)

        binding.toolbarCharacterDetail.heartCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                viewModel.addFavCharacter(viewModel.character)
            } else {
                viewModel.removeFavCharacter(viewModel.character)
            }
        }

        Utils.addFragmentToActivity(supportFragmentManager, HorizontalGalleryFragment.newInstance("Comics", viewModel.character.id, viewModel.character.name), binding.containerComics.id)

        Utils.addFragmentToActivity(supportFragmentManager, HorizontalGalleryFragment.newInstance("Series", viewModel.character.id, viewModel.character.name), binding.containerSeries.id)
    }


    private fun getImageUri(): String {
        return viewModel.character.thumbnail.path + IMAGE_TYPE + viewModel.character.thumbnail.extension
    }


    private fun getCharacterFromIntent() {
        viewModel.character = intent.extras.getParcelable(intent_character)

    }

    private fun observeViewModel() {
        viewModel.getFavCharacterById(viewModel.character.id).observe( this , Observer { response ->
            binding.toolbarCharacterDetail.heartCheckBox.isChecked = response != null
        })
    }

}