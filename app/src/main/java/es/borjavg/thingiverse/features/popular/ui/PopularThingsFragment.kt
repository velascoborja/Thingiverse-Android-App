package es.borjavg.thingiverse.features.popular.ui

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.borjavg.thingiverse.databinding.FragmentPopularThingsBinding
import es.borjavg.thingiverse.features.popular.presentation.PopularThingsViewModel
import es.borjavg.thingiverse.features.popular.presentation.PopularThingsViewModelFactory
import es.borjavg.thingiverse.ui.common.ImageLoader
import es.borjavg.thingiverse.ui.common.switchVisibility
import javax.inject.Inject

@AndroidEntryPoint
class PopularThingsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: PopularThingsViewModelFactory

    @Inject
    lateinit var imageLoader: ImageLoader

    private val viewModel: PopularThingsViewModel by viewModels { viewModelFactory }

    var adapter: ThingsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentPopularThingsBinding.inflate(layoutInflater, container, false).also { binding ->

        with(binding) {
            adapter = ThingsAdapter(imageLoader)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            with(binding) {
                recyclerView.switchVisibility(it.isLoading.not())
                animationView.switchVisibility(it.isLoading)
                adapter?.submitList(it.items)
            }
        }

        viewModel.viewErrors.observe(viewLifecycleOwner) {
            // TODO: 12/11/21 Show error
        }

        viewModel.viewActions.observe(viewLifecycleOwner) {
            // TODO Show actions
        }

        viewModel.load()

    }.root

    companion object {
        fun newInstance() = PopularThingsFragment()
    }
}