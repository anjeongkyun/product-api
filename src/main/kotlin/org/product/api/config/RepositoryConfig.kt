package org.product.api.config

import org.product.persistence.repositories.BrandJpaRepository
import org.product.persistence.repositories.BrandRepositoryImpl
import org.product.persistence.repositories.ProductJpaRepository
import org.product.persistence.repositories.ProductRepositoryImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RepositoryConfig {
    @Bean
    fun productRepository(productJpaRepository: ProductJpaRepository) = ProductRepositoryImpl(productJpaRepository)

    @Bean
    fun brandRepository(brandJpaRepository: BrandJpaRepository) = BrandRepositoryImpl(brandJpaRepository)
}
