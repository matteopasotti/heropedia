package com.pasotti.matteo.wikiheroes.view.ui

import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.api.Status
import com.pasotti.matteo.wikiheroes.databinding.ActivityHomeBinding
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.utils.ErrorDialog
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.CharactersAdapter
import com.pasotti.matteo.wikiheroes.view.viewholder.CharacterViewHolder
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject
import android.content.Intent
import android.os.Parcelable
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.view.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.item_character.view.*
import android.util.Pair as UtilPair


class HomeActivity : AppCompatActivity(), CharacterViewHolder.Delegate {



    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val binding by lazy { DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home) }

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HomeActivityViewModel::class.java) }

    private val adapter by lazy { CharactersAdapter(this) }

    private var page = 0

    private var firstTime = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        firstTime = true


        initView()

        observeViewModel()
    }

    private fun initView() {
        val linearLayout = LinearLayoutManager(this)
        binding.rvCharacters.layoutManager = linearLayout
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.addOnScrollListener(Utils.InfiniteScrollListener({
            page = page + 1
            loadMore(page) }, linearLayout))
    }

    private fun loadMore(page : Int) {
        viewModel.postPage(page)
    }


    private fun observeViewModel() {
        viewModel.charactersLiveData.observe(this, Observer { it?.let { processResponse(it) } })
        loadMore(page++)
    }

    private fun processResponse(response: Resource<List<Character>>) {
        when (response.status) {
            Status.LOADING -> renderLoadingState()

            Status.SUCCESS -> renderDataState(response.data!!)

            Status.ERROR -> renderErrorState(response.error!!)
        }
    }

    private fun renderLoadingState() {

        Log.d("HomeActivity", "call LOADING")
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun renderDataState(items : List<Character>) {

        page = items.get(items.size-1).page

        binding.progressBar.visibility = View.GONE

        adapter.updateList(items)

        if(firstTime) {
            binding.rvCharacters.scheduleLayoutAnimation()
            firstTime = false
        }

    }

    private fun renderErrorState(throwable: Throwable) {
        binding.progressBar.visibility = View.GONE
        ErrorDialog.show(this, throwable.toString())
        Log.d("HomeActivity", "call ERROR response : " + throwable.toString())
    }

    override fun onItemClick(character: Character, view: View) {
        Timber.i("Clicked Character ${character.name}" )

        val options = ActivityOptions.makeSceneTransitionAnimation(this,
                UtilPair.create(view.image as View, resources.getString(R.string.transition_character_image)),
                UtilPair.create(view.name as View, resources.getString(R.string.transition_character_name)))

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.intent_character , character as Parcelable)
        startActivity(intent, options.toBundle())
    }

}
