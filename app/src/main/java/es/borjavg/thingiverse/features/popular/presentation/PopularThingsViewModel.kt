package es.borjavg.thingiverse.features.popular.presentation

import es.borjavg.domain.usecases.GetPopularThingsUseCase
import es.borjavg.presentation.*
import es.borjavg.thingiverse.features.main.ui.models.ThingModel
import es.borjavg.thingiverse.features.main.ui.models.toPresentation

data class PopularViewState(
    val isLoading: Boolean = false,
    val items: List<ThingModel> = emptyList()
) : ViewState

sealed class PopularViewAction : ViewAction

sealed class PopularViewIntent : ViewIntent {
    object OnThingClick : PopularViewIntent()
}


class PopularThingsViewModel(
    private val getPopularThingsUseCase: GetPopularThingsUseCase,
    dispatchers: CoroutinesDispatchers
) :
    LoaderViewModel<PopularViewState, PopularViewAction, PopularViewIntent>(dispatchers) {

    override val initialViewState: PopularViewState
        get() = PopularViewState()

    override fun sendIntent(intent: PopularViewIntent) = when (intent) {
        PopularViewIntent.OnThingClick -> handleDetailSelected()
    }

    private fun handleDetailSelected() {
        TODO("Handle thing click ❤️")
    }

    override fun load() {
        launch {
            setState { copy(isLoading = true) }
            getPopularThingsUseCase().fold({ things ->
                setState { copy(isLoading = false, items = things.map { it.toPresentation() }) }
            }, {
                dispatchError(Throwable())
                setState { copy(isLoading = false) }
            })
        }
    }
}
