package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_info

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import android.util.Pair
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.FragmentMoreInfoBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.view.adapter.CreatorRowAdapter
import com.pasotti.matteo.wikiheroes.view.ui.creator.CreatorDetailActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.CreatorViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.creator_item.view.*
import javax.inject.Inject

class MoreInfoFragment : Fragment() , CreatorViewHolder.Delegate {

    //TODO we need to prepare the items for the adapter, creating groups by role

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MoreInfoViewModel::class.java)}

    lateinit var binding : FragmentMoreInfoBinding

    lateinit var creators : List<Item>

    lateinit var adapter : CreatorRowAdapter

    companion object {

        val CREATORS = "CREATORS"

        fun newInstance(creators : ArrayList<Item>) : MoreInfoFragment {
            val args: Bundle = Bundle()
            args.putParcelableArrayList(CREATORS , creators)
            val fragment = MoreInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        creators = arguments!!.getParcelableArrayList(CREATORS)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_more_info, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        adapter = CreatorRowAdapter(this, viewModel.getCreatorsMap(creators))
        binding.listRowsCreators.adapter = adapter
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onItemClick(creator: Item, view: View) {

        val txt = Pair.create(view.creator_name as View, resources.getString(R.string.transition_creator_name))
        val options = ActivityOptions.makeSceneTransitionAnimation(activity, txt)

        val intent = Intent(activity, CreatorDetailActivity::class.java)
        intent.putExtra(CreatorDetailActivity.CREATOR , creator as Parcelable)
        intent.putExtra(CreatorDetailActivity.TITLE_SECTION, "Comics")
        startActivity(intent, options.toBundle())
    }
}