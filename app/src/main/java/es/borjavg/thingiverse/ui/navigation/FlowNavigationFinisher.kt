package es.borjavg.thingiverse.ui.navigation

import android.content.Context
import androidx.fragment.app.Fragment

interface FlowNavigationFinisher {

    fun finish()
}

interface ParamFlowNavigationFinisher<T> {

    fun finish(value: T)
}

val Context.asFlowNavigationFinisher: FlowNavigationFinisher? get() = this as? FlowNavigationFinisher

val Fragment.asFlowNavigationFinisher: FlowNavigationFinisher?
    get() = this as? FlowNavigationFinisher ?: requireContext().asFlowNavigationFinisher

@Suppress("UNCHECKED_CAST")
fun <T> Context.asParamFlowNavigationFinisher(): ParamFlowNavigationFinisher<T>? =
    this as? ParamFlowNavigationFinisher<T>

@Suppress("UNCHECKED_CAST")
fun <T> Fragment.asParamFlowNavigationFinisher(): ParamFlowNavigationFinisher<T>? =
    this as? ParamFlowNavigationFinisher<T> ?: requireContext().asParamFlowNavigationFinisher()
