package com.pasotti.matteo.wikiheroes.view.adapter

import android.util.SparseArray
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.pasotti.matteo.wikiheroes.models.Item
import com.pasotti.matteo.wikiheroes.view.ui.person.comics.CreatorComicsFragment
import com.pasotti.matteo.wikiheroes.view.ui.person.series.CreatorSeriesFragment

class CreatorViewPagerAdapter(fragmentManager: FragmentManager , val creator : Item) : FragmentPagerAdapter(fragmentManager) {

    private var registeredFragments = SparseArray<Fragment>()

    private val baseId : Long = 0


    override fun getItemId(position: Int): Long {
        return baseId + position
    }

    override fun getItem(position: Int): Fragment {
        val result: Fragment
        when (position) {
            0 -> return CreatorComicsFragment.newInstance(creator)
            1 -> return CreatorSeriesFragment.newInstance(creator)

            else -> result = Fragment()
        }

        return result
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        0 -> "Comics"

        1 -> "Series"

        else -> null
    }

    /**
     * On each Fragment instantiation we are saving the reference of that Fragment in a Map
     * It will help us to retrieve the Fragment by position
     *
     * @param container
     * @param position
     * @return
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        registeredFragments.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        registeredFragments.remove(position)
        super.destroyItem(container, position, `object`)
    }
}