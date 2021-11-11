package es.borjavg.domain.repositories

import es.borjavg.domain.Either
import es.borjavg.domain.models.Thing

interface ThingsRepository {
    suspend fun getPopularThings(): Either<List<Thing>>
}