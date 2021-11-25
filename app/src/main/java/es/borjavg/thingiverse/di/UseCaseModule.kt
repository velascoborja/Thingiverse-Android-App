package es.borjavg.thingiverse.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import es.borjavg.domain.usecases.GetLikedThingsUseCase
import es.borjavg.domain.usecases.GetPopularThingsUseCase
import es.borjavg.domain.usecases.RemoveLikedThingUseCase
import es.borjavg.domain.usecases.SaveLikedThingUseCase

@Module
@InstallIn(ActivityComponent::class)
interface UseCaseModule {

    @ActivityScoped
    @Binds
    fun providePopularUseCase(impl: GetPopularThingsUseCase.GetPopularThingsUseCaseImpl): GetPopularThingsUseCase

    @ActivityScoped
    @Binds
    fun provideLikeUseCase(impl: SaveLikedThingUseCase.SaveLikedThingUseCaseImpl): SaveLikedThingUseCase

    @ActivityScoped
    @Binds
    fun provideRemovedLikedThingsUseCase(impl: RemoveLikedThingUseCase.RemoveLikedThingUseCaseImpl): RemoveLikedThingUseCase

    @ActivityScoped
    @Binds
    fun provideGetLikedThingsUseCase(impl: GetLikedThingsUseCase.GetLikedThingsUseCaseImpl): GetLikedThingsUseCase
}