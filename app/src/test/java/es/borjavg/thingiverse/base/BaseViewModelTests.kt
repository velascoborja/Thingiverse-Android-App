package es.borjavg.thingiverse.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import es.borjavg.presentation.CoroutinesDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Rule
import org.mockito.Mockito

abstract class BaseViewModelTests {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    val dispatchers: CoroutinesDispatchers =
        TestCoroutinesDispatchers(coroutinesTestRule.testDispatcher)

    @After
    fun validate() {
        /*Ensure misusing errors are showing in the correct tests and not at the end of the
        execution chain*/
        Mockito.validateMockitoUsage()
    }
}

@ExperimentalCoroutinesApi
class TestCoroutinesDispatchers(
    testCoroutineDispatcher: TestCoroutineDispatcher
) : CoroutinesDispatchers {
    override val Main: CoroutineDispatcher = testCoroutineDispatcher
    override val IO: CoroutineDispatcher = testCoroutineDispatcher
    override val Default: CoroutineDispatcher = testCoroutineDispatcher
    override val Unconfined: CoroutineDispatcher = testCoroutineDispatcher
}
