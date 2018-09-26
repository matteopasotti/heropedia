package com.pasotti.matteo.wikiheroes.repository

import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class LoginRepository {

    fun login(login: String, password: String): Observable<Boolean> {
        Timber.v("login %s with password %s", login, password)

        return Observable.fromCallable { CORRECT_LOGIN == login && CORRECT_PASSWORD == password }
                .delay(2000, TimeUnit.MILLISECONDS)
    }

    companion object {
        internal val CORRECT_LOGIN = "matteopasotti"
        internal val CORRECT_PASSWORD = "Password123"
    }
}