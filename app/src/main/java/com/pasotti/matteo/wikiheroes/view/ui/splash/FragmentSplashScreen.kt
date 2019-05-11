package com.pasotti.matteo.wikiheroes.view.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.pasotti.matteo.wikiheroes.R
import com.pasotti.matteo.wikiheroes.databinding.FragmentSplashScreenBinding
import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import com.pasotti.matteo.wikiheroes.view.ui.home.MainActivity
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FragmentSplashScreen : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(SplashActivityViewModel::class.java)}


    lateinit var binding : FragmentSplashScreenBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate( inflater , R.layout.fragment_splash_screen , container , false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //refresh db every 5 days
        viewModel.checkDateSyncComics()
        viewModel.checkDateSynchCharacters()

        Handler().postDelayed({
            view.findNavController().navigate(R.id.action_fragmentSplashScreen_to_homeCharactersFragment)
            //val mainIntent = Intent(activity, MainActivity::class.java)
            //tartActivity(mainIntent)
        }, 3000)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}