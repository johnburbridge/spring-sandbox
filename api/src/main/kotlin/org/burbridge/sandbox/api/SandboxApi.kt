package org.burbridge.sandbox.api
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@SpringBootApplication
class SandboxApi

fun main(args: Array<String>) {
    runApplication<SandboxApi>(*args) {
        setBannerMode(Banner.Mode.CONSOLE)
    }
}