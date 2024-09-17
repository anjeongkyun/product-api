package org.product.domainmodel.reader

import org.product.domainmodel.entities.Product
import org.product.domainmodel.entities.ProductCategory
import org.product.domainmodel.valueobject.Order

interface ProductReader {
    fun readByCategoryOrderByAmount(
        category: ProductCategory,
        order: Order,
    ): List<Product>

    fun readLowestPriceProductsForCategories(
        categories: List<ProductCategory>,
        order: Order,
    ): List<Product>

    fun readLowestPricedBrandProductsByCategories(categories: List<ProductCategory>): List<Product>

    fun readByBrandId(brandId: Long): List<Product>

    fun readLowestAndHighestPriceProductsByCategory(category: ProductCategory): List<Product>
}
