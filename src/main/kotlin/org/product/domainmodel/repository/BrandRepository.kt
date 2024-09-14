package org.product.domainmodel.repository

import org.product.domainmodel.entities.Brand

interface BrandRepository {
    fun create(entity: Brand): Brand

    fun update(
        id: Long,
        modifier: (Brand) -> Brand,
    )

    fun delete(id: Long)

    fun findAll(): List<Brand>

    fun findByName(name: String): Brand?
}
