package es.borjavg.domain.extensions

import kotlinx.coroutines.Deferred

suspend fun <A, B, C> zip(
    def1: Deferred<A>,
    def2: Deferred<B>,
    biFunction: suspend (A, B) -> C
): C = biFunction(def1.await(), def2.await())
