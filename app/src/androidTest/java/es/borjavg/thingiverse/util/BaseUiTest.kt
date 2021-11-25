package es.borjavg.thingiverse.util

import android.app.Activity
import android.content.Context
import androidx.annotation.StringRes
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.intent.Intents
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject


abstract class BaseUiTest {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @get:Rule
    abstract val hiltRule: HiltAndroidRule

    lateinit var mockWebServer: MockWebServer

    private val idlingResource by lazy { OkHttp3IdlingResource.create("OkHttp", okHttpClient) }

    @Before
    fun setUp() {
        Intents.init()

        hiltRule.inject()
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockServerDispatcher().RequestDispatcher()
        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun tearDown() {
        Intents.release()

        mockWebServer.shutdown()
    }

    inline fun <reified T : Activity> launchActivity(result: ActivityScenario<T>.() -> Unit = {}) {
        result(ActivityScenario.launch(T::class.java))
    }

    fun getString(@StringRes stringRes: Int) =
        ApplicationProvider.getApplicationContext<Context>().getString(stringRes)

    fun forceServerError() {
        mockWebServer.dispatcher = MockServerDispatcher().ErrorDispatcher()
    }

    fun delayServerResponse() {
        mockWebServer.dispatcher = MockServerDispatcher().RequestDispatcher(delayResponse = true)
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}