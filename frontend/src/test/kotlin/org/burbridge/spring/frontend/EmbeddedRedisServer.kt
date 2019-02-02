package org.burbridge.spring.frontend

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import redis.embedded.RedisServer
import java.io.IOException
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

private val logger = KotlinLogging.logger {}

@Component
class EmbededRedisServer {

    @Value("\${spring.redis.port}")
    private val redisPort: Int = 6379

    private var redisServer: RedisServer? = null

    @PostConstruct
    @Throws(IOException::class)
    fun startRedis() {
        logger.info { "Starting embedded Redis server on port $redisPort" }
        redisServer = RedisServer(redisPort)
        redisServer!!.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer!!.stop()
    }
}