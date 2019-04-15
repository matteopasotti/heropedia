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
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.FavCharacter
import com.pasotti.matteo.wikiheroes.models.ShopItem
import com.pasotti.matteo.wikiheroes.utils.ItemOffsetDecoration
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.DetailAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.FavCharacterAdapter
import com.pasotti.matteo.wikiheroes.view.adapter.ShopComicAdapter
import com.pasotti.matteo.wikiheroes.view.ui.detail.DetailActivity
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailComicActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.DetailViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.FavCharacterViewHolder
import com.pasotti.matteo.wikiheroes.view.viewholder.ShopComicViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fav_character_row.view.*
import kotlinx.android.synthetic.main.item_character.view.*
import timber.log.Timber
import javax.inject.Inject

class HomeDeskFragment : Fragment() , ShopComicViewHolder.Delegate , FavCharacterViewHolder.Delegate {


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
        binding.text.text = "Desk Fragment"

        val linearLayoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        val linearLayoutManager2 = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.adapterCharacters = FavCharacterAdapter(this)
        binding.listCharactersItems.layoutManager = linearLayoutManager2
        val itemDecoration =  ItemOffsetDecoration(context!!, R.dimen.default_padding)
        binding.listCharactersItems.addItemDecoration(itemDecoration)
        binding.listCharactersItems.adapter = viewModel.adapterCharacters

        viewModel.adapter = ShopComicAdapter(this , viewModel.getThisWeekDate())

        binding.listShopItems.layoutManager = linearLayoutManager
        binding.listShopItems.adapter = viewModel.adapter
    }

    fun observeViewModel() {
        viewModel.getItemsFromShop().observe(this , Observer { response -> processShopItemsResponse(response) })

        viewModel.getFavCharacters().observe( this , Observer { response ->  processFavCharactersResponse(response)})
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


    private fun processShopItemsResponse( response : List<ShopItem>) {
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
        val intent = Intent(activity, DetailComicActivity::class.java)
        intent.putExtra(DetailComicActivity.INTENT_COMIC , item as Parcelable)
        intent.putExtra(DetailComicActivity.INTENT_SECTION , "Comics")
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
}