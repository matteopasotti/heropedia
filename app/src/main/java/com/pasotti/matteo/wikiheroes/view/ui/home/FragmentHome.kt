package com.pasotti.matteo.wikiheroes.view.ui.home

import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.FragmentHomePagerBinding
import com.pasotti.matteo.wikiheroes.view.adapter.SampleFragmentPagerAdapter
import com.pasotti.matteo.wikiheroes.view.ui.home.characters.HomeCharactersFragment
import com.pasotti.matteo.wikiheroes.view.ui.home.comics.HomeComicsFragment
import com.pasotti.matteo.wikiheroes.view.ui.home.desk.HomeDeskFragment
import com.pasotti.matteo.wikiheroes.view.ui.search.SearchActivity

class FragmentHome : Fragment() {

    lateinit var binding : FragmentHomePagerBinding

    private lateinit var pagerAdapter : SampleFragmentPagerAdapter


    private class HomeFragmentPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return HomeCharactersFragment.newInstance()
                1 -> return HomeComicsFragment.newInstance()
                2 -> return HomeDeskFragment.newInstance()
            }
            return Fragment()
        }

        override fun getCount(): Int {
            return 3
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