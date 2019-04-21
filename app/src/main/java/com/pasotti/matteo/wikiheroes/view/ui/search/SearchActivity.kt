package com.pasotti.matteo.wikiheroes.view.ui.search

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Pair
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ActivitySearchBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.CharactersAdapter
import com.pasotti.matteo.wikiheroes.view.ui.detail.DetailActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.CharacterViewHolder
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.item_character.view.*
import timber.log.Timber
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), CharacterViewHolder.Delegate {

    @Inject
    lateinit var viewModelFactory : AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(SearchActivityViewModel::class.java) }

    private val binding by lazy { DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search) }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) //This line initialise our dependencies
        super.onCreate(savedInstanceState)

        initUI()
    }

    private fun initUI() {
        binding.backArrow.setOnClickListener {
            onBackPressed()
        }


        val linearLayout = androidx.recyclerview.widget.LinearLayoutManager(this)
        binding.listResults.layoutManager = linearLayout
        viewModel.adapter = CharactersAdapter(this)
        binding.listResults.adapter = viewModel.adapter


        binding.searchEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.searchCharacter(binding.searchEdit.text.toString())
                        .observe(this, Observer { response ->
                            if (response != null && response.isSuccessful) {
                                if (response.body!!.data != null && !response.body!!.data.results.isNullOrEmpty()) {
                                    binding.progressBar.visibility = View.GONE
                                    viewModel.adapter.updateList(response.body!!.data.results)
                                } else {
                                    // no results
                                    binding.progressBar.visibility = View.GONE
                                    Utils.showAlert(this, "No results found.")
                                }
                            } else {
                                renderErrorState(response.error)
                            }
                        })
                true
            } else {
                false
            }
        }
    }

    private fun processRespose( results : List<Character>) {

    }

    private fun renderErrorState(throwable: Throwable?) {
        binding.progressBar.visibility = View.GONE
        throwable?.message?.let { Utils.showAlert(this, it) }
    }

    override fun onItemClick(character: Character, view: View) {
        Timber.i("Clicked Character ${character.name}" )

        val img = Pair.create(view.image as View, resources.getString(R.string.transition_character_image))

        val name = Pair.create(view.name as View, resources.getString(R.string.transition_character_name))

        val options = ActivityOptions.makeSceneTransitionAnimation(this, img , name)

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.intent_character , character as Parcelable)
        startActivity(intent, options.toBundle())
    }

}