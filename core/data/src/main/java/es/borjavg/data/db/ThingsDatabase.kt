package es.borjavg.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import es.borjavg.data.db.dao.ThingsDao
import es.borjavg.data.db.model.ThingEntity

@Database(entities = [ThingEntity::class], version = 1)
abstract class ThingsDatabase : RoomDatabase() {
    abstract fun thingsDao(): ThingsDao
}