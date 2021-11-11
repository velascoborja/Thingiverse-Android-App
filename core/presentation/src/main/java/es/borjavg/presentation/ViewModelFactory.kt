package es.borjavg.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class ViewModelFactory<T : ViewModel> : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    final override fun <T : ViewModel> create(modelClass: Class<T>): T = create() as T

    abstract fun create(): T
}
