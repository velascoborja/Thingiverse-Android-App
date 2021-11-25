package es.borjavg.thingiverse.features.likes

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import es.borjavg.data.di.ApiUrlModule
import es.borjavg.thingiverse.features.main.ui.MainActivity
import es.borjavg.thingiverse.features.popular.PopularRobot
import es.borjavg.thingiverse.util.BaseUiTest
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@UninstallModules(ApiUrlModule::class)
@RunWith(AndroidJUnit4::class)
class LikesFragmentTest : BaseUiTest() {

    override val hiltRule = HiltAndroidRule(this)

    @Test
    fun given_EmptyViewWhen_NoItemsAreLikedThen_EmptyViewIsShown() {
        likes {
            clickLikesMenu()
            matchEmptyView()
        }
    }

    @Test
    fun when_AThingIsLikedThen_ItIsListed() {
        popular {
            likeFirstThing()
        }

        likes {
            clickLikesMenu()
            matchItemLiked()
        }
    }

    private fun likes(func: LikesRobot.() -> Unit) = LikesRobot().apply {
        launchActivity<MainActivity>()
        func()
    }

    private fun popular(func: PopularRobot.() -> Unit) = PopularRobot().apply {
        launchActivity<MainActivity>()
        selectPopularTab()
        func()
    }
}


