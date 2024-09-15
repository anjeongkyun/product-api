package org.product

import org.product.domainmodel.reader.ProductReader
import org.product.domainmodel.repository.BrandRepository
import org.product.domainmodel.repository.ProductRepository
import org.product.persistence.repositories.*
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestRepositoryConfiguration {
    @Bean
    fun brandRepository(brandJpaRepository: BrandJpaRepository): BrandRepository = BrandRepositoryImpl(brandJpaRepository)

    @Bean
    fun productRepository(productJpaRepository: ProductJpaRepository): ProductRepository = ProductRepositoryImpl(productJpaRepository)

    @Bean
    fun productReader(productJpaRepository: ProductJpaRepository): ProductReader = ProductReaderImpl(productJpaRepository)
}
