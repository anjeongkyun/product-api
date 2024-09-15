package org.product.product

import autoparams.AutoSource
import autoparams.customization.Customization
import healingpaper.unittest.customizer.ProductCustomizer
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.product.TestRepositoryConfiguration
import org.product.api.config.DataSourceConfig
import org.product.contracts.commands.UpdateProductCommand
import org.product.domainmodel.entities.Brand
import org.product.domainmodel.entities.Product
import org.product.domainmodel.exceptions.InvariantViolationException
import org.product.domainmodel.repository.BrandRepository
import org.product.domainmodel.repository.ProductRepository
import org.product.domainmodel.usecases.product.UpdateProductUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@DataJpaTest
@ContextConfiguration(classes = [DataSourceConfig::class, TestRepositoryConfiguration::class])
class SpecsForUpdateProductUseCase {
    @Autowired
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    @ParameterizedTest
    @AutoSource
    @Customization(ProductCustomizer::class)
    fun `Sut는 다른 상품중에 이미 같은 브랜드에서 동일한 title가 존재한다면 예외를 던져야한다`(
        brand: Brand,
        product1: Product,
        product2: Product,
    ) {
        // Arrange
        val createdBrand = brandRepository.create(brand.copy(id = null))
        val createdProduct1 = productRepository.create(product1.copy(id = null, brand = createdBrand))
        val createdProduct2 = productRepository.create(product2.copy(id = null, brand = createdBrand))

        val sut = UpdateProductUseCase(productRepository)
        val command =
            UpdateProductCommand(
                id = createdProduct1.id!!,
                title = createdProduct2.title,
                amount = createdProduct1.amount.amount,
            )

        // Act && Assert
        assertThrows<InvariantViolationException> {
            sut.execute(command)
        }
    }

    @ParameterizedTest
    @AutoSource
    @Customization(ProductCustomizer::class)
    fun `Sut는 상품을 수정할 수 있다`(
        brand: Brand,
        product: Product,
    ) {
        // Arrange
        val createdBrand = brandRepository.create(brand.copy(id = null))
        val sut = UpdateProductUseCase(productRepository)
        val createdProduct = productRepository.create(product.copy(id = null, brand = createdBrand))

        val command =
            UpdateProductCommand(
                id = createdProduct.id!!,
                title = createdProduct.title,
                amount = createdProduct.amount.amount,
            )

        // Act
        sut.execute(command)

        // Assert
        val actual = productRepository.findAll().firstOrNull()

        assertThat(actual).isNotNull
        assertThat(actual!!.title).isEqualTo(command.title)
        assertThat(actual.amount.amount).isEqualTo(command.amount)
        assertThat(actual.updatedDateTime)
            .isCloseTo(OffsetDateTime.now(), within(1, ChronoUnit.SECONDS))
    }
}
