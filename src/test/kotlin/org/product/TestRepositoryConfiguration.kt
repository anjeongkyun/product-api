package org.product

import org.product.domainmodel.repository.BrandRepository
import org.product.domainmodel.repository.ProductRepository
import org.product.persistence.repositories.BrandJpaRepository
import org.product.persistence.repositories.BrandRepositoryImpl
import org.product.persistence.repositories.ProductJpaRepository
import org.product.persistence.repositories.ProductRepositoryImpl
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestRepositoryConfiguration {
    @Bean
    fun brandRepository(brandJpaRepository: BrandJpaRepository): BrandRepository = BrandRepositoryImpl(brandJpaRepository)

    @Bean
    fun productRepository(productJpaRepository: ProductJpaRepository): ProductRepository = ProductRepositoryImpl(productJpaRepository)
}
