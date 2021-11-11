package es.borjavg.thingiverse.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.borjavg.presentation.CoroutinesDispatchers
import es.borjavg.presentation.CoroutinesDispatchersImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ApplicationModule {

    @Singleton
    @Binds
    fun providesCoroutinesDispatchers(impl: CoroutinesDispatchersImpl): CoroutinesDispatchers

}