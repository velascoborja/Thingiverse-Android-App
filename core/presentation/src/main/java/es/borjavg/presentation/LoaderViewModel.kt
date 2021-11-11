package es.borjavg.presentation

/**
 * [BaseViewModel] that performs data loading
 */
abstract class LoaderViewModel<V : ViewState, A : ViewAction, I : ViewIntent>(
    dispatchers: CoroutinesDispatchers
) : BaseViewModel<V, A, I>( dispatchers) {

    abstract fun load()
}
