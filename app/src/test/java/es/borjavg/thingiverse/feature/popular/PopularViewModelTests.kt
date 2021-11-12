package es.borjavg.thingiverse.feature.popular

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import es.borjavg.domain.Either
import es.borjavg.domain.Left
import es.borjavg.domain.Right
import es.borjavg.domain.models.Thing
import es.borjavg.thingiverse.base.BaseViewModelTests
import es.borjavg.thingiverse.base.TestObserver
import es.borjavg.thingiverse.features.popular.presentation.PopularThingsViewModel
import es.borjavg.thingiverse.features.popular.presentation.PopularViewState
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

    private val mockData = (0..15).map { index ->
        Thing(
            id = "Id$index",
            thumb = "thumbnail",
            name = "Name$index",
            likeCount = index,
            publicUrl = "https://example.org"
        )
    }

    private fun buildViewModel(thingsResponse: Either<List<Thing>> = Right(mockData)) =
        PopularThingsViewModel(
            getPopularThingsUseCase = mock { onBlocking { invoke() } doReturn (thingsResponse) },
            dispatchers = dispatchers
        )
}