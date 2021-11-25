package es.borjavg.thingiverse.features.likes.presentation

import es.borjavg.domain.models.Thing
import es.borjavg.domain.usecases.GetLikedThingsUseCase
import es.borjavg.domain.usecases.RemoveLikedThingUseCase
import es.borjavg.domain.usecases.SaveLikedThingUseCase
import es.borjavg.presentation.*
import es.borjavg.thingiverse.features.main.ui.models.ThingModel
import es.borjavg.thingiverse.features.main.ui.models.toPresentation

data class LikesViewState(
    val items: List<ThingModel> = emptyList()
) : ViewState

sealed class LikesViewAction : ViewAction {
    class OpenThingDetail(val url: String) : LikesViewAction()
}

sealed class LikesViewIntent : ViewIntent {
    class OnThingClick(val thingModel: ThingModel) : LikesViewIntent()
    class OnLikeThingClick(val thingModel: ThingModel) : LikesViewIntent()
}

class LikesViewModel(
    private val getLikedThingsUseCase: GetLikedThingsUseCase,
    private val saveLikedThingUseCase: SaveLikedThingUseCase,
    private val removeLikedThingUseCase: RemoveLikedThingUseCase,
    coroutinesDispatchers: CoroutinesDispatchers
) : LoaderViewModel<LikesViewState, LikesViewAction, LikesViewIntent>(coroutinesDispatchers) {

    override val initialViewState = LikesViewState()

    private var thingList: List<Thing> = emptyList()

    override fun sendIntent(intent: LikesViewIntent) = when (intent) {
        is LikesViewIntent.OnLikeThingClick -> handleLikeClick(intent.thingModel)
        is LikesViewIntent.OnThingClick -> handleItemClick(intent.thingModel)
    }

    private fun handleItemClick(thingModel: ThingModel) {
        TODO("Not yet implemented")
    }

    private fun handleLikeClick(thingModel: ThingModel) {
        val previousModel = getState().items.first { it.id == thingModel.id }

        if (previousModel.liked != thingModel.liked) {

            launch {
                val thing = thingList.first { it.id == thingModel.id }
                val saved =
                    if (thingModel.liked) saveLikedThingUseCase(thing).rightOrNull() != null
                    else removeLikedThingUseCase(thing).rightOrNull() != null

                val updatedItems = getState().items.map {
                    if (it.id == thingModel.id && saved) it.copy(liked = thingModel.liked)
                    else it
                }

                setState { copy(items = updatedItems) }
            }

        }
    }

    override fun load() {
        launch {
            getLikedThingsUseCase().fold({ likedThings ->
                thingList = likedThings
                setState { copy(items = likedThings.map { it.toPresentation(true) }) }
            }, {
                dispatchError(Throwable())
                setState { copy(items = emptyList()) }
            })
        }
    }

}
