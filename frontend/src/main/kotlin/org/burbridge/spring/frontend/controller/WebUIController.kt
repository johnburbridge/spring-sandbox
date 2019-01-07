package org.burbridge.spring.frontend.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class WebUIController {

    @RequestMapping("/")
    fun home(model: Model): String {
        return "home"
    }
}
