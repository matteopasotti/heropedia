package com.pasotti.matteo.wikiheroes.view.ui.search

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Pair
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ActivitySearchBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.models.SearchObjectItem
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.SearchAdapter
import com.pasotti.matteo.wikiheroes.view.ui.creator.CreatorDetailActivity
import com.pasotti.matteo.wikiheroes.view.ui.detail.DetailActivity
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailItemActivity
import com.pasotti.matteo.wikiheroes.view.ui.person.PersonDetailActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.item_character.view.*
import kotlinx.android.synthetic.main.item_creator.view.*
import timber.log.Timber
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), SearchObjectCharacterViewHolder.Delegate, SearchObjectComicViewHolder.Delegate, SearchObjectSeriesViewHolder.Delegate, SearchObjectCreatorViewHolder.Delegate {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

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
        viewModel.adapter = SearchAdapter(this, this, this, this)
        binding.listResults.adapter = viewModel.adapter

        binding.optionCharacterTextview.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.searchOption = "Character"
                if (viewModel.searchOption != null && viewModel.adapter.items.size > 0) {
                    viewModel.switchTab = true
                    search()
                }
            }
        }

        binding.optionComicTextview.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.searchOption = "Comic"
                if (viewModel.searchOption != null && viewModel.adapter.items.size > 0) {
                    viewModel.switchTab = true
                    search()
                }
            }
        }

        binding.optionSeriesTextview.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.searchOption = "Series"
                if (viewModel.searchOption != null && viewModel.adapter.items.size > 0) {
                    viewModel.switchTab = true
                    search()
                }
            }
        }

        binding.optionPersonTextview.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.searchOption = "Person"
                if (viewModel.searchOption != null && viewModel.adapter.items.size > 0) {
                    viewModel.switchTab = true
                    search()
                }
            }
        }

        //INFINITE SCROLL LISTENER
        binding.listResults.addOnScrollListener(Utils.InfiniteScrollListener({
            viewModel.increasePage()
            search()
        }, linearLayout))


        binding.searchEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.resetLists()
                viewModel.searchText = binding.searchEdit.text.toString()
                closeKeyboard(binding.searchEdit)
                search()
                true
            } else {
                false
            }
        }
    }

    private fun search() {
        if (viewModel.searchOption != null) {
            binding.progressBar.visibility = View.VISIBLE
            when (viewModel.searchOption) {

                "Character" -> {
                    searchCharacter(viewModel.searchText)
                }

                "Comic" -> {
                    searchComic(viewModel.searchText)
                }

                "Series" -> {
                    searchSeries(viewModel.searchText)
                }

                "Person" -> {
                    searchCreator(viewModel.searchText)
                }

            }
        } else {
            Utils.showAlert(this, "Select what you want to search")
        }
    }

    private fun searchComic(searchText: String) {
        if(viewModel.comics == null || (viewModel.comicsPageCounter != 0 && !viewModel.switchTab)) {
            viewModel.searchComics(searchText)
                    .observe(this, Observer { response ->
                        if (response != null && response.isSuccessful) {
                            if (!response.body!!.data.results.isNullOrEmpty()) {

                                val items: MutableList<Detail> = mutableListOf()
                                response.body.data.results.forEach {
                                    it.week = Utils.WEEK.none
                                    items.add(it)
                                }

                                binding.progressBar.visibility = View.GONE
                                if(viewModel.comics == null) {
                                    viewModel.comics = mutableListOf()
                                    viewModel.comics!!.addAll(items)
                                    viewModel.adapter.createList(items)
                                } else {
                                    viewModel.comics!!.addAll(items)
                                    viewModel.adapter.addNewItems(items)
                                }

                                viewModel.switchTab = false
                                viewModel.increaseOffset()
                            } else {
                                // no results
                                binding.progressBar.visibility = View.GONE
                                Utils.showAlert(this, "No results found.")
                            }
                        } else {
                            renderErrorState(response.error)
                        }
                    })
        } else {
            binding.progressBar.visibility = View.GONE
            viewModel.adapter.createList(viewModel.comics!!)
            viewModel.switchTab = false
        }

    }

    private fun searchCharacter(searchText: String) {
        if(viewModel.characters == null || (viewModel.charactersPageCounter != 0 && !viewModel.switchTab)) {
            viewModel.searchCharacter(searchText)
                    .observe(this, Observer { response ->
                        if (response != null && response.isSuccessful) {
                            if (!response.body!!.data.results.isNullOrEmpty()) {
                                binding.progressBar.visibility = View.GONE

                                val items: MutableList<SearchObjectItem> = mutableListOf()
                                response.body.data.results.forEach {
                                    items.add(it)
                                }

                                if(viewModel.characters == null) {
                                    viewModel.characters = mutableListOf()
                                    viewModel.characters!!.addAll(items)
                                    viewModel.adapter.createList(items)
                                } else {
                                    viewModel.characters!!.addAll(items)
                                    viewModel.adapter.addNewItems(items)
                                }
                                viewModel.switchTab = false
                                viewModel.increaseOffset()
                                //viewModel.adapter.createList(response.body!!.data.results)
                            } else {
                                // no results
                                binding.progressBar.visibility = View.GONE
                                Utils.showAlert(this, "No results found.")
                            }
                        } else {
                            renderErrorState(response.error)
                        }
                    })
        } else {
            binding.progressBar.visibility = View.GONE
            viewModel.adapter.createList(viewModel.characters!!)
            viewModel.switchTab = false
        }

    }

    private fun searchCreator( searchText: String ) {
        if(viewModel.persons == null || (viewModel.personPageCounter != 0 && !viewModel.switchTab)) {
            viewModel.searchCreator(searchText).observe( this , Observer { response ->
                if (response != null && response.isSuccessful) {
                    if (!response.body!!.data.results.isNullOrEmpty()) {
                        binding.progressBar.visibility = View.GONE

                        val items: MutableList<SearchObjectItem> = mutableListOf()
                        response.body.data.results.forEach {
                            it.week = Utils.WEEK.none
                            items.add(it)
                        }

                        if(viewModel.persons == null) {
                            viewModel.persons = mutableListOf()
                            viewModel.persons!!.addAll(items)
                            viewModel.adapter.createList(items)
                        } else {
                            viewModel.persons!!.addAll(items)
                            viewModel.adapter.addNewItems(items)
                        }
                        viewModel.switchTab = false
                        viewModel.increaseOffset()
                    } else {
                        // no results
                        binding.progressBar.visibility = View.GONE
                        Utils.showAlert(this, "No results found.")
                    }
                } else {
                    renderErrorState(response.error)
                }
            })
        } else {
            binding.progressBar.visibility = View.GONE
            viewModel.adapter.createList(viewModel.persons!!)
            viewModel.switchTab = false
        }

    }

    private fun searchSeries( searchText: String ) {
        if(viewModel.series == null || (viewModel.seriesPageCounter != 0 && !viewModel.switchTab)) {
            viewModel.searchSeries(searchText).observe( this , Observer { response ->
                if (response != null && response.isSuccessful) {
                    if (!response.body!!.data.results.isNullOrEmpty()) {


                        binding.progressBar.visibility = View.GONE
                        viewModel.increaseOffset()

                        val items: MutableList<Detail> = mutableListOf()
                        response.body!!.data.results.forEach {
                            it.week = Utils.WEEK.none
                            items.add(it)
                        }

                        if(viewModel.series == null) {
                            viewModel.series = mutableListOf()
                            viewModel.series!!.addAll(items)
                            viewModel.adapter.createList(items)
                        } else {
                            viewModel.series!!.addAll(items)
                            viewModel.adapter.addNewItems(items)
                        }

                        viewModel.switchTab = false

                    } else {
                        // no results
                        binding.progressBar.visibility = View.GONE
                        Utils.showAlert(this, "No results found.")
                    }
                } else {
                    renderErrorState(response.error)
                }
            })
        } else {
            binding.progressBar.visibility = View.GONE
            viewModel.adapter.createList(viewModel.series!!)
            viewModel.switchTab = false
        }

    }


    private fun renderErrorState(throwable: Throwable?) {
        binding.progressBar.visibility = View.GONE
        throwable?.message?.let { Utils.showAlert(this, it) }
    }

    override fun onCharacterClicked(character: Character, view: View) {
        Timber.i("Clicked Character ${character.name}")

        val img = Pair.create(view.image as View, resources.getString(R.string.transition_character_image))

        val name = Pair.create(view.name as View, resources.getString(R.string.transition_character_name))

        val options = ActivityOptions.makeSceneTransitionAnimation(this, img, name)

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.intent_character, character as Parcelable)
        startActivity(intent, options.toBundle())
    }


    override fun onSeriesClicked(item: Detail, view: View) {
        val intent = Intent(this, DetailItemActivity::class.java)
        intent.putExtra(DetailItemActivity.INTENT_ITEM, item as Parcelable)
        intent.putExtra(DetailItemActivity.INTENT_SECTION, "Series")
        startActivity(intent)
    }


    override fun onComicClicked(item: Detail, view: View) {
        val intent = Intent(this, DetailItemActivity::class.java)
        intent.putExtra(DetailItemActivity.INTENT_ITEM, item as Parcelable)
        intent.putExtra(DetailItemActivity.INTENT_SECTION, "Comics")
        startActivity(intent)
    }

    override fun onCreatorClicked(item : Detail, view: View) {
        val txt = Pair.create(view.creator_name as View, resources.getString(R.string.transition_creator_name))
        val options = ActivityOptions.makeSceneTransitionAnimation(this, txt)

        val intent = Intent(this, PersonDetailActivity::class.java)

        //val intent = Intent(this, CreatorDetailActivity::class.java)

        val creator = Item(item.resourceURI , item.fullName!!, null , null)
        intent.putExtra(PersonDetailActivity.CREATOR , creator as Parcelable)
        intent.putExtra(PersonDetailActivity.IMAGE , item.thumbnail?.path + "." +item.thumbnail?.extension)
        //intent.putExtra(CreatorDetailActivity.TITLE_SECTION, "Comics")
        //startActivity(intent, options.toBundle())
        startActivity(intent)
    }

    private fun closeKeyboard(view : View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken , 0)
    }

}