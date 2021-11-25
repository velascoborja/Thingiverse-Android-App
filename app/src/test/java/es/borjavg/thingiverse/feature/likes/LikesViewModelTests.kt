package es.borjavg.thingiverse.feature.likes

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import es.borjavg.domain.Right
import es.borjavg.domain.models.Thing
import es.borjavg.thingiverse.base.BaseViewModelTests
import es.borjavg.thingiverse.base.TestObserver
import es.borjavg.thingiverse.features.likes.presentation.LikesViewAction
import es.borjavg.thingiverse.features.likes.presentation.LikesViewIntent
import es.borjavg.thingiverse.features.likes.presentation.LikesViewModel
import es.borjavg.thingiverse.features.likes.presentation.LikesViewState
import es.borjavg.thingiverse.features.main.ui.models.toPresentation
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert
import org.junit.Test

class LikesViewModelTests : BaseViewModelTests() {

    @Test
    fun `WHEN no items are liked THEN empty view is shown`() {
        val viewModel = buildViewModel(emptyItems = true)
        val observer = TestObserver<LikesViewState>()
        viewModel.viewState.observeForever(observer)

        viewModel.load()

        Assert.assertTrue(observer.lastValue.items.isEmpty())
    }

    @Test
    fun `WHEN available liked items THEN view is notified`() {
        val viewModel = buildViewModel()
        val observer = TestObserver<LikesViewState>()
        viewModel.viewState.observeForever(observer)

        viewModel.load()

        Assert.assertTrue(observer.lastValue.items.isNotEmpty())
    }

    @Test
    fun `GIVEN loaded items WHEN item is clicked THEN items detail is shown`() {
        val viewModel = buildViewModel()
        val observer = mock<Observer<LikesViewAction>>()
        val captor = argumentCaptor<LikesViewAction>()
        val thingModel = mockData.first().toPresentation(true)
        viewModel.viewActions.observeForever(observer)

        viewModel.load()
        viewModel.sendIntent(LikesViewIntent.OnThingClick(thingModel))

        verify(observer, atLeastOnce()).onChanged(captor.capture())
        Assert.assertTrue(captor.lastValue is LikesViewAction.OpenThingDetail)
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

    private fun buildViewModel(emptyItems: Boolean = false) =
        LikesViewModel(
            getLikedThingsUseCase = mock { onBlocking { invoke() } doReturn flowOf(if (emptyItems) emptyList() else mockData) },
            removeLikedThingUseCase = mock { onBlocking { invoke(any()) } doReturn Right(Unit) },
            coroutinesDispatchers = dispatchers
        )
}