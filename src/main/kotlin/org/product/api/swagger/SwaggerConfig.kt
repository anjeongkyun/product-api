package org.product.api.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI =
        OpenAPI()
            .components(Components())
            .info(configurationInfo())

    private fun configurationInfo(): Info =
        Info()
            .title("[MUSINSA] Java(Kotlin) Backend Engineer 과제 API 문서")
            .description("Product API for managing products and brands.")
            .version("1.0.0")
}
