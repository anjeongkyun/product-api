package org.product.persistence.repositories

import org.product.domainmodel.entities.Product
import org.product.domainmodel.entities.ProductCategory
import org.product.domainmodel.reader.ProductReader
import org.product.domainmodel.valueobject.Order
import org.product.persistence.mappers.toDomainEntity
import org.product.persistence.model.ProductDataModel

class ProductReaderImpl(
    private val productJpaRepository: ProductJpaRepository,
) : ProductReader {
    override fun readByCategoryOrderByAmount(
        category: ProductCategory,
        order: Order,
    ): List<Product> =
        findByCategory(order, category)
            .map { it.toDomainEntity() }

    override fun readLowestPriceProductsForCategories(
        categories: List<ProductCategory>,
        order: Order,
    ): List<Product> =
        productJpaRepository
            .findLowestPriceProductsForCategories(
                ProductCategory.entries,
            ).map { it.toDomainEntity() }

    override fun readLowestPricedBrandProductsByCategories(categories: List<ProductCategory>): List<Product> =
        productJpaRepository
            .findLowestPricedBrandProductsByCategories(
                categories.map { it.name },
            ).map { it.toDomainEntity() }

    override fun readByBrandId(brandId: Long): List<Product> =
        productJpaRepository
            .findByBrandId(brandId)
            .map { it.toDomainEntity() }

    override fun readLowestAndHighestPriceProductsByCategory(category: ProductCategory): List<Product> =
        productJpaRepository
            .findLowestAndHighestPriceProductsByCategory(category.name)
            .map { it.toDomainEntity() }

    private fun findByCategory(
        order: Order,
        category: ProductCategory,
    ): List<ProductDataModel> {
        if (order == Order.ASC) {
            return productJpaRepository.findByCategoryOrderByAmountAsc(category)
        } else {
            return productJpaRepository.findByCategoryOrderByAmountDesc(category)
        }
    }
}
