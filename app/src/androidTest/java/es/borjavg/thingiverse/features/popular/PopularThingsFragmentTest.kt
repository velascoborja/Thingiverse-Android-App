package es.borjavg.thingiverse.features.popular

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import es.borjavg.data.di.ApiUrlModule
import es.borjavg.thingiverse.features.popular.ui.PopularThingsFragment
import es.borjavg.thingiverse.util.BaseUiTest
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@UninstallModules(ApiUrlModule::class)
@RunWith(AndroidJUnit4::class)
class PopularThingsFragmentTest : BaseUiTest() {

    override val hiltRule = HiltAndroidRule(this)

    @Test
    fun givenListOfThingsWhenOneIsSelectedThenDetailIsShown() {
        popular {
            clickThing(1)
            checkThingDetailOpens()
        }
    }

    fun popular(func: PopularRobot.() -> Unit) = PopularRobot().apply {
        launchFragmentInHiltContainer<PopularThingsFragment>()
        func()
    }
}
