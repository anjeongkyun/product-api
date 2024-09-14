package org.product.domainmodel.repository

import org.product.domainmodel.entities.Brand

interface BrandRepository {
    fun create(entity: Brand)

    fun update(
        id: String,
        modifier: (Brand) -> Brand,
    )

    fun delete(id: String)

    fun findAll(): List<Brand>

    fun findByName(name: String): Brand?
}
