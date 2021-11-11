package es.borjavg.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.borjavg.data.repositories.ThingsRepositoryImpl
import es.borjavg.domain.repositories.ThingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    @Singleton
    fun bindsThingsRepository(impl: ThingsRepositoryImpl): ThingsRepository

}