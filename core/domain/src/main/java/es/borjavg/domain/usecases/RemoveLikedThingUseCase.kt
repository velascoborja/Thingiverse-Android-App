package es.borjavg.domain.usecases

import es.borjavg.domain.Either
import es.borjavg.domain.models.Thing
import es.borjavg.domain.repositories.ThingsRepository
import javax.inject.Inject

interface RemoveLikedThingUseCase {
    suspend operator fun invoke(thing: Thing): Either<Unit>

    class RemoveLikedThingUseCaseImpl @Inject constructor(
        private val thingsRepository: ThingsRepository
    ) : RemoveLikedThingUseCase {
        override suspend fun invoke(thing: Thing): Either<Unit> =
            thingsRepository.removeLikedThing(thing)
    }
}