package es.borjavg.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import es.borjavg.data.db.model.ThingEntity

@Dao
interface ThingsDao {

    @Query("SELECT * FROM thingentity")
    suspend fun getAll(): List<ThingEntity>

    @Query("SELECT * FROM thingentity WHERE id LIKE :id LIMIT 1")
    suspend fun getThingById(id: String): ThingEntity

    @Insert
    suspend fun insertAll(vararg thingEntity: ThingEntity)

    @Delete
    suspend fun delete(thingEntity: ThingEntity)

}