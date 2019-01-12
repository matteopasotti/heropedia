package com.pasotti.matteo.wikiheroes.view.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ActivityDetailCircularBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.utils.ErrorDialog
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.ui.gallery.HorizontalGalleryFragment
import dagger.android.AndroidInjection
import org.jetbrains.anko.backgroundDrawable
import javax.inject.Inject


class DetailActivity : AppCompatActivity() {


    companion object {

        const val TAG = "DetailActivity"

        const val intent_character = "character"

        const val IMAGE_TYPE = "."
    }

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DetailActivityViewModel::class.java) }

    private val binding by lazy { DataBindingUtil.setContentView<ActivityDetailCircularBinding>(this, R.layout.activity_detail_circular) }

    private lateinit var char: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        if(savedInstanceState == null) {
            supportPostponeEnterTransition()

            initUI()
        }

    }

    private fun initUI() {

        getCharacterFromIntent()

        binding.character = char

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
                                val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, colors)
                                gradientDrawable.cornerRadius = 0f

                                binding.viewGradient.backgroundDrawable = gradientDrawable


                            } else {
                                it?.getDominantColor(resources.getColor(R.color.black))
                            }
                        }

                        return false
                    }
                }).into(binding.circularImage)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }


        Utils.addFragmentToActivity(supportFragmentManager, HorizontalGalleryFragment.newInstance("Comics", char.id), binding.containerComics.id)

        Utils.addFragmentToActivity(supportFragmentManager, HorizontalGalleryFragment.newInstance("Series", char.id), binding.containerSeries.id)
    }

    private fun renderLoadingState() {

        Log.d(TAG, "call LOADING")
        //binding.progressBar.visibility = View.VISIBLE
    }

    private fun renderErrorState(throwable: Throwable) {
        //binding.progressBar.visibility = View.GONE
        ErrorDialog.show(supportFragmentManager.beginTransaction(), throwable.toString())
        Log.d(TAG, "call ERROR response : " + throwable.toString())
    }


    private fun getImageUri(): String {
        return char.thumbnail.path + IMAGE_TYPE + char.thumbnail.extension
    }


    private fun getCharacterFromIntent() {
        char = intent.extras.getParcelable(intent_character)

    }

}