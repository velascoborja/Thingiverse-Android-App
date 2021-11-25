package es.borjavg.thingiverse.features.likes

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import es.borjavg.data.di.ApiUrlModule
import es.borjavg.thingiverse.R
import es.borjavg.thingiverse.features.main.ui.MainActivity
import es.borjavg.thingiverse.util.BaseUiTest
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@UninstallModules(ApiUrlModule::class)
@RunWith(AndroidJUnit4::class)
class LikesFragmentTest : BaseUiTest() {

    override val hiltRule = HiltAndroidRule(this)

    @Test
    fun givenListOfThingsWhenOneIsSelectedThenDetailIsShown() {
        likes {
            // TODO include test
        }
    }

    private fun likes(func: LikesRobot.() -> Unit) = LikesRobot().apply {
        launchActivity<MainActivity>()
        clickButton(R.id.menu_item_likes)
        func()
    }
}
