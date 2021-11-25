package es.borjavg.thingiverse.features.popular.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.borjavg.thingiverse.databinding.FragmentPopularThingsBinding
import es.borjavg.thingiverse.features.popular.presentation.PopularThingsViewModel
import es.borjavg.thingiverse.features.popular.presentation.PopularThingsViewModelFactory
import es.borjavg.thingiverse.features.popular.presentation.PopularViewAction
import es.borjavg.thingiverse.features.popular.presentation.PopularViewIntent
import es.borjavg.thingiverse.ui.common.*
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
    ) = FragmentPopularThingsBinding.inflate(layoutInflater, container, false).also { binding ->

        with(binding) {
            adapter = ThingsAdapter(
                imageLoader = imageLoader,
                onItemClickListener = {
                    viewModel.sendIntent(PopularViewIntent.OnThingClick(it))
                },
                onItemLikeChanged = {
                    viewModel.sendIntent(PopularViewIntent.OnLikeThingClick(it))
                }
            )

            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            with(binding) {
                recyclerView.switchVisibility(it.isLoading.not())
                loadingAnimationView.switchVisibility(it.isLoading)
                adapter?.submitList(it.items)
            }
        }

        viewModel.viewErrors.observe(viewLifecycleOwner) {
            with(binding) {
                recyclerView.gone()
                loadingAnimationView.gone()
                errorAnimationView.visible()
                errorAnimationView.playAnimation()
            }
        }

        viewModel.viewActions.observe(viewLifecycleOwner) { action ->
            when (action) {
                is PopularViewAction.OpenThingDetail -> requireContext().openUrl(action.url)
            }
        }

        viewModel.load()

    }.root

    companion object {
        fun newInstance() = PopularThingsFragment()
    }
}