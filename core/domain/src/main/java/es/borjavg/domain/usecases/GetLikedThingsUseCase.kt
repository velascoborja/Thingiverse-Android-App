package es.borjavg.domain.usecases

import es.borjavg.domain.Either
import es.borjavg.domain.models.Thing
import es.borjavg.domain.repositories.ThingsRepository
import javax.inject.Inject

interface GetLikedThingsUseCase {
    suspend operator fun invoke(): Either<List<Thing>>

    class GetLikedThingsUseCaseImpl @Inject constructor(
        private val thingsRepository: ThingsRepository
    ) : GetLikedThingsUseCase {
        override suspend fun invoke(): Either<List<Thing>> =
            thingsRepository.getLikedThings()
    }
}