package org.product.product

import autoparams.AutoSource
import autoparams.customization.Customization
import healingpaper.unittest.customizer.ProductCustomizer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.product.TestRepositoryConfiguration
import org.product.api.config.DataSourceConfig
import org.product.contracts.commands.DeleteProductCommand
import org.product.domainmodel.entities.Brand
import org.product.domainmodel.entities.Product
import org.product.domainmodel.repository.BrandRepository
import org.product.domainmodel.repository.ProductRepository
import org.product.domainmodel.usecases.product.DeleteProductUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [DataSourceConfig::class, TestRepositoryConfiguration::class])
class SpecsForDeleteProductUseCase {
    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @ParameterizedTest
    @AutoSource
    @Customization(ProductCustomizer::class)
    fun `Sut는 명령의 ID에 해당하는 상품을 삭제할 수 있다`(
        product: Product,
        brand: Brand,
    ) {
        // Arrange
        val createdBrand = brandRepository.create(brand.copy(id = null))
        val createdProduct = productRepository.create(product.copy(id = null, brand = createdBrand))
        val sut = DeleteProductUseCase(productRepository)
        val command = DeleteProductCommand(id = createdProduct.id!!)

        // Act
        sut.execute(command)

        // Assert
        val actual = productRepository.findAll().find { it.id == createdProduct.id }
        assertThat(actual).isNull()
    }
}
