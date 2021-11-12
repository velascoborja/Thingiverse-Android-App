package es.borjavg.thingiverse.features.main.presentation

import es.borjavg.presentation.*

sealed class MainViewIntent : ViewIntent {
    object OnNavigateToLikesClick : MainViewIntent()
    object OnNavigateToPopularClick : MainViewIntent()
}

data class MainViewState(
    val showToolbar: Boolean
) : ViewState

sealed class MainViewActions : ViewAction {
    object ShowLikesTab : MainViewActions()
    object ShowPopularTab : MainViewActions()
}


class MainViewModel(
    dispatchers: CoroutinesDispatchers
) : BaseViewModel<MainViewState, MainViewActions, MainViewIntent>(dispatchers) {
    override val initialViewState: MainViewState get() = MainViewState(showToolbar = true)

    override fun sendIntent(intent: MainViewIntent) =
        when (intent) {
            MainViewIntent.OnNavigateToLikesClick -> dispatchAction(MainViewActions.ShowLikesTab)
            MainViewIntent.OnNavigateToPopularClick -> dispatchAction(MainViewActions.ShowPopularTab)
        }
}