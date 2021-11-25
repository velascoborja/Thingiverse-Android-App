package es.borjavg.thingiverse.features.popular

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import es.borjavg.data.di.ApiUrlModule
import es.borjavg.thingiverse.features.main.ui.MainActivity
import es.borjavg.thingiverse.util.BaseUiTest
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@UninstallModules(ApiUrlModule::class)
@RunWith(AndroidJUnit4::class)
class PopularThingsFragmentTest : BaseUiTest() {

    override val hiltRule = HiltAndroidRule(this)

    @Test
    fun given_LoadingScreenWhen_DataIsLoadingThen_LoadingViewIsShown() {
        delayServerResponse()

        popular {
            matchLoadingVisible()
        }
    }

    @Test
    fun given_LoadingScreenWhen_ErrorIsProducedThen_ErrorViewIsShown() {
        forceServerError()

        popular {
            matchErrorVisible()
        }
    }

    @Test
    fun given_ListOfThingsWhen_OneIsSelectedThen_DetailIsShown() {
        popular {
            clickFirstThing()
            matchThingDetailOpens()
        }
    }

    private fun popular(func: PopularRobot.() -> Unit) = PopularRobot().apply {
        launchActivity<MainActivity>()
        selectPopularTab()
        func()
    }
}
