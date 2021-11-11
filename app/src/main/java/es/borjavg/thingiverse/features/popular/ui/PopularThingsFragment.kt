package es.borjavg.thingiverse.features.popular.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import es.borjavg.thingiverse.databinding.FragmentPopularThingsBinding
import es.borjavg.thingiverse.features.popular.presentation.PopularThingsViewModel
import es.borjavg.thingiverse.features.popular.presentation.PopularThingsViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class PopularThingsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: PopularThingsViewModelFactory

    private val viewModel: PopularThingsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPopularThingsBinding.inflate(layoutInflater, container, false)

        viewModel.viewState.observe(viewLifecycleOwner) {

        }

        viewModel.viewErrors.observe(viewLifecycleOwner) {

        }

        viewModel.viewActions.observe(viewLifecycleOwner) {

        }

        viewModel.load()

        return binding.root
    }

    companion object {
        fun newInstance() = PopularThingsFragment()
    }
}