package com.pasotti.matteo.wikiheroes.base


import com.pasotti.matteo.wikiheroes.factory.AppViewModelFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import java.io.File
import javax.inject.Inject

abstract class BaseTest {
    /**
     *  we configure MockWebServer library to mock the API response
     */

    lateinit var mockServer: MockWebServer

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    @Before
    open fun setUp(){
        this.configureMockServer()
        this.configureDi()
    }


    // CONFIGURATION
    open fun configureDi() {
        /*this.testAppComponent = DaggerTestAppComponent.builder()
                .baseUrl(if (isMockServerEnabled()) mockServer.url("/").toString() else Utils.BASE_URL)
                .build()

        this.testAppComponent.inject(this)*/
    }



    // MOCK SERVER
    abstract fun isMockServerEnabled(): Boolean // Because we don't want it always enabled on all tests

    open fun stopMockServer() {
        if (isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

    open fun configureMockServer() {
        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    open fun mockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName)))

    private fun getJson(path : String) : String {
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}