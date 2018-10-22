package org.burbridge.sandbox.api

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SandboxApi

fun main(args: Array<String>) {
    runApplication<SandboxApi>(*args) {
        setBannerMode(Banner.Mode.CONSOLE)
    }
}