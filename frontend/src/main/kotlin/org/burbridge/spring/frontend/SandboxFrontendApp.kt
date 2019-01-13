package org.burbridge.spring.frontend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SandboxFrontendApp

fun main(args: Array<String>) {
    runApplication<SandboxFrontendApp>(*args)
}