package com.pasotti.matteo.wikiheroes.repository

import com.pasotti.matteo.wikiheroes.utils.PreferenceManager


class MainRepository (private val preferenceManager: PreferenceManager) {

    fun saveSaveDominantColor(color : Int) {
        preferenceManager.setInt(PreferenceManager.DOMINANT_COLOR, color)
    }


    fun getDominantColor() : Int = preferenceManager.getInt(PreferenceManager.DOMINANT_COLOR, 0)
    // put the value of black color

    fun getStandardColor() : Int = -13094864
}