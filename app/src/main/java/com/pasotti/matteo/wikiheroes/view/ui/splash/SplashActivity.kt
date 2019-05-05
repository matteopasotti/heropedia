package com.pasotti.matteo.wikiheroes.view.ui.home.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.view.ui.home.MainActivity
import com.pasotti.matteo.wikiheroes.view.ui.splash.SplashActivityViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(SplashActivityViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        //refresh db every 5 days
        viewModel.checkDateSyncComics()
        viewModel.checkDateSynchCharacters()

        Handler().postDelayed({
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 3000)

    }
}