package es.borjavg.data.db.dao

import androidx.room.*
import es.borjavg.data.db.model.ThingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ThingsDao {

    @Query("SELECT * FROM ThingEntity")
    fun getAll(): Flow<List<ThingEntity>>

    @Query("SELECT * FROM ThingEntity WHERE id LIKE :id LIMIT 1")
    suspend fun getThingById(id: String): ThingEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg thingEntity: ThingEntity)

    @Delete
    suspend fun delete(thingEntity: ThingEntity)

}