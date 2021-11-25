package es.borjavg.data.db.datasources

import es.borjavg.data.db.ThingsDatabase
import es.borjavg.data.db.model.toDomain
import es.borjavg.data.db.model.toEntity
import es.borjavg.domain.models.Thing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThingsDbDataSource @Inject constructor(
    private val thingsDatabase: ThingsDatabase
) {

    fun getLikedThings(): Flow<List<Thing>> {
        return thingsDatabase.thingsDao().getAll()
            .map { likedThings -> likedThings.map { it.toDomain() } }
    }

    suspend fun saveLikedThing(thing: Thing) {
        thingsDatabase.thingsDao().insertAll(thing.toEntity())
    }

    suspend fun removeLikedThing(thing: Thing) {
        thingsDatabase.thingsDao().delete(thing.toEntity())
    }
}