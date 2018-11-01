package com.pasotti.matteo.wikiheroes.view.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.api.Status
import com.pasotti.matteo.wikiheroes.databinding.ActivityDetailBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.utils.ErrorDialog
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.ui.gallery.HorizontalGalleryFragment
import dagger.android.AndroidInjection
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

    private val binding by lazy { DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail) }

    lateinit var char: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        supportPostponeEnterTransition()

        initUI()

        observeViewModel()
    }

    private fun initUI() {

        getCharacterFromIntent()

        binding.character = char

        setSupportActionBar(binding.toolbarCharacterDetail)

        binding.toolbarCharacterDetail.setNavigationOnClickListener { onBackPressed() }

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }


        binding.collapsingToolbar.title = char.name
        val tf = ResourcesCompat.getFont(this, R.font.product_sans_bold)
        binding.collapsingToolbar.setCollapsedTitleTypeface(tf)
        binding.collapsingToolbar.setExpandedTitleTypeface(tf)


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

    }

    private fun observeViewModel() {
        viewModel.getComicsByCharacterId(char.id).observe(this, Observer { it?.let { processResponse(it) } })

    }

    private fun processResponse(response: ApiResponse<DetailResponse>) {
        if(response.isSuccessful && response.body != null) {
            renderDataState(Utils.checkDetailsImages(response.body.data.results))
        }
    }

    private fun renderDataState ( items : List<Detail>) {
        initComicsView(items)
    }

    private fun renderLoadingState() {

        Log.d("HomeActivity", "call LOADING")
        //binding.progressBar.visibility = View.VISIBLE
    }

    private fun renderErrorState(throwable: Throwable) {
        //binding.progressBar.visibility = View.GONE
        ErrorDialog.show(this, throwable.toString())
        Log.d("HomeActivity", "call ERROR response : " + throwable.toString())
    }

    private fun initComicsView(items : List<Detail>) {
        if(items != null && items.size > 0) {
            Utils.addFragmentToActivity(supportFragmentManager , HorizontalGalleryFragment.newInstance("Comics" , ArrayList(items)), binding.containerComics.id)
        }
    }


    private fun getImageUri(): String {
        return char.thumbnail.path + IMAGE_TYPE + char.thumbnail.extension
    }


    private fun getCharacterFromIntent() {
        char = intent.extras.getParcelable(intent_character)

    }

}