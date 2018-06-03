package com.pasotti.matteo.wikiheroes.di

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@kotlin.annotation.Retention
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

//it's the same
/*
@interface ViewModelKey {
    Class<? extends ViewModel> value();
}
 */


/*
The key is, on the other hand, specified using a custom annotation that is itself annotated with @MapKey .
Here it is the annotation used for creating a Map with, as key type, a Class<? extends ViewModel> object.
 */