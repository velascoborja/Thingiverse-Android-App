package es.borjavg.data.repositories

import es.borjavg.data.api.datasources.ThingsApiDataSource
import es.borjavg.domain.Either
import es.borjavg.domain.models.Thing
import es.borjavg.domain.repositories.ThingsRepository
import javax.inject.Inject

class ThingsRepositoryImpl @Inject constructor(
    private val thingsApiDataSource: ThingsApiDataSource
) : ThingsRepository {

    override suspend fun getPopularThings(): Either<List<Thing>> =
        thingsApiDataSource.getPopularThings()
}