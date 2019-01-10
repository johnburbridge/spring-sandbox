package org.burbridge.spring.frontend.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class WebUIController {

    @RequestMapping(path =  ["/", "/home"])
    fun home(model: Model): String {
        return "home"
    }

    @RequestMapping("/login")
    fun login(model: Model): String {
        return "login"
    }

    @RequestMapping("/admin")
    fun admin(model: Model): String {
        return "admin"
    }
}
