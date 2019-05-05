package com.pasotti.matteo.wikiheroes.view.ui.detail_items.detail_comic

import android.content.Context
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pasotti.matteo.wikiheroes.databinding.FragmentDetailImageBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DetailImageFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(DetailImageViewModel::class.java) }

    private lateinit var binding: FragmentDetailImageBinding

    companion object {


        private const val IMAGE_URL = "image_url"

        fun newInstance( imageUrl : String) : DetailImageFragment {
            val args = Bundle()
            args.putString(IMAGE_URL , imageUrl)
            val fragment = DetailImageFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        configureTransition()
        binding = FragmentDetailImageBinding.inflate( inflater , container , false)
        binding.viewmodel = viewModel
        binding.setLifecycleOwner(viewLifecycleOwner)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadDataWhenFragmentStarts(arguments!!.getString(IMAGE_URL))
    }

    private fun configureTransition() {
        val transition = ChangeBounds().apply { duration = 300 }
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }
}