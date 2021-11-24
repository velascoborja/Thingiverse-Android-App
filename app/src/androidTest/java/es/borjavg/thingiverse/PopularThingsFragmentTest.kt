package es.borjavg.thingiverse

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import es.borjavg.data.di.ApiUrlModule
import es.borjavg.data.di.BaseUrl
import es.borjavg.thingiverse.features.popular.ui.PopularThingsFragment
import es.borjavg.thingiverse.util.MockServerDispatcher
import es.borjavg.thingiverse.util.launchFragmentInHiltContainer
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Singleton

@UninstallModules(ApiUrlModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PopularThingsFragmentTest {

    lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

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

    @Test
    fun givenArticleIsClickedThenNavigateToDetailsScreen() {
        //Launch fragment
        launchFragmentInHiltContainer<PopularThingsFragment> {}

        //Click on second thing
        onView(withId(R.id.recyclerView)).perform(
            actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )

        //Check thing opens
        intended(allOf(hasAction(equalTo(Intent.ACTION_VIEW))))
    }


    @Module
    @InstallIn(SingletonComponent::class)
    class FakeApiUrlModule {

        @Provides
        @Singleton
        @BaseUrl
        fun provideBaseUrl(): String = "http://localhost:8080/"

    }
}