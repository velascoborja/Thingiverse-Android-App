package es.borjavg.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.borjavg.data.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiUrlModule {

    @Provides
    @BaseUrl
    @Singleton
    fun provideBaseUrl() = BuildConfig.API_BASE_URL

}