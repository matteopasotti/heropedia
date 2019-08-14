package com.pasotti.matteo.wikiheroes.view.ui.home.characters

import android.app.ActivityOptions
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
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.FragmentHomeCharactersBinding
import com.pasotti.matteo.wikiheroes.models.Character
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.CharactersAdapter
import com.pasotti.matteo.wikiheroes.view.ui.detail.DetailActivity
import com.pasotti.matteo.wikiheroes.view.ui.home.HomeActivityViewModel
import com.pasotti.matteo.wikiheroes.view.viewholder.CharacterViewHolder
import kotlinx.android.synthetic.main.item_character.view.*
import org.koin.android.architecture.ext.viewModel
import timber.log.Timber


class HomeCharactersFragment : Fragment(), CharacterViewHolder.Delegate {


    override fun onItemClick(character: Character, view: View) {
        Timber.i("Clicked Character ${character.name}")

        val img = Pair.create(view.image as View, resources.getString(R.string.transition_character_image))

        val name = Pair.create(view.name as View, resources.getString(R.string.transition_character_name))

        val options = ActivityOptions.makeSceneTransitionAnimation(activity, img, name)

        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.intent_character, character as Parcelable)
        startActivity(intent, options.toBundle())
    }

    private val viewModel : HomeActivityViewModel by viewModel()

    lateinit var binding: FragmentHomeCharactersBinding


    companion object {
        fun newInstance(): HomeCharactersFragment {
            val args = Bundle()
            val fragment = HomeCharactersFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_characters, container, false)

        if (savedInstanceState == null) {
            viewModel.firstTime = true
        }

        initView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun initView() {
        val linearLayout = androidx.recyclerview.widget.LinearLayoutManager(context)
        binding.rvCharacters.layoutManager = linearLayout
        viewModel.adapter = CharactersAdapter(this)
        binding.rvCharacters.adapter = viewModel.adapter
        binding.rvCharacters.addOnScrollListener(Utils.InfiniteScrollListener({
            viewModel.pageCounter += 1
            loadMore(viewModel.pageCounter)
        }, linearLayout))
    }

    private fun loadMore(page: Int) {
        viewModel.getCharacters(page)
    }

    private fun observeViewModel() {
        viewModel.characters.observe(this, Observer { response ->

            if(response != null) {
                renderDataState(Utils.checkCharactersImages(response))
            }

        })

        loadMore(viewModel.pageCounter++)
    }

    private fun renderDataState(items: List<Character>) {

        binding.progressBar.visibility = View.GONE

        viewModel.pageCounter = items[items.size - 1].page

        viewModel.adapter.updateList(items)

        if (viewModel.firstTime) {
            binding.rvCharacters.scheduleLayoutAnimation()
            viewModel.firstTime = false
        }

    }

    private fun renderErrorState(throwable: Throwable) {
        Timber.d(throwable)
        binding.progressBar.visibility = View.GONE
        //ErrorDialog.show(suppo.beginTransaction(), throwable.toString())
    }

}