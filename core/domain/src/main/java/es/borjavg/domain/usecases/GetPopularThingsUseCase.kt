package es.borjavg.domain.usecases

import es.borjavg.domain.Either
import es.borjavg.domain.models.Thing
import es.borjavg.domain.repositories.ThingsRepository
import javax.inject.Inject

interface GetPopularThingsUseCase {
    suspend operator fun invoke(): Either<List<Thing>>

    class GetPopularThingsUseCaseImpl @Inject constructor(
        private val thingsRepository: ThingsRepository
    ) : GetPopularThingsUseCase {
        override suspend fun invoke(): Either<List<Thing>> =
            thingsRepository.getPopularThings()
    }
}