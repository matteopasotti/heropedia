package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic.more_info

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.FragmentMoreInfoBinding
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.view.adapter.CreatorRowAdapter
import com.pasotti.matteo.wikiheroes.view.ui.person.PersonDetailActivity
import com.pasotti.matteo.wikiheroes.view.viewholder.CreatorViewHolder
import org.koin.android.architecture.ext.viewModel


class MoreInfoFragment : Fragment() , CreatorViewHolder.Delegate {

    private val viewModel : MoreInfoViewModel by viewModel()

    lateinit var binding : FragmentMoreInfoBinding

    lateinit var creators : List<Item>

    lateinit var adapter : CreatorRowAdapter

    companion object {

        const val CREATORS = "CREATORS"

        const val SECTION = "SECTION"

        fun newInstance(creators : ArrayList<Item>, section : String) : MoreInfoFragment {
            val args: Bundle = Bundle()
            args.putParcelableArrayList(CREATORS , creators)
            args.putString(SECTION , section)
            val fragment = MoreInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if( savedInstanceState == null ) {
            creators = arguments!!.getParcelableArrayList(CREATORS)

            viewModel.creators = creators

            viewModel.section = arguments!!.getString(SECTION)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_more_info, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        adapter = CreatorRowAdapter(this, viewModel.getCreatorsMap(viewModel.creators))
        binding.listRowsCreators.adapter = adapter
    }

    override fun onItemClick(creator: Item, view: View) {

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