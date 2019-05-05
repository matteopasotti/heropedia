package com.pasotti.matteo.wikiheroes.view.ui.home.desk

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.FragmentHomeDeskBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.*
import com.pasotti.matteo.wikiheroes.utils.ItemOffsetDecoration
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.FavCharacterAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.DeskComicsAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.DeskSeriesAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.FavCreatorAdapter
import com.pasotti.matteo.wikiheroes.view.ui.detail.DetailActivity
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailItemActivity
import com.pasotti.matteo.wikiheroes.view.ui.person.PersonDetailActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.FavCharacterViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.DeskComicViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.DeskSeriesViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.FavCreatorViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fav_character_row.view.*
import timber.log.Timber
import javax.inject.Inject

class HomeDeskFragment : Fragment() , DeskComicViewHolder.Delegate , FavCharacterViewHolder.Delegate , DeskSeriesViewHolder.Delegate , FavCreatorViewHolder.Delegate {

    lateinit var binding : FragmentHomeDeskBinding

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HomeDeskViewModel::class.java) }



    companion object {
        fun newInstance() : HomeDeskFragment {
            val args = Bundle()
            val fragment = HomeDeskFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater ,  R.layout.fragment_home_desk, container, false)

        initView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun initView() {

        val linearLayoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager2 = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager3 = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager4 = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.adapterCharacters = FavCharacterAdapter(this)
        binding.listCharactersItems.layoutManager = linearLayoutManager2
        val itemDecoration =  ItemOffsetDecoration(context!!, R.dimen.default_padding)
        binding.listCharactersItems.addItemDecoration(itemDecoration)
        binding.listCharactersItems.adapter = viewModel.adapterCharacters

        viewModel.adapter = DeskComicsAdapter(this , viewModel.getThisWeekDate())

        binding.listShopItems.layoutManager = linearLayoutManager
        binding.listShopItems.adapter = viewModel.adapter

        viewModel.adapterSeries = DeskSeriesAdapter(this)
        binding.listSeries.layoutManager = linearLayoutManager3
        binding.listSeries.adapter = viewModel.adapterSeries

        viewModel.adapterPeople = FavCreatorAdapter(this)
        binding.listPeople.layoutManager = linearLayoutManager4
        binding.listPeople.adapter = viewModel.adapterPeople
    }

    fun observeViewModel() {
        viewModel.getComicsFromShop().observe(this , Observer { response -> processDeskComicsResponse(response) })

        viewModel.getFavCharacters().observe( this , Observer { response ->  processFavCharactersResponse(response)})

        viewModel.getSeriesFromDesk().observe( this , Observer { response ->
            processDeskSeriesResponse(response)
        })

        viewModel.getPeopleFromDesk().observe( this , Observer { response ->  processFavPeopleResponse(response)})
    }

    private fun processFavCharactersResponse( response : List<FavCharacter>) {
        if(response.isNotEmpty()) {
            binding.charactersSection.visibility = View.VISIBLE
            binding.text.visibility = View.GONE
            var listItems : MutableList<Character> = mutableListOf()

            response.forEach {
                listItems.add(it.character)
            }
            viewModel.adapterCharacters.updateList(listItems)
        } else {
            binding.charactersSection.visibility = View.GONE
        }
    }

    private fun processFavPeopleResponse( response : List<Item>) {
        if(response.isNotEmpty()) {
            binding.deskPersonsSection.visibility = View.VISIBLE
            binding.text.visibility = View.GONE
            viewModel.adapterPeople.updateItems(response)
        } else {
            binding.deskPersonsSection.visibility = View.GONE
        }
    }

    private fun processDeskSeriesResponse( response : List<DeskItem>) {
        if(response.isNotEmpty()) {
            binding.deskSeriesSection.visibility = View.VISIBLE
            binding.text.visibility = View.GONE
            var listItems : MutableList<Detail> = mutableListOf()

            response.forEach {
                it.item.week = Utils.WEEK.none
                listItems.add(it.item)
            }
            viewModel.adapterSeries.updateList(listItems)
        } else {
            binding.deskSeriesSection.visibility = View.GONE
        }
    }


    private fun processDeskComicsResponse(response : List<DeskItem>) {
        if(response.isNotEmpty()) {
            binding.shopSection.visibility = View.VISIBLE
            binding.text.visibility = View.GONE
            var listItems : MutableList<Detail> = mutableListOf()

            response.forEach {
                it.item.week = Utils.WEEK.none
                listItems.add(it.item)
            }
            viewModel.adapter.updateList(listItems)
        } else {
            binding.shopSection.visibility = View.GONE
        }
    }

    override fun onItemClick(item: Detail, view: View) {
        val intent = Intent(activity, DetailItemActivity::class.java)
        intent.putExtra(DetailItemActivity.INTENT_ITEM , item as Parcelable)
        intent.putExtra(DetailItemActivity.INTENT_SECTION , "Comics")
        startActivity(intent)
    }

    override fun onItemClick(character: Character, view: View) {
        Timber.i("Clicked Character ${character.name}" )

        val img = Pair.create(view.character_image as View, resources.getString(R.string.transition_character_image))

        //val name = Pair.create(view.name as View, resources.getString(R.string.transition_character_name))

        val options = ActivityOptions.makeSceneTransitionAnimation(activity, img)

        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.intent_character , character as Parcelable)
        startActivity(intent , options.toBundle())
    }

    override fun onSeriesClicked(item: Detail, view: View) {
        val intent = Intent(activity, DetailItemActivity::class.java)
        intent.putExtra(DetailItemActivity.INTENT_ITEM , item as Parcelable)
        intent.putExtra(DetailItemActivity.INTENT_SECTION , "Series")
        startActivity(intent)
    }

    override fun onCreatorClicked(creator: Item, view: View) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getCreatorDetail(creator).observe( this , Observer { response ->
            binding.progressBar.visibility = View.GONE
            if(response.isSuccessful && response != null && response.body!!.data.results.isNotEmpty()) {
                val detail = response.body.data.results[0]
                val intent = Intent( activity , PersonDetailActivity::class.java)
                intent.putExtra(PersonDetailActivity.CREATOR , creator as Parcelable)
                intent.putExtra(PersonDetailActivity.IMAGE , detail.thumbnail?.path + "." + detail.thumbnail?.extension)
                startActivity(intent)
            }

        })
    }
}