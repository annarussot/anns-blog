package io.anna.annsblog

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AppController {
    @GetMapping("/home", "/")
    fun home(): String = "home"
}
