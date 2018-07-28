package com.pasotti.matteo.wikiheroes.view.ui.home.login

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.ActivityLoginBinding
import dagger.android.AndroidInjection


class LoginActivity : AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login)

        val ACCELERATE_DECELERATE = AccelerateDecelerateInterpolator()
        val generator = RandomTransitionGenerator(5000, ACCELERATE_DECELERATE)
        binding.image.setTransitionGenerator(generator) //set new transition on kenburns view
    }


}