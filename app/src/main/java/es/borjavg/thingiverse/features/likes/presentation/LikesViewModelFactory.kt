package es.borjavg.thingiverse.features.likes.presentation

import es.borjavg.domain.usecases.GetLikedThingsUseCase
import es.borjavg.domain.usecases.RemoveLikedThingUseCase
import es.borjavg.domain.usecases.SaveLikedThingUseCase
import es.borjavg.presentation.CoroutinesDispatchers
import es.borjavg.presentation.ViewModelFactory
import javax.inject.Inject

class LikesViewModelFactory @Inject constructor(
    private val coroutinesDispatchers: CoroutinesDispatchers,
    private val getLikedThingsUseCase: GetLikedThingsUseCase,
    private val saveLikedThingUseCase: SaveLikedThingUseCase,
    private val removeLikedThingUseCase: RemoveLikedThingUseCase
) : ViewModelFactory<LikesViewModel>() {
    override fun create() = LikesViewModel(
        coroutinesDispatchers = coroutinesDispatchers,
        getLikedThingsUseCase = getLikedThingsUseCase,
        saveLikedThingUseCase = saveLikedThingUseCase,
        removeLikedThingUseCase = removeLikedThingUseCase
    )
}