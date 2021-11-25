package es.borjavg.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import es.borjavg.data.db.model.ThingEntity

@Dao
interface ThingsDao {

    @Query("SELECT * FROM ThingEntity")
    fun getAll(): List<ThingEntity>

    @Query("SELECT * FROM ThingEntity WHERE id LIKE :id LIMIT 1")
    fun getThingById(id: String): ThingEntity

    @Insert
    fun insertAll(vararg thingEntity: ThingEntity)

    @Delete
    fun delete(thingEntity: ThingEntity)

}