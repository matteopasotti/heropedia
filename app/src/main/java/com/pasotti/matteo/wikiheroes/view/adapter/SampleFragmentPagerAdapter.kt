package com.pasotti.matteo.wikiheroes.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.pasotti.matteo.wikiheroes.view.ui.home.characters.HomeCharactersFragment
import com.pasotti.matteo.wikiheroes.view.ui.home.comics.HomeComicsFragment
import com.pasotti.matteo.wikiheroes.view.ui.home.desk.HomeDeskFragment

class SampleFragmentPagerAdapter (context: Context , fm: FragmentManager) : FragmentPagerAdapter(fm) {


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