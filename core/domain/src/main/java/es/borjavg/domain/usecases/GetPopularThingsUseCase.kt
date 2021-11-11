package es.borjavg.domain.usecases

import es.borjavg.domain.Either
import es.borjavg.domain.models.Thing
import es.borjavg.domain.repositories.ThingsRepository
import javax.inject.Inject

class GetPopularThingsUseCase @Inject constructor(
    private val thingsRepository: ThingsRepository
) {
    suspend operator fun invoke(): Either<List<Thing>> = thingsRepository.getPopularThings()
}