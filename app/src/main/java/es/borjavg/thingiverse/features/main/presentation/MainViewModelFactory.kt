package es.borjavg.thingiverse.features.main.presentation

import es.borjavg.presentation.CoroutinesDispatchers
import es.borjavg.presentation.ViewModelFactory
import javax.inject.Inject

class MainViewModelFactory @Inject constructor(
    private val coroutinesDispatchers: CoroutinesDispatchers
) : ViewModelFactory<MainViewModel>() {

    override fun create() = MainViewModel(
        dispatchers = coroutinesDispatchers
    )
}