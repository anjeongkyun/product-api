package org.product.domainmodel.reader

import org.product.domainmodel.entities.Product
import org.product.domainmodel.entities.ProductCategory
import org.product.domainmodel.valueobject.Order

interface ProductReader {
    fun readTopByCategoryOrderByAmount(
        category: ProductCategory,
        order: Order,
    ): List<Product>
}
