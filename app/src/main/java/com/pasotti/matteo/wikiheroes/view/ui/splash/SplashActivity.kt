package com.pasotti.matteo.wikiheroes.view.ui.home.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.view.ui.home.MainActivity
import com.pasotti.matteo.wikiheroes.view.ui.splash.SplashActivityViewModel
import dagger.android.AndroidInjection
import org.koin.android.architecture.ext.viewModel


class SplashActivity : AppCompatActivity() {

    private val viewModel : SplashActivityViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
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