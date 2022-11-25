package es.borjavg.thingiverse.features.likes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.borjavg.thingiverse.R
import es.borjavg.thingiverse.features.likes.presentation.LikesViewAction
import es.borjavg.thingiverse.features.likes.presentation.LikesViewIntent
import es.borjavg.thingiverse.features.likes.presentation.LikesViewModel
import es.borjavg.thingiverse.features.likes.presentation.LikesViewModelFactory
import es.borjavg.thingiverse.features.popular.ui.ThingsAdapter
import es.borjavg.thingiverse.ui.common.ImageLoader
import es.borjavg.thingiverse.ui.common.openUrl
import es.borjavg.thingiverse.ui.common.toast
import es.borjavg.thingiverse.ui.theme.AppTheme
import es.borjavg.thingiverse.ui.widgets.ThingList
import javax.inject.Inject

@AndroidEntryPoint
class LikesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: LikesViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: LikesViewModel by viewModels { viewModelFactory }

    private var adapter: ThingsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            setContent {
                AppTheme {
                    ThingList(
                        items = state.items,
                        isLoading = false,
                        onThingClick = { viewModel.sendIntent(LikesViewIntent.OnThingClick(it)) },
                        onThingLikeClick = { viewModel.sendIntent(LikesViewIntent.OnLikeThingClick(it)) }
                    )
                }
            }
        }

        viewModel.viewErrors.observe(viewLifecycleOwner) {
            toast(R.string.remove_like_error)
        }

        viewModel.viewActions.observe(viewLifecycleOwner) { action ->
            when (action) {
                is LikesViewAction.OpenThingDetail -> requireContext().openUrl(action.url)
            }
        }

        viewModel.load()
    }


    companion object {
        fun newInstance() = LikesFragment()
    }

}