package es.borjavg.thingiverse.features.popular.presentation

import es.borjavg.domain.usecases.GetPopularThingsUseCase
import es.borjavg.domain.usecases.RemoveLikedThingUseCase
import es.borjavg.domain.usecases.SaveLikedThingUseCase
import es.borjavg.presentation.CoroutinesDispatchers
import es.borjavg.presentation.ViewModelFactory
import javax.inject.Inject

class PopularThingsViewModelFactory @Inject constructor(
    private val dispatchers: CoroutinesDispatchers,
    private val getPopularThingsUseCase: GetPopularThingsUseCase,
    private val saveLikedThingUseCase: SaveLikedThingUseCase,
    private val removeLikedThingUseCase: RemoveLikedThingUseCase
) : ViewModelFactory<PopularThingsViewModel>() {

    override fun create() = PopularThingsViewModel(
        getPopularThingsUseCase = getPopularThingsUseCase,
        saveLikedThingUseCase = saveLikedThingUseCase,
        removeLikedThingUseCase = removeLikedThingUseCase,
        dispatchers = dispatchers
    )
}