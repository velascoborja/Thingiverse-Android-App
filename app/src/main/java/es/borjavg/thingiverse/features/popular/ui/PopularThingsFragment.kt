package es.borjavg.thingiverse.features.popular.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.borjavg.thingiverse.features.popular.presentation.PopularThingsViewModel
import es.borjavg.thingiverse.features.popular.presentation.PopularThingsViewModelFactory
import es.borjavg.thingiverse.features.popular.presentation.PopularViewAction
import es.borjavg.thingiverse.features.popular.presentation.PopularViewIntent
import es.borjavg.thingiverse.ui.common.ImageLoader
import es.borjavg.thingiverse.ui.common.openUrl
import es.borjavg.thingiverse.ui.theme.AppTheme
import es.borjavg.thingiverse.ui.widgets.ThingList
import javax.inject.Inject

@AndroidEntryPoint
class PopularThingsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: PopularThingsViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: PopularThingsViewModel by viewModels { viewModelFactory }

    private var adapter: ThingsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {

        viewModel.viewState.observe(viewLifecycleOwner) {
            setContent {
                AppTheme {
                    ThingList(
                        isLoading = it.isLoading,
                        items = it.items,
                        onThingClick = {
                            viewModel.sendIntent(PopularViewIntent.OnThingClick(it))
                        }, onThingLikeClick = {
                            viewModel.sendIntent(PopularViewIntent.OnLikeThingClick(it))
                        })
                }
            }
        }

        viewModel.viewErrors.observe(viewLifecycleOwner) {
            // TODO: show errors
            /*with(binding) {
                recyclerView.gone()
                loadingAnimationView.gone()
                errorAnimationView.visible()
                errorAnimationView.playAnimation()
            }*/
        }

        viewModel.viewActions.observe(viewLifecycleOwner) { action ->
            when (action) {
                is PopularViewAction.OpenThingDetail -> requireContext().openUrl(action.url)
            }
        }

        viewModel.load()
    }

    companion object {
        fun newInstance() = PopularThingsFragment()
    }
}