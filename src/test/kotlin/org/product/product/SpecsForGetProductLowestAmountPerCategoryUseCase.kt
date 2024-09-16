package org.product.product

import autoparams.AutoSource
import autoparams.customization.Customization
import healingpaper.unittest.customizer.ProductCustomizer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.product.TestRepositoryConfiguration
import org.product.api.config.DataSourceConfig
import org.product.domainmodel.entities.Brand
import org.product.domainmodel.entities.Product
import org.product.domainmodel.entities.ProductCategory
import org.product.domainmodel.reader.ProductReader
import org.product.domainmodel.repository.BrandRepository
import org.product.domainmodel.repository.ProductRepository
import org.product.domainmodel.usecases.product.GetProductLowestAmountPerCategoryUseCase
import org.product.domainmodel.valueobject.Money
import org.product.domainmodel.valueobject.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import java.time.OffsetDateTime

@DataJpaTest
@ContextConfiguration(classes = [DataSourceConfig::class, TestRepositoryConfiguration::class])
class SpecsForGetProductLowestAmountPerCategoryUseCase {
    @Autowired
    private lateinit var productReader: ProductReader

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var brandRepository: BrandRepository

    @AutoSource
    @ParameterizedTest
    @Customization(ProductCustomizer::class)
    fun `Sut는 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회할 수 있다`(
        brandA: Brand,
        brandB: Brand,
        brandC: Brand,
        brandD: Brand,
        brandE: Brand,
        brandF: Brand,
        brandG: Brand,
        brandH: Brand,
        brandI: Brand,
        product: Product,
    ) {
        // Arrange
        arrangeEntities(
            listOf(brandA, brandB, brandC, brandD, brandE, brandF, brandG, brandH, brandI),
            product,
        )

        val sut = GetProductLowestAmountPerCategoryUseCase(productReader)

        // Act
        val actual = sut.process()

        // Assert
        assertThat(actual.lowestAmountProducts).isNotEmpty
        assertThat(actual.totalAmount).isGreaterThan(0L)
        ProductCategory.entries.forEach { category ->
            val lowestPriceView =
                actual.lowestAmountProducts.find { it.productCategory == category.name }!!

            val minPriceProduct =
                productReader
                    .readByCategoryOrderByAmount(category, Order.ASC)
                    .first()

            assertThat(lowestPriceView.lowestAmountBrand.productAmount).isEqualTo(minPriceProduct.amount.amount)
            assertThat(lowestPriceView.productCategory).isEqualTo(category.name)
        }

        val expectedTotalAmount =
            ProductCategory.entries
                .map { category ->
                    productReader
                        .readByCategoryOrderByAmount(category, Order.ASC)
                        .first()
                        .amount.amount
                }.sum()

        assertThat(actual.totalAmount).isEqualTo(expectedTotalAmount)
    }

    private fun arrangeEntities(
        brands: List<Brand>,
        product: Product,
    ) {
        val createdBrands =
            brands.map { brand ->
                brandRepository.create(brand.copy(id = null))
            }

        val categoryPrices =
            mapOf(
                ProductCategory.TOP to listOf(11200L, 10500L, 10000L, 10100L, 10700L, 11200L, 10500L, 10800L, 11400L),
                ProductCategory.OUTER to listOf(5500L, 5900L, 6200L, 5100L, 5000L, 7200L, 5800L, 6300L, 6700L),
                ProductCategory.PANTS to listOf(4200L, 3800L, 3300L, 3000L, 3800L, 4000L, 3900L, 3100L, 3200L),
                ProductCategory.SNEAKERS to listOf(9000L, 9100L, 9200L, 9500L, 9900L, 9300L, 9000L, 9700L, 9500L),
                ProductCategory.BAG to listOf(2000L, 2100L, 2200L, 2500L, 2300L, 2100L, 2200L, 2100L, 2400L),
                ProductCategory.HAT to listOf(1700L, 2000L, 1900L, 1500L, 1800L, 1600L, 1700L, 1600L, 1700L),
                ProductCategory.SOCKS to listOf(1800L, 2000L, 2200L, 2400L, 2100L, 2300L, 2100L, 2000L, 1700L),
                ProductCategory.ACCESSORY to listOf(2300L, 2200L, 2100L, 2000L, 2100L, 1900L, 2000L, 2000L, 2400L),
            )

        createdBrands.forEachIndexed { index, brand ->
            ProductCategory.entries.forEach { category ->
                val amount = categoryPrices[category]?.get(index) ?: 0L
                productRepository.create(
                    product.copy(
                        id = null,
                        category = category,
                        brand = brand,
                        amount = Money(amount),
                        createdDateTime = OffsetDateTime.now(),
                    ),
                )
            }
        }
    }
}
