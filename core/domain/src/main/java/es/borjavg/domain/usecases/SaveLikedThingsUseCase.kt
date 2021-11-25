package es.borjavg.domain.usecases

import es.borjavg.domain.Either
import es.borjavg.domain.models.Thing
import es.borjavg.domain.repositories.ThingsRepository
import javax.inject.Inject

interface SaveLikedThingsUseCase {
    suspend operator fun invoke(thing: Thing): Either<Unit>

    class SaveLikedThingsUseCaseImpl @Inject constructor(
        private val thingsRepository: ThingsRepository
    ) : SaveLikedThingsUseCase {
        override suspend fun invoke(thing: Thing): Either<Unit> =
            thingsRepository.saveLikedThing(thing)
    }
}