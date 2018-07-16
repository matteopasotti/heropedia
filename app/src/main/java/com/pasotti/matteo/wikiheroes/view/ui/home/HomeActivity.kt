package com.pasotti.matteo.wikiheroes.view.ui.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.WindowManager
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.api.Status
import com.pasotti.matteo.wikiheroes.databinding.ActivityHomeBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.CharacterResponse
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.CharacterAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.CharactersAdapter
import dagger.android.AndroidInjection
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val binding by lazy { DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home) }

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HomeActivityViewModel::class.java) }

    lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home)


        initView()

        observeViewModel()
    }

    private fun initView() {
        val linearLayout = LinearLayoutManager(this)
        binding.rvCharacters.layoutManager = linearLayout
        binding.rvCharacters.addOnScrollListener(Utils.InfiniteScrollListener({ viewModel.loadMoreCharacters(adapter) }, linearLayout))
    }


    private fun observeViewModel() {
        viewModel.charactersLiveData.observe(this, Observer { it?.let { processResponse(it) } })
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
        //greetingTextView.setVisibility(View.GONE)
        //loadingIndicator.setVisibility(View.VISIBLE)
    }

    private fun renderDataState(items : List<Character>) {

        val response = items
        adapter = CharacterAdapter(items)
        binding.rvCharacters.adapter = adapter

    }

    private fun renderErrorState(throwable: Throwable) {
        Log.d("HomeActivity", "call ERROR response : " + throwable.toString())
        //Toast.makeText(this, R.string.greeting_error, Toast.LENGTH_SHORT).show()
    }
}
