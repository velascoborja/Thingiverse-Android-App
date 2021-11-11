package es.borjavg.data.api.datasources

import es.borjavg.data.api.model.toDomain
import es.borjavg.data.api.services.ThingsService
import es.borjavg.domain.Either
import es.borjavg.domain.Left
import es.borjavg.domain.Right
import es.borjavg.domain.models.Thing
import retrofit2.await
import retrofit2.awaitResponse
import javax.inject.Inject

class ThingsApiDataSource @Inject constructor(
    private val thingsService: ThingsService
) {
    suspend fun getPopularThings(): Either<List<Thing>> = runCatching {
        Right(thingsService.getPopularThings().await().map { thingData ->
            thingData.toDomain()
        })
    }.getOrElse {
        Left(Throwable())
    }
}


