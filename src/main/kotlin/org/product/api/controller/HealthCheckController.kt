package org.product.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Health-Check API Controller")
@RestController
class HealthCheckController {
    @GetMapping("/")
    fun healthCheck(): String = "OK"
}
