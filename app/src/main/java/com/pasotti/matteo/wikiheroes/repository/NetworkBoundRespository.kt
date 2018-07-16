package com.pasotti.matteo.wikiheroes.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.os.AsyncTask
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.pasotti.matteo.wikiheroes.api.ApiResponse
import com.pasotti.matteo.wikiheroes.api.Resource

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
constructor() {
    private val result = MediatorLiveData<Resource<ResultType>>()

    // returns a LiveData that represents the resource
    val asLiveData: LiveData<Resource<ResultType>>
        get() = result



    init {
        result.setValue(Resource.loading(null))
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
            }
        }


    }


    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = fetchService()
        // we re-attach dbSource as a new source,
        // it will dispatch its latest value quickly
        result.addSource(dbSource) { newData -> result.setValue(Resource.loading(newData)) }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            response?.let {
                when(response.isSuccessful) {
                    true -> {
                        response.body?.let {
                            saveFetchData(it)
                            val loaded = loadFromDb()
                            result.addSource(loaded) { newData ->
                                result.removeSource(loaded)
                                setValue(Resource.success(newData))
                            }
                        }
                    }

                    false -> {
                        onFetchFailed()
                        result.addSource(dbSource) { newData ->
                            result.setValue(
                                    Resource.error(response!!.error))
                        }
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        result.value = newValue
    }


    // Called to save the result of the API response into the database
    @WorkerThread
    protected abstract fun saveFetchData(item: RequestType)

    // Called with the data in the database to decide whether it should be
    // fetched from the network.
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    // Called to get the cached data from the database
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    // Called to create the API call.
    @MainThread
    protected abstract fun fetchService(): LiveData<ApiResponse<RequestType>>

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
    @MainThread
    protected abstract fun onFetchFailed()
}