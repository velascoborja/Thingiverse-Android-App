package es.borjavg.thingiverse.feature.popular

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import es.borjavg.domain.Either
import es.borjavg.domain.Left
import es.borjavg.domain.Right
import es.borjavg.domain.models.Thing
import es.borjavg.thingiverse.base.BaseViewModelTests
import es.borjavg.thingiverse.base.TestObserver
import es.borjavg.thingiverse.features.main.ui.models.toPresentation
import es.borjavg.thingiverse.features.popular.presentation.PopularThingsViewModel
import es.borjavg.thingiverse.features.popular.presentation.PopularViewAction
import es.borjavg.thingiverse.features.popular.presentation.PopularViewIntent
import es.borjavg.thingiverse.features.popular.presentation.PopularViewState
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Test

class PopularViewModelTests : BaseViewModelTests() {

    @Test
    fun `WHEN view is loading THEN the view is notified`() {
        val viewModel = buildViewModel()
        val observer = TestObserver<PopularViewState>()
        viewModel.viewState.observeForever(observer)

        viewModel.load()

        Assert.assertFalse(observer.firstValue.isLoading)
        Assert.assertTrue(observer.secondValue.isLoading)
        Assert.assertFalse(observer.lastValue.isLoading)
    }

    @Test
    fun `WHEN popular things fail THEN the view shows an error`() {
        val viewModel = buildViewModel(thingsResponse = Left(Throwable()))
        val viewErrorsObserver = mock<Observer<Throwable>>()
        viewModel.viewErrors.observeForever(viewErrorsObserver)

        viewModel.load()

        verify(viewErrorsObserver).onChanged(any())
    }

    @Test
    fun `WHEN view loads THEN items are shown`() {
        val viewModel = buildViewModel()
        val observer = TestObserver<PopularViewState>()
        viewModel.viewState.observeForever(observer)

        viewModel.load()

        Assert.assertTrue(observer.lastValue.items.isNotEmpty())
    }

    @Test
    fun `GIVEN loaded items WHEN item is clicked THEN items detail is shown`() {
        val viewModel = buildViewModel()
        val observer = mock<Observer<PopularViewAction>>()
        val captor = argumentCaptor<PopularViewAction>()
        val thingModel = mockData.first().toPresentation(true)
        viewModel.viewActions.observeForever(observer)

        viewModel.load()
        viewModel.sendIntent(PopularViewIntent.OnThingClick(thingModel))

        verify(observer, atLeastOnce()).onChanged(captor.capture())
        Assert.assertTrue(captor.lastValue is PopularViewAction.OpenThingDetail)
    }

    private val mockData = (0..15).map { index ->
        Thing(
            id = "Id$index",
            thumb = "thumbnail",
            name = "Name$index",
            commentCount = index,
            publicUrl = "https://example.org"
        )
    }

    private val likedMockData = listOf(mockData.random())

    private fun buildViewModel(thingsResponse: Either<List<Thing>> = Right(mockData)) =
        PopularThingsViewModel(
            getPopularThingsUseCase = mock { onBlocking { invoke() } doReturn (thingsResponse) },
            saveLikedThingUseCase = mock { onBlocking { invoke(any()) } doReturn Right(Unit) },
            removeLikedThingUseCase = mock { onBlocking { invoke(any()) } doReturn Right(Unit) },
            getLikedThingsUseCase = mock { onBlocking { invoke() } doReturn flowOf(likedMockData) },
            dispatchers = dispatchers
        )
}