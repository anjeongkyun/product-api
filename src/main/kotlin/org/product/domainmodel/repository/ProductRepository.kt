package org.product.domainmodel.repository

import org.product.domainmodel.entities.Product

interface ProductRepository {
    fun create(entity: Product): Product

    fun update(
        id: Long,
        modifier: (Product) -> Product,
    ): Product

    fun findByBrandIdAndTitle(
        brandId: Long,
        title: String,
    ): Product?

    fun findAll(): List<Product>

    fun findById(id: Long): Product?

    fun delete(id: Long)
}
