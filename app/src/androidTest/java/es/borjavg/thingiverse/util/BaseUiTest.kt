package es.borjavg.thingiverse.util

import androidx.test.espresso.intent.Intents
import dagger.hilt.android.testing.HiltAndroidRule
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseUiTest {

    @get:Rule
    abstract val hiltRule: HiltAndroidRule

    lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        Intents.init()

        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockServerDispatcher().RequestDispatcher()
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        Intents.release()

        mockWebServer.shutdown()
    }
}