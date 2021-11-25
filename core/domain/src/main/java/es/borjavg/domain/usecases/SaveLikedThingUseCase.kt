package es.borjavg.domain.usecases

import es.borjavg.domain.Either
import es.borjavg.domain.models.Thing
import es.borjavg.domain.repositories.ThingsRepository
import javax.inject.Inject

interface SaveLikedThingUseCase {
    suspend operator fun invoke(thing: Thing): Either<Unit>

    class SaveLikedThingUseCaseImpl @Inject constructor(
        private val thingsRepository: ThingsRepository
    ) : SaveLikedThingUseCase {
        override suspend fun invoke(thing: Thing): Either<Unit> =
            thingsRepository.saveLikedThing(thing)
    }
}