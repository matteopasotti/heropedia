package com.pasotti.matteo.wikiheroes.view.ui.home.splash

import android.support.v7.app.AppCompatActivity
import com.pasotti.matteo.wikiheroes.view.ui.HomeActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.view.ui.home.login.LoginActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val mainIntent = Intent(this, HomeActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 3000)

    }
}