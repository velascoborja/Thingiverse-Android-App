package es.borjavg.thingiverse.features.popular.presentation

import es.borjavg.domain.models.Thing
import es.borjavg.domain.usecases.GetPopularThingsUseCase
import es.borjavg.domain.usecases.SaveLikedThingsUseCase
import es.borjavg.presentation.*
import es.borjavg.thingiverse.features.main.ui.models.ThingModel
import es.borjavg.thingiverse.features.main.ui.models.toPresentation

data class PopularViewState(
    val isLoading: Boolean = false,
    val items: List<ThingModel> = emptyList()
) : ViewState

sealed class PopularViewAction : ViewAction {
    class OpenThingDetail(val url: String) : PopularViewAction()
}

sealed class PopularViewIntent : ViewIntent {
    class OnThingClick(val thingModel: ThingModel) : PopularViewIntent()
}

class PopularThingsViewModel(
    private val getPopularThingsUseCase: GetPopularThingsUseCase,
    private val saveLikedThingsUseCase: SaveLikedThingsUseCase,
    dispatchers: CoroutinesDispatchers
) :
    LoaderViewModel<PopularViewState, PopularViewAction, PopularViewIntent>(dispatchers) {

    private var thingList: List<Thing> = emptyList()

    override val initialViewState: PopularViewState
        get() = PopularViewState()

    override fun sendIntent(intent: PopularViewIntent) = when (intent) {
        is PopularViewIntent.OnThingClick -> {

            launch {
                val selectedThing = thingList.first { it.id == intent.thingModel.id }
                saveLikedThingsUseCase(selectedThing)
            }


            handleDetailSelected(intent.thingModel)
        }
    }

    private fun handleDetailSelected(thingModel: ThingModel) {
        /*dispatchAction(PopularViewAction.OpenThingDetail(thingModel.detailUrl))*/
    }

    override fun load() {
        launch {
            setState { copy(isLoading = true) }
            getPopularThingsUseCase().fold({ things ->
                thingList = things
                setState { copy(isLoading = false, items = things.map { it.toPresentation() }) }
            }, {
                dispatchError(Throwable())
                setState { copy(isLoading = false) }
            })
        }
    }
}
