package org.burbridge.spring.frontend.client

import org.apache.http.HttpHost
import org.springframework.web.client.RestTemplate
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class RestTemplateFactory : FactoryBean<RestTemplate>, InitializingBean {

    @Value("\${sandbox.api.scheme}")
    lateinit var hostScheme: String

    @Value("\${sandbox.api.host}")
    lateinit var hostName: String

    @Value("\${sandbox.api.port}")
    lateinit var hostPort: String

    private var restTemplate: RestTemplate? = null

    override fun getObject(): RestTemplate? {
        return restTemplate
    }

    override fun getObjectType(): Class<RestTemplate>? {
        return RestTemplate::class.java
    }

    override fun isSingleton(): Boolean {
        return true
    }

    override fun afterPropertiesSet() {
        val host = HttpHost(hostName, hostPort.toInt(), hostScheme)
        restTemplate = RestTemplate(HttpComponentsClientHttpRequestFactoryBasicAuth(host))
    }
}