package es.borjavg.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.borjavg.data.BuildConfig
import es.borjavg.data.api.services.ThingsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideMoviesService(retrofit: Retrofit): ThingsService =
        retrofit.create(ThingsService::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        @BaseUrl url: String,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val request = chain.request().newBuilder().also {
                    it.addHeader("authorization", "Bearer ${BuildConfig.THINGIVERSE_TOKEN}")
                }

                chain.proceed(request.build())
            }

            if (BuildConfig.DEBUG) addInterceptor(HttpLoggingInterceptor().also {
                it.setLevel(HttpLoggingInterceptor.Level.HEADERS)
            })
        }.build()
}