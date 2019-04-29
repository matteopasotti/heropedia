package com.pasotti.matteo.wikiheroes.view.ui.person

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ActivityCreatorDetailBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class PersonDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(PersonDetailActivityViewModel::class.java) }

    private val binding by lazy { DataBindingUtil.setContentView<ActivityCreatorDetailBinding>(this, R.layout.activity_creator_detail) }



    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) //This line initialise our dependencies
        super.onCreate(savedInstanceState)

        //initUI()
    }
}