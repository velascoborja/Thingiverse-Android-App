package es.borjavg.thingiverse.features.popular.presentation

import es.borjavg.domain.extensions.zip
import es.borjavg.domain.models.Thing
import es.borjavg.domain.usecases.GetLikedThingsUseCase
import es.borjavg.domain.usecases.GetPopularThingsUseCase
import es.borjavg.domain.usecases.RemoveLikedThingUseCase
import es.borjavg.domain.usecases.SaveLikedThingUseCase
import es.borjavg.presentation.*
import es.borjavg.thingiverse.features.main.ui.models.ThingModel
import es.borjavg.thingiverse.features.main.ui.models.toPresentation
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull

data class PopularViewState(
    val isLoading: Boolean = false,
    val items: List<ThingModel> = emptyList()
) : ViewState

sealed class PopularViewAction : ViewAction {
    class OpenThingDetail(val url: String) : PopularViewAction()
}

sealed class PopularViewIntent : ViewIntent {
    class OnThingClick(val thingModel: ThingModel) : PopularViewIntent()
    class OnLikeThingClick(val thingModel: ThingModel) : PopularViewIntent()
}

class PopularThingsViewModel(
    private val getPopularThingsUseCase: GetPopularThingsUseCase,
    private val saveLikedThingUseCase: SaveLikedThingUseCase,
    private val removeLikedThingUseCase: RemoveLikedThingUseCase,
    private val getLikedThingsUseCase: GetLikedThingsUseCase,
    dispatchers: CoroutinesDispatchers
) :
    LoaderViewModel<PopularViewState, PopularViewAction, PopularViewIntent>(dispatchers) {

    private var thingList: List<Thing> = emptyList()

    override val initialViewState = PopularViewState()

    override fun sendIntent(intent: PopularViewIntent) = when (intent) {
        is PopularViewIntent.OnThingClick -> handleDetailSelected(intent.thingModel)
        is PopularViewIntent.OnLikeThingClick -> handleLikedThing(intent.thingModel)
    }

    private fun handleLikedThing(thingModel: ThingModel) {
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

    private fun handleDetailSelected(thingModel: ThingModel) {
        dispatchAction(PopularViewAction.OpenThingDetail(thingModel.detailUrl))
    }

    override fun load() {
        launch {
            setState { copy(isLoading = true) }

            zip(
                async { getLikedThingsUseCase() },
                async { getPopularThingsUseCase() }
            ) { likeFlow, popularEither ->

                popularEither.fold({ popularThings ->
                    thingList = popularThings
                    val likedThings = likeFlow.firstOrNull().orEmpty()
                    val mergedThings = popularThings.map { popularThing ->
                        popularThing.toPresentation(likedThings.any { it.id == popularThing.id })
                    }
                    setState { copy(isLoading = false, items = mergedThings) }
                }, {
                    dispatchError(Throwable())
                    setState { copy(isLoading = false) }
                })
            }
        }
    }
}
