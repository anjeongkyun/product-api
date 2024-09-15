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
    override fun readTopByCategoryOrderByAmount(
        category: ProductCategory,
        order: Order,
    ): List<Product> = findTopByCategory(order, category).map { it.toDomainEntity() }

    private fun findTopByCategory(
        order: Order,
        category: ProductCategory,
    ): List<ProductDataModel> {
        if (order == Order.ASC) {
            return productJpaRepository.findTopByCategoryOrderByAmountAsc(category)
        } else {
            return productJpaRepository.findTopByCategoryOrderByAmountDesc(category)
        }
    }
}
