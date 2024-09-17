package org.product.api.config

import org.product.persistence.repositories.ProductJpaRepository
import org.product.persistence.repositories.ProductReaderImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ReaderConfig {
    @Bean
    fun productReader(productJpaRepository: ProductJpaRepository) = ProductReaderImpl(productJpaRepository)
}
