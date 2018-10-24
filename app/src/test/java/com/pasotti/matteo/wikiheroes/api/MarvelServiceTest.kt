package com.pasotti.matteo.wikiheroes.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pasotti.matteo.wikiheroes.utils.Utils
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.Okio
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*

@RunWith(JUnit4::class)
class MarvelServiceTest {


    @Rule
    @JvmField val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MarvelApi
    private lateinit var mockWebServer: MockWebServer

    val defaultLimit = 10

    var offset = 0

    val timestamp = Date().time
    val hash = Utils.md5(timestamp.toString() + Utils.MARVEL_PRIVATE_KEY + Utils.MARVEL_PUBLIC_KEY)

    @Throws(IOException::class)
    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(MarvelApi::class.java)
    }

    @Throws(IOException::class)
    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test fun getCharacters() {
        enqueueResponse("get-characters.json")
        val characters = LiveDataTestUtil.getValue(service.getCharacters("-modified", timestamp.toString(), Utils.MARVEL_PUBLIC_KEY, hash, offset, defaultLimit)).body

        val request: RecordedRequest = mockWebServer.takeRequest()
        MatcherAssert.assertThat(request.path, CoreMatchers.`is`("/v1/public/characters?orderBy=-modified&ts=" + timestamp.toString() + "&apikey=" + Utils.MARVEL_PUBLIC_KEY + "&hash=" + hash + "&offset=0&limit=10"))
        MatcherAssert.assertThat(characters, notNullValue())
        MatcherAssert.assertThat(characters?.data?.results?.get(0)?.id , `is`(1011244))
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, emptyMap())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }
}