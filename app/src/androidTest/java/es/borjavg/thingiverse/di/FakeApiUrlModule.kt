package es.borjavg.thingiverse.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.borjavg.data.di.BaseUrl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FakeApiUrlModule {

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = "http://localhost:8080/"
}