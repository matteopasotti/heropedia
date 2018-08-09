package com.pasotti.matteo.wikiheroes.utils

import android.animation.Animator
import android.transition.Transition
import android.transition.TransitionValues
import android.view.ViewGroup

class CustomTransition : Transition() {

    override fun captureStartValues(transitionValues: TransitionValues) {}

    override fun captureEndValues(transitionValues: TransitionValues) {}

    override fun createAnimator(
            sceneRoot: ViewGroup,
            startValues: TransitionValues?,
            endValues: TransitionValues?
    ): Animator? {
        return null
    }

}