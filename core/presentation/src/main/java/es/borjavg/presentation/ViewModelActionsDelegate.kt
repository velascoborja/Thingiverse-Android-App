package es.borjavg.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface ViewModelActionsDelegate<V : ViewState, A : ViewAction> {
    val launch: (suspend CoroutineScope.() -> Unit) -> Job
    val setState: (Reducer<V>) -> Unit
    val getState: () -> V
    val dispatchAction: (A) -> Unit
    val dispatchError: (PresentationError) -> Unit

    companion object {
        operator fun <V : ViewState, A : ViewAction> invoke(
            launch: (suspend CoroutineScope.() -> Unit) -> Job,
            setState: (Reducer<V>) -> Unit,
            getState: () -> V,
            dispatchAction: (A) -> Unit,
            dispatchError: (PresentationError) -> Unit
        ) = object : ViewModelActionsDelegate<V, A> {
            override val launch: (suspend CoroutineScope.() -> Unit) -> Job = launch
            override val setState: (Reducer<V>) -> Unit = setState
            override val getState: () -> V = getState
            override val dispatchError: (PresentationError) -> Unit = dispatchError
            override val dispatchAction: (A) -> Unit = dispatchAction
        }
    }
}
