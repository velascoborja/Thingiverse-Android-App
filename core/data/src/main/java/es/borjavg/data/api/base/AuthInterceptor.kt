package es.borjavg.data.api.base

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        // TODO include common data to all requests

        val request = chain.request().newBuilder()
        request.addHeader("KeyHeaderOrigin", "HeaderOrigin")
        return chain.proceed(request.build())
    }
}
