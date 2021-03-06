package es.borjavg.thingiverse.util

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

class MockServerDispatcher {
    /**
     * Return ok response from mock server
     */
    internal inner class RequestDispatcher(val delayResponse: Boolean = false) : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/popular" -> MockResponse().setResponseCode(200)
                    .setBody(getJsonContent("popular_things.json"))
                    .apply { if (delayResponse) setBodyDelay(5, TimeUnit.SECONDS) }
                else -> MockResponse().setResponseCode(400)
            }
        }
    }

    /**
     * Return error response from mock server
     */
    internal inner class ErrorDispatcher : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(400)
        }
    }

    private fun getJsonContent(fileName: String) =
        InputStreamReader(javaClass.classLoader?.getResourceAsStream(fileName)).use { it.readText() }
}