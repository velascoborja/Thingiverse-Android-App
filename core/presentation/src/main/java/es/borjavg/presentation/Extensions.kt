package es.borjavg.presentation

import androidx.lifecycle.MutableLiveData

/**
 * Creates a new [MutableLiveData] with the given [initialValue]
 */
fun <T> mutableLiveDataOf(initialValue: T): MutableLiveData<T> =
    MutableLiveData<T>().apply { value = initialValue }

