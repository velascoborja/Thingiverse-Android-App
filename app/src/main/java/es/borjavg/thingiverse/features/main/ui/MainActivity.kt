package es.borjavg.thingiverse.features.main.ui

import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint
import es.borjavg.thingiverse.R
import es.borjavg.thingiverse.databinding.ActivityMainBinding
import es.borjavg.thingiverse.features.likes.ui.LikesFragment
import es.borjavg.thingiverse.features.main.presentation.MainViewActions
import es.borjavg.thingiverse.features.main.presentation.MainViewIntent
import es.borjavg.thingiverse.features.main.presentation.MainViewModel
import es.borjavg.thingiverse.features.main.presentation.MainViewModelFactory
import es.borjavg.thingiverse.features.popular.ui.PopularThingsFragment
import es.borjavg.thingiverse.ui.common.switchVisibility
import es.borjavg.thingiverse.ui.navigation.Navigator
import es.borjavg.thingiverse.ui.navigation.NavigatorImpl
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val navigator: Navigator by lazy {
        NavigatorImpl(supportFragmentManager, R.id.container)
    }

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            bottomNavigationView.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_item_likes -> {
                        viewModel.sendIntent(MainViewIntent.OnNavigateToLikesClick)
                        true
                    }
                    R.id.menu_item_popular -> {
                        viewModel.sendIntent(MainViewIntent.OnNavigateToPopularClick)
                        true
                    }
                    else -> false
                }
            }
        }

        viewModel.viewActions.observe(this) { action ->
            when (action) {
                MainViewActions.ShowLikesTab -> pushTab(LikesFragment.newInstance())
                MainViewActions.ShowPopularTab -> pushTab(PopularThingsFragment.newInstance())
            }
        }

        viewModel.viewState.observe(this) {
            binding.toolbar.switchVisibility(it.showToolbar)
        }

        pushTab(PopularThingsFragment.newInstance())
    }

    private fun pushTab(fragment: Fragment) {
        navigator.clearBackStack()
        navigator.push(fragment)
    }
}