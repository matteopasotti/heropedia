package com.pasotti.matteo.wikiheroes.view.ui.home.comics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.FragmentHomeComicsBinding

class HomeComicsFragment : Fragment() {

    lateinit var binding : FragmentHomeComicsBinding


    companion object {
        fun newInstance() : HomeComicsFragment {
            val args = Bundle()
            val fragment = HomeComicsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater ,  R.layout.fragment_home_comics, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.text.text = "Comics Fragment"
    }
}