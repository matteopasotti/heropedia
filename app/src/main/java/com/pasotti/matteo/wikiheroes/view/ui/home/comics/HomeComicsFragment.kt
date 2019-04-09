package com.pasotti.matteo.wikiheroes.view.ui.home.comics

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Paint
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
import com.pasotti.matteo.wikiheroes.api.Resource
import com.pasotti.matteo.wikiheroes.api.Status
import com.pasotti.matteo.wikiheroes.databinding.FragmentHomeComicsBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.HomeComicsAdapter
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailComicActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.HomeComicsViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.home_item_comic.view.*
import timber.log.Timber
import javax.inject.Inject
import android.R



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

            //todo develop the logic for week field
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



        //SELECTION OF THE WEEK
        binding.previousWeek.setOnClickListener {
            //lastWeek
            if(viewModel.currentWeek != Utils.WEEK.lastWeek) {
                //load items of lastWeek
                binding.thisWeek.paintFlags = UNDERLINE_TEXT_FLAG.inv()
                binding.nextWeek.paintFlags = UNDERLINE_TEXT_FLAG.inv()
                binding.previousWeek.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                viewModel.currentWeek = Utils.WEEK.lastWeek
                changeWeek()
            }
        }


        binding.thisWeek.setOnClickListener {
            if(viewModel.currentWeek != Utils.WEEK.thisWeek) {
                //load items of thisWeek
                binding.previousWeek.paintFlags = UNDERLINE_TEXT_FLAG.inv()
                binding.nextWeek.paintFlags = UNDERLINE_TEXT_FLAG.inv()
                binding.thisWeek.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                viewModel.currentWeek = Utils.WEEK.thisWeek
                changeWeek()
            }
        }


        binding.nextWeek.setOnClickListener {
            if(viewModel.currentWeek != Utils.WEEK.nextWeek) {
                //load items of nextWeek
                binding.thisWeek.paintFlags = UNDERLINE_TEXT_FLAG.inv()
                binding.previousWeek.paintFlags = UNDERLINE_TEXT_FLAG.inv()
                binding.nextWeek.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                viewModel.currentWeek = Utils.WEEK.nextWeek
                changeWeek()
            }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun loadMore(page : Int) {
        viewModel.postPage(page)
    }


    private fun changeWeek() {
        viewModel.weekHasChanged = true
        viewModel.postPage(0)
    }

    private fun observeViewModel() {
        viewModel.comicsLiveData.observe( this , Observer { it?.let { processResponse(it) }  })
        loadMore(viewModel.pageCounter++)
    }

    private fun processResponse ( response : Resource<List<Detail>>) {
        when (response.status) {
            Status.LOADING -> renderLoadingState()

            Status.SUCCESS -> renderDataState(response.data!!)

            Status.ERROR -> renderErrorState(response.error!!)
        }
    }

    private fun renderLoadingState() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun renderDataState(items : List<Detail> ) {

        binding.progressBar.visibility = View.GONE
        if(!items.isNullOrEmpty()) {
            viewModel.pageCounter = items[items.size-1].page

            if(viewModel.weekHasChanged) {
                viewModel.weekHasChanged = false
                viewModel.firstTime = true
                viewModel.adapter.addList(items)
            } else {
                viewModel.adapter.updateList(items)
            }


            if(viewModel.firstTime) {
                binding.rvComics.scheduleLayoutAnimation()
                viewModel.firstTime = false
            }
        }


    }

    private fun renderErrorState(throwable: Throwable) {
        Timber.d("call ERROR response : " + throwable.toString())
        binding.progressBar.visibility = View.GONE
        //ErrorDialog.show(this.supportFragmentManager.beginTransaction(), throwable.toString())
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