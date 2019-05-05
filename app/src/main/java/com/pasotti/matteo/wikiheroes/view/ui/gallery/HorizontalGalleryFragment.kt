package com.pasotti.matteo.wikiheroes.view.ui.gallery

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
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.databinding.FragmentHorizontalGalleryBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Detail
import com.pasotti.matteo.wikiheroes.models.DetailResponse
import com.pasotti.matteo.wikiheroes.utils.Utils
import com.pasotti.matteo.wikiheroes.view.adapter.DetailAdapter
import com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.DetailItemActivity
import com.pasotti.matteo.wikiheroes.view.ui.seeall.SeeAllActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.DetailViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.item_small_image.view.*
import javax.inject.Inject

class HorizontalGalleryFragment : Fragment() , DetailViewHolder.Delegate {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HorizontalGalleryViewModel::class.java)}

    lateinit var binding : FragmentHorizontalGalleryBinding

    lateinit var adapter : DetailAdapter

    companion object {

        private const val TITLE = "title"
        private const val CHARACTER_ID = "characterId"
        private const val CHARACTER_NAME = "character_name"

        fun newInstance( title : String , characterId : Int, characterName : String) : HorizontalGalleryFragment {
            val args = Bundle()
            args.putString(TITLE , title)
            args.putInt(CHARACTER_ID, characterId)
            args.putString(CHARACTER_NAME, characterName)
            val fragment = HorizontalGalleryFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater ,  R.layout.fragment_horizontal_gallery, container, false)

        initView()

        return binding.root
    }

    private fun initView() {

        viewModel.characterId = arguments!!.getInt(CHARACTER_ID)
        adapter = DetailAdapter(this)
        val linearLayoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false)
        binding.listItems.layoutManager = linearLayoutManager
        binding.listItems.adapter = adapter
        binding.sectionTitle.text = arguments!!.getString(TITLE)
        viewModel.section =  arguments!!.getString(TITLE)
        viewModel.characterName = arguments!!.getString(CHARACTER_NAME)

        binding.seeallLabel.setOnClickListener {

            val intent = Intent(context , SeeAllActivity::class.java)
            intent.putExtra(SeeAllActivity.TITLE_SECTION , "Character name")
            intent.putExtra(SeeAllActivity.ID, viewModel.characterId)
            intent.putExtra(SeeAllActivity.SECTION, viewModel.section)
            intent.putExtra(SeeAllActivity.CHARACTER_NAME, viewModel.characterName)
            startActivity(intent)
        }

    }

    private fun observeViewModel() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getItems(viewModel.characterId, viewModel.section).observe(this, Observer { it?.let { processResponse(it) } })

    }

    private fun processResponse(response: ApiResponse<DetailResponse>) {
        binding.progressBar.visibility = View.GONE
        if(response.isSuccessful && response.body != null && !response.body.data.results.isNullOrEmpty()) {
            renderDataState(Utils.checkDetailsImages(response.body.data.results))
        } else {
            binding.root.visibility = View.INVISIBLE
        }
    }

    private fun renderDataState ( items : List<Detail>) {
        items.forEach {
            it.week = Utils.WEEK.none
        }
        adapter.updateList(items)
    }


    override fun onItemClick(item: Detail, view: View) {

        val img = Pair.create(view.image_gallery as View, resources.getString(R.string.transition_detail_image))

        val txt = Pair.create(view.title_gallery as View, resources.getString(R.string.transition_detail_title))

        //val options = ActivityOptions.makeSceneTransitionAnimation(activity, img, txt)

        val intent = Intent(activity, DetailItemActivity::class.java)
        intent.putExtra(DetailItemActivity.INTENT_ITEM , item as Parcelable)
        intent.putExtra(DetailItemActivity.INTENT_SECTION , viewModel.section)
        startActivity(intent)
    }

}