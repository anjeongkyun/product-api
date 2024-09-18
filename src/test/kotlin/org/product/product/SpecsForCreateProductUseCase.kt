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
import org.product.contracts.commands.CreateProductCommand
import org.product.domainmodel.entities.Brand
import org.product.domainmodel.entities.Product
import org.product.domainmodel.exceptions.InvariantViolationException
import org.product.domainmodel.repository.BrandRepository
import org.product.domainmodel.repository.ProductRepository
import org.product.domainmodel.usecases.product.CreateProductUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@DataJpaTest
@ContextConfiguration(classes = [DataSourceConfig::class, TestRepositoryConfiguration::class])
class SpecsForCreateProductUseCase {
    @Autowired
    private lateinit var brandRepository: BrandRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    @ParameterizedTest
    @AutoSource
    @Customization(ProductCustomizer::class)
    fun `Sut는 같은 브랜드에서 동일한 title인 상품을 생성하면 예외를 던져야한다`(
        brand: Brand,
        product: Product,
    ) {
        // Arrange
        val createdBrand = brandRepository.create(brand.copy(id = null))
        productRepository.create(product.copy(id = null, brand = createdBrand))

        val sut = CreateProductUseCase(productRepository, brandRepository)
        val command =
            CreateProductCommand(
                title = product.title,
                category = product.category,
                brandId = createdBrand.id!!,
                amount = product.amount.amount,
            )

        // Act && Assert
        assertThrows<InvariantViolationException> {
            sut.execute(command)
        }
    }

    @ParameterizedTest
    @AutoSource
    @Customization(ProductCustomizer::class)
    fun `Sut는 상품을 생성할 수 있다`(
        brand: Brand,
        product: Product,
    ) {
        // Arrange
        val createdBrand = brandRepository.create(brand.copy(id = null))
        val sut = CreateProductUseCase(productRepository, brandRepository)
        val command =
            CreateProductCommand(
                title = product.title,
                category = product.category,
                brandId = createdBrand.id!!,
                amount = product.amount.amount,
            )

        // Act
        sut.execute(command)

        // Assert
        val actual =
            productRepository.findByBrandIdAndTitle(
                brandId = createdBrand.id!!,
                title = command.title,
            )

        assertThat(actual).isNotNull
        assertThat(actual!!.id).isNotNull
        assertThat(actual.title).isEqualTo(command.title)
        assertThat(actual.category).isEqualTo(command.category)
        assertThat(actual.brand.id).isEqualTo(command.brandId)
        assertThat(actual.brand.name).isEqualTo(createdBrand.name)
        assertThat(actual.amount.amount).isEqualTo(command.amount)
        assertThat(actual.createdDateTime)
            .isCloseTo(OffsetDateTime.now(), within(1, ChronoUnit.SECONDS))
        assertThat(actual.updatedDateTime)
            .isCloseTo(OffsetDateTime.now(), within(1, ChronoUnit.SECONDS))
    }
}
