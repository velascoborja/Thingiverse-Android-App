package es.borjavg.data.db.datasources

import es.borjavg.data.db.ThingsDatabase
import es.borjavg.data.db.model.toDomain
import es.borjavg.data.db.model.toEntity
import es.borjavg.domain.models.Thing
import javax.inject.Inject

class ThingsDbDataSource @Inject constructor(
    private val thingsDatabase: ThingsDatabase
) {

    suspend fun getLikedThings(): List<Thing> {
        return thingsDatabase.thingsDao().getAll().map { it.toDomain() }
    }

    suspend fun saveLikedThing(thing: Thing) {
        thingsDatabase.thingsDao().insertAll(thing.toEntity())
    }
}