package es.borjavg.presentation

/**
 * [BaseViewModel] that performs data loading
 */
abstract class LoaderViewModel<V : ViewState, A : ViewAction>(
    errorProvider: ErrorProvider,
    dispatchers: CoroutinesDispatchers
) : BaseViewModel<V, A>(errorProvider, dispatchers) {

    abstract fun load()
}
