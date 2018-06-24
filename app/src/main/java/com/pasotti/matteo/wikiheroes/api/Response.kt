package com.pasotti.matteo.wikiheroes.api

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource private constructor(val status: Status, val data: String?, val error: Throwable?) {
    companion object {

        fun loading(): Resource {
            return Resource(Status.LOADING, null, null)
        }

        fun success(data: String): Resource {
            return Resource(Status.SUCCESS, data, null)
        }

        fun error(error: Throwable): Resource {
            return Resource(Status.ERROR, null, error)
        }
    }
}