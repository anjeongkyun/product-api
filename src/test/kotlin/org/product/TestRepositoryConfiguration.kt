package org.product

import org.product.data.repositories.BrandJpaRepository
import org.product.data.repositories.BrandRepositoryImpl
import org.product.domainmodel.repository.BrandRepository
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestRepositoryConfiguration {
    @Bean
    fun brandRepository(brandJpaRepository: BrandJpaRepository): BrandRepository = BrandRepositoryImpl(brandJpaRepository)
}
