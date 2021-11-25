package es.borjavg.data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.borjavg.data.db.ThingsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideThingsDatabase(
        applicationContext: Application,
        @DbName dbName: String
    ) = Room.databaseBuilder(
        applicationContext,
        ThingsDatabase::class.java, dbName
    ).build()

    @Provides
    @Singleton
    @DbName
    fun provideThingDbName(): String = "things_database"
}

