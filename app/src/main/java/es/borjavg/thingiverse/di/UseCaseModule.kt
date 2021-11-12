package es.borjavg.thingiverse.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import es.borjavg.domain.usecases.GetPopularThingsUseCase

@Module
@InstallIn(ActivityComponent::class)
interface UseCaseModule {

    @ActivityScoped
    @Binds
    fun providePopularUseCase(impl: GetPopularThingsUseCase.GetPopularThingsUseCaseImpl): GetPopularThingsUseCase

}