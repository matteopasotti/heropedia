package com.pasotti.matteo.wikiheroes.view.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ActivityMainBinding
import com.pasotti.matteo.wikiheroes.view.ui.home.characters.HomeCharactersFragment
import com.pasotti.matteo.wikiheroes.view.ui.home.comics.HomeComicsFragment
import com.pasotti.matteo.wikiheroes.view.ui.home.desk.HomeDeskFragment
import com.pasotti.matteo.wikiheroes.view.ui.search.SearchActivity

class MainActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }

    private lateinit var pagerAdapter : HomeFragmentPageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUI()
    }

    private fun initUI() {


        // set toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.title = ""


        pagerAdapter = HomeFragmentPageAdapter(supportFragmentManager)
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



    private class HomeFragmentPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return HomeCharactersFragment.newInstance()
                1 -> return HomeComicsFragment.newInstance()
                2 -> return HomeDeskFragment.newInstance()
            }
            return null
        }

        override fun getCount(): Int {
            return 3
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home_toolbar, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
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

    }

}


