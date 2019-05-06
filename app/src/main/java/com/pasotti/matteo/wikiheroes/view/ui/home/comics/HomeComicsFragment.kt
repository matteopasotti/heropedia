package com.pasotti.matteo.wikiheroes.view.ui.home.comics

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
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
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailItemActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.HomeComicsViewHolder
import dagger.android.support.AndroidSupportInjection
import org.jetbrains.anko.textColor
import timber.log.Timber
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

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater ,  com.pasotti.matteo.wikiheroes.R.layout.fragment_home_comics, container, false)

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

        binding.thisWeek.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        //SELECTION OF THE WEEK
        binding.previousWeek.setOnClickListener {
            //lastWeek
            if(viewModel.currentWeek != Utils.WEEK.lastWeek) {
                //load items of lastWeek
                binding.thisWeek.paintFlags = 0
                binding.nextWeek.paintFlags = 0

                binding.progressBar.visibility = View.VISIBLE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.thisWeek.textColor = resources.getColor(R.color.whiteFadeBg , null)
                    binding.nextWeek.textColor = resources.getColor(R.color.whiteFadeBg , null)
                    binding.previousWeek.textColor = resources.getColor(R.color.dark_yellow , null)
                } else {
                    binding.thisWeek.textColor = resources.getColor(R.color.whiteFadeBg)
                    binding.nextWeek.textColor = resources.getColor(R.color.whiteFadeBg)
                    binding.previousWeek.textColor = resources.getColor(R.color.dark_yellow)
                }

                binding.previousWeek.paintFlags = Paint.UNDERLINE_TEXT_FLAG

                viewModel.currentWeek = Utils.WEEK.lastWeek
                changeWeek(Utils.WEEK.lastWeek)
            }
        }


        binding.thisWeek.setOnClickListener {
            if(viewModel.currentWeek != Utils.WEEK.thisWeek) {
                //load items of thisWeek
                binding.previousWeek.paintFlags = 0
                binding.nextWeek.paintFlags = 0


                binding.progressBar.visibility = View.VISIBLE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.previousWeek.textColor = resources.getColor(R.color.whiteFadeBg , null)
                    binding.nextWeek.textColor = resources.getColor(R.color.whiteFadeBg , null)
                    binding.thisWeek.textColor = resources.getColor(R.color.dark_yellow , null)
                } else {
                    binding.previousWeek.textColor = resources.getColor(R.color.whiteFadeBg)
                    binding.nextWeek.textColor = resources.getColor(R.color.whiteFadeBg)
                    binding.thisWeek.textColor = resources.getColor(R.color.dark_yellow)
                }

                binding.thisWeek.paintFlags = Paint.UNDERLINE_TEXT_FLAG

                viewModel.currentWeek = Utils.WEEK.thisWeek
                changeWeek(Utils.WEEK.thisWeek)
            }
        }


        binding.nextWeek.setOnClickListener {
            if(viewModel.currentWeek != Utils.WEEK.nextWeek) {
                //load items of nextWeek
                binding.thisWeek.paintFlags = 0
                binding.previousWeek.paintFlags = 0

                binding.progressBar.visibility = View.VISIBLE
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.previousWeek.textColor = resources.getColor(R.color.whiteFadeBg , null)
                    binding.thisWeek.textColor = resources.getColor(R.color.whiteFadeBg , null)
                    binding.nextWeek.textColor = resources.getColor(R.color.dark_yellow , null)
                } else {
                    binding.previousWeek.textColor = resources.getColor(R.color.whiteFadeBg)
                    binding.thisWeek.textColor = resources.getColor(R.color.whiteFadeBg)
                    binding.nextWeek.textColor = resources.getColor(R.color.dark_yellow)
                }
                binding.nextWeek.paintFlags = Paint.UNDERLINE_TEXT_FLAG

                viewModel.currentWeek = Utils.WEEK.nextWeek
                changeWeek(Utils.WEEK.nextWeek)
            }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }


    private fun changeWeek(week : Utils.WEEK) {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.weekHasChanged = true
        viewModel.changeWeek(week)
    }

    private fun observeViewModel() {
        viewModel.comicsLiveData.observe( this , Observer { it?.let { processResponse(it) }  })
       viewModel.changeWeek(Utils.WEEK.thisWeek)
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

            if(viewModel.weekHasChanged) {
                viewModel.weekHasChanged = false
                viewModel.adapter.addList(items)
            } else {
                viewModel.adapter.updateList(items)
            }

        }


    }

    private fun renderErrorState(throwable: Throwable) {
        Timber.d("call ERROR response : " + throwable.toString())
        binding.progressBar.visibility = View.GONE
        //ErrorDialog.show(this.supportFragmentManager.beginTransaction(), throwable.toString())
    }


    override fun onItemClick(item: Detail, view: View) {
        val intent = Intent(activity, DetailItemActivity::class.java)
        intent.putExtra(DetailItemActivity.INTENT_ITEM , item as Parcelable)
        intent.putExtra(DetailItemActivity.INTENT_SECTION , "Comics")
        startActivity(intent)
    }
}