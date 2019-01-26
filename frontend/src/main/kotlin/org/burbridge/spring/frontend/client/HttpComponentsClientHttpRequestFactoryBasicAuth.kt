package org.burbridge.spring.frontend.client

import org.apache.http.HttpHost
import org.apache.http.client.protocol.HttpClientContext
import org.apache.http.impl.auth.BasicScheme
import org.apache.http.impl.client.BasicAuthCache
import org.apache.http.protocol.BasicHttpContext
import org.apache.http.protocol.HttpContext
import org.springframework.http.HttpMethod
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import java.net.URI


class HttpComponentsClientHttpRequestFactoryBasicAuth(var host: HttpHost) : HttpComponentsClientHttpRequestFactory() {

    override fun createHttpContext(httpMethod: HttpMethod, uri: URI): HttpContext {
        return createHttpContext()
    }

    private fun createHttpContext(): HttpContext {
        val authCache = BasicAuthCache()
        authCache.put(host, BasicScheme())

        val localContext = BasicHttpContext()
        localContext.setAttribute(HttpClientContext.AUTH_CACHE, authCache)
        return localContext
    }
}
