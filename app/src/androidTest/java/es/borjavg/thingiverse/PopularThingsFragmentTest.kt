package es.borjavg.thingiverse

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import es.borjavg.data.di.ApiUrlModule
import es.borjavg.thingiverse.features.popular.ui.PopularThingsFragment
import es.borjavg.thingiverse.util.BaseUiTest
import es.borjavg.thingiverse.util.launchFragmentInHiltContainer
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@UninstallModules(ApiUrlModule::class)
@RunWith(AndroidJUnit4::class)
class PopularThingsFragmentTest : BaseUiTest() {

    override val hiltRule = HiltAndroidRule(this)

    @Test
    fun givenListOfThingsWhenOneIsSelectedThenDetailIsShown() {
        //Launch fragment
        launchFragmentInHiltContainer<PopularThingsFragment>()

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
}
