package es.borjavg.thingiverse.features.main

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import es.borjavg.data.di.ApiUrlModule
import es.borjavg.thingiverse.R
import es.borjavg.thingiverse.features.likes.ui.LikesFragment
import es.borjavg.thingiverse.features.main.ui.MainActivity
import es.borjavg.thingiverse.features.popular.ui.PopularThingsFragment
import es.borjavg.thingiverse.util.BaseUiTest
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@UninstallModules(ApiUrlModule::class)
@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseUiTest() {

    override val hiltRule = HiltAndroidRule(this)

    @Test
    fun when_AppIsLoadedThen_ToolbarTitleIsShowed() {
        launchActivity<MainActivity> {
            main {
                selectPopularTab()
                matchToolbarTitle(getString(R.string.app_name))
            }
        }
    }

    @Test
    fun when_PopularTabIsClockedThen_PopularFragmentLoads() {
        launchActivity<MainActivity> {
            main {
                selectPopularTab()
            }

            onActivity { mainActivity ->
                Assert.assertTrue(mainActivity.supportFragmentManager.fragments.any { it is PopularThingsFragment })
            }
        }
    }

    @Test
    fun when_LikesTabIsClockedThen_LikesFragmentLoads() {
        launchActivity<MainActivity> {
            main {
                selectLikesTab()
            }

            onActivity { mainActivity ->
                Assert.assertTrue(mainActivity.supportFragmentManager.fragments.any { it is LikesFragment })
            }
        }
    }

    private fun main(func: MainRobot.() -> Unit) = MainRobot().apply { func() }
}
