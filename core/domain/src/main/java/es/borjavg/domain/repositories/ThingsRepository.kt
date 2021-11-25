package es.borjavg.domain.repositories

import es.borjavg.domain.Either
import es.borjavg.domain.models.Thing
import kotlinx.coroutines.flow.Flow

interface ThingsRepository {
    suspend fun getPopularThings(): Either<List<Thing>>
    suspend fun saveLikedThing(thing: Thing): Either<Unit>
    suspend fun removeLikedThing(thing: Thing): Either<Unit>
    fun getLikedThings(): Flow<List<Thing>>
}