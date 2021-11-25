package es.borjavg.domain.usecases

import es.borjavg.domain.models.Thing
import es.borjavg.domain.repositories.ThingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetLikedThingsUseCase {
    suspend operator fun invoke(): Flow<List<Thing>>

    class GetLikedThingsUseCaseImpl @Inject constructor(
        private val thingsRepository: ThingsRepository
    ) : GetLikedThingsUseCase {
        override suspend fun invoke(): Flow<List<Thing>> =
            thingsRepository.getLikedThings()
    }
}