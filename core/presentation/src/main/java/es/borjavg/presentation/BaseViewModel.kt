package es.borjavg.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Represents persistent state of the View
 */
interface ViewState

/**
 * Represents one time actions for the View to handle
 */
interface ViewAction

/**
 * Represents intents that views use to communicate with ViewModels
 */
interface ViewIntent

/**
 * Base [ViewModel] to extend. It is parametrized on [ViewState] and [ViewAction]
 * [ViewState] mutation must be done through [BaseViewModel.setState]
 * [ViewAction] dispatching must be done through [BaseViewModel.dispatchAction]
 */
typealias Reducer<T> = T.() -> T

abstract class BaseViewModel<V : ViewState, A : ViewAction, I : ViewIntent>(
    protected val dispatchers: CoroutinesDispatchers,
) : ViewModel() {

    protected abstract val initialViewState: V

    private val _viewState: MutableLiveData<V> by lazy { mutableLiveDataOf(initialViewState) }
    private val _viewActions: LiveEvent<A> = LiveEvent()
    private val _viewErrors: LiveEvent<Throwable> = LiveEvent()

    val viewState: LiveData<V> by lazy { _viewState }
    val viewActions: LiveData<A> by lazy { _viewActions }
    val viewErrors: LiveData<Throwable> by lazy { _viewErrors }

    /**
     * Returns the current state of the View. Its is safe to call
     * as it guaranteed to always return a non null instance of [ViewState]
     */
    fun getState(): V = viewState.value ?: throw IllegalStateException()

    /**
     * Mutates the current [ViewState] via [reduce] function and notify
     * [viewState] observers
     */
    protected fun setState(reduce: Reducer<V>) {
        _viewState.value = reduce(getState())
    }

    /**
     * Dispatches the given [ViewAction] to [viewActions] observers
     */
    protected fun dispatchAction(action: A) {
        _viewActions.value = action
    }

    /**
     * Dispatches the given [Throwable] to [viewErrors] observers
     */
    protected fun dispatchError(error: Throwable) {
        _viewErrors.value = error
    }

    abstract fun sendIntent(intent: I)

    /**
     * Launches the given suspend block in the scope of this ViewModel
     */
    protected fun launch(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(block = block)
}
