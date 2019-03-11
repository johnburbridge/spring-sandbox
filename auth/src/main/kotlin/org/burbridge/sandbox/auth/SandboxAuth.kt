package org.burbridge.sandbox.auth

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SandboxAuth

fun main(args: Array<String>) {
    runApplication<SandboxAuth>(*args) {
        setBannerMode(Banner.Mode.CONSOLE)
    }
}