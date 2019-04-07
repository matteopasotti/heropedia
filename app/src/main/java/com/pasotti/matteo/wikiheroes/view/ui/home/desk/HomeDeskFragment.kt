package com.pasotti.matteo.wikiheroes.view.ui.home.desk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.FragmentHomeDeskBinding

class HomeDeskFragment : Fragment() {
    lateinit var binding : FragmentHomeDeskBinding


    companion object {
        fun newInstance() : HomeDeskFragment {
            val args = Bundle()
            val fragment = HomeDeskFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater ,  R.layout.fragment_home_desk, container, false)

        initView()

        return binding.root
    }

    private fun initView() {
        binding.text.text = "Desk Fragment"
    }
}