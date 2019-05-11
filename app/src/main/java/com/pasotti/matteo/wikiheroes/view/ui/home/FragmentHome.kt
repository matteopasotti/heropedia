package com.pasotti.matteo.wikiheroes.view.ui.home

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.FragmentHomePagerBinding
import com.pasotti.matteo.wikiheroes.view.adapter.SampleFragmentPagerAdapter

class FragmentHome : Fragment() {

    lateinit var binding : FragmentHomePagerBinding

    private lateinit var pagerAdapter : SampleFragmentPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate( inflater , R.layout.fragment_home_pager , container , false)


        initUI()

        return binding.root
    }

    private fun initUI() {
        pagerAdapter = SampleFragmentPagerAdapter(context!!, fragmentManager!!)
        binding.viewPager.adapter = pagerAdapter
        binding.viewPager.offscreenPageLimit = (pagerAdapter.count - 1)

        binding.navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_characters -> {
                    binding.viewPager.currentItem = 0
                }
                R.id.navigation_comics -> {
                    binding.viewPager.currentItem = 1
                }
                R.id.navigation_saved -> {
                    binding.viewPager.currentItem = 2
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_home_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!,
                view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }

   /* override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if( item != null) {
            when( item.itemId) {
                R.id.action_search -> {
                    val intent = Intent(this , SearchActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.enter, R.anim.exit)
                    return true
                }
            }
        }

        return super.onOptionsItemSelected(item)

    }*/
}