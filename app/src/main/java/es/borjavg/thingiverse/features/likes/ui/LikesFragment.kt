package es.borjavg.thingiverse.features.likes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.borjavg.thingiverse.R
import es.borjavg.thingiverse.databinding.FragmentLikesBinding
import es.borjavg.thingiverse.features.likes.presentation.LikesViewAction
import es.borjavg.thingiverse.features.likes.presentation.LikesViewIntent
import es.borjavg.thingiverse.features.likes.presentation.LikesViewModel
import es.borjavg.thingiverse.features.likes.presentation.LikesViewModelFactory
import es.borjavg.thingiverse.features.popular.ui.ThingsAdapter
import es.borjavg.thingiverse.ui.common.ImageLoader
import es.borjavg.thingiverse.ui.common.openUrl
import es.borjavg.thingiverse.ui.common.switchVisibility
import es.borjavg.thingiverse.ui.common.toast
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
    ) = FragmentLikesBinding.inflate(layoutInflater, container, false).also { binding ->

        with(binding) {
            adapter = ThingsAdapter(
                imageLoader = imageLoader,
                onItemClickListener = {
                    viewModel.sendIntent(LikesViewIntent.OnThingClick(it))
                },
                onItemLikeChanged = {
                    viewModel.sendIntent(LikesViewIntent.OnLikeThingClick(it))
                }
            )

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            with(binding) {
                recyclerView.switchVisibility(state.items.isNotEmpty())
                emptyView.switchVisibility(state.items.isEmpty())
                adapter?.submitList(state.items)
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

    }.root


    companion object {
        fun newInstance() = LikesFragment()
    }

}