package es.borjavg.data.repositories

import es.borjavg.data.api.datasources.ThingsApiDataSource
import es.borjavg.data.db.datasources.ThingsDbDataSource
import es.borjavg.domain.Either
import es.borjavg.domain.Left
import es.borjavg.domain.Right
import es.borjavg.domain.models.Thing
import es.borjavg.domain.repositories.ThingsRepository
import javax.inject.Inject

class ThingsRepositoryImpl @Inject constructor(
    private val thingsApiDataSource: ThingsApiDataSource,
    private val thingsDbDataSource: ThingsDbDataSource
) : ThingsRepository {

    override suspend fun getPopularThings(): Either<List<Thing>> =
        thingsApiDataSource.getPopularThings()

    override suspend fun saveLikedThing(thing: Thing): Either<Unit> = runCatching {
        thingsDbDataSource.saveLikedThing(thing)
        Right(Unit)
    }.getOrElse { Left(Throwable()) }

    override suspend fun removeLikedThing(thing: Thing): Either<Unit> = runCatching {
        thingsDbDataSource.removeLikedThing(thing)
        Right(Unit)
    }.getOrElse { Left(Throwable()) }

    override suspend fun getLikedThings(): Either<List<Thing>> = runCatching {
        Right(thingsDbDataSource.getLikedThings())
    }.getOrElse { Left(Throwable()) }
}