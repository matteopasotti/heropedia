package com.pasotti.matteo.wikiheroes.view.ui.home.comics

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
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.FragmentHomeComicsBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.HomeComicsAdapter
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailComicActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.HomeComicsViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.home_item_comic.view.*
import kotlinx.android.synthetic.main.item_small_image.view.*
import javax.inject.Inject

class HomeComicsFragment : Fragment() , HomeComicsViewHolder.Delegate {


    lateinit var binding : FragmentHomeComicsBinding

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HomeComicsViewModel::class.java) }



    companion object {
        fun newInstance() : HomeComicsFragment {
            val args = Bundle()
            val fragment = HomeComicsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater ,  R.layout.fragment_home_comics, container, false)

        if ( savedInstanceState == null ) {
            viewModel.firstTime = true
        }

        initView()

        return binding.root
    }

    private fun initView() {
        val linearLayout = androidx.recyclerview.widget.LinearLayoutManager(context)
        binding.rvComics.layoutManager = linearLayout
        viewModel.adapter = HomeComicsAdapter(this)
        binding.rvComics.adapter = viewModel.adapter
        binding.rvComics.addOnScrollListener(Utils.InfiniteScrollListener({
            viewModel.pageCounter += 1
            loadMore(viewModel.pageCounter) }, linearLayout))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun loadMore(page : Int) {
        viewModel.postPage(page)
    }

    private fun observeViewModel() {
        viewModel.getComicsOfTheWeek().observe( this , Observer { response ->
            if(response.isSuccessful) {
                viewModel.adapter.updateList(response.body!!.data.results)
            }
        })

        //loadMore(viewModel.pageCounter++)
    }
    

    override fun onItemClick(item: Detail, view: View) {
        val img = Pair.create(view.image as View, resources.getString(R.string.transition_detail_image))

        val txt = Pair.create(view.title_detail as View, resources.getString(R.string.transition_detail_title))

        val options = ActivityOptions.makeSceneTransitionAnimation(activity, img, txt)

        val intent = Intent(activity, DetailComicActivity::class.java)
        intent.putExtra(DetailComicActivity.INTENT_COMIC , item as Parcelable)
        intent.putExtra(DetailComicActivity.INTENT_SECTION , "Comics")
        startActivity(intent, options.toBundle())
    }
}