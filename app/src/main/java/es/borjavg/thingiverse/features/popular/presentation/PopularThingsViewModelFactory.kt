package es.borjavg.thingiverse.features.popular.presentation

import es.borjavg.domain.usecases.GetPopularThingsUseCase
import es.borjavg.presentation.CoroutinesDispatchers
import es.borjavg.presentation.ViewModelFactory
import javax.inject.Inject

class PopularThingsViewModelFactory @Inject constructor(
    private val dispatchers: CoroutinesDispatchers,
    private val getPopularThingsUseCase: GetPopularThingsUseCase
) : ViewModelFactory<PopularThingsViewModel>() {

    override fun create() = PopularThingsViewModel(
        getPopularThingsUseCase = getPopularThingsUseCase,
        dispatchers = dispatchers
    )
}