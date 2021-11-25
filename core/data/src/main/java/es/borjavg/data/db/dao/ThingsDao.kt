package es.borjavg.data.db.dao

import androidx.room.*
import es.borjavg.data.db.model.ThingEntity

@Dao
interface ThingsDao {

    @Query("SELECT * FROM ThingEntity")
    suspend fun getAll(): List<ThingEntity>

    @Query("SELECT * FROM ThingEntity WHERE id LIKE :id LIMIT 1")
    suspend fun getThingById(id: String): ThingEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg thingEntity: ThingEntity)

    @Delete
    suspend fun delete(thingEntity: ThingEntity)

}