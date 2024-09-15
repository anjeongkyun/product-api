package org.product.domainmodel.repository

import org.product.domainmodel.entities.Brand

interface BrandRepository {
    fun create(entity: Brand): Brand

    fun update(
        id: Long,
        modifier: (Brand) -> Brand,
    ): Brand

    fun delete(id: Long)

    fun findAll(): List<Brand>

    fun findByName(name: String): Brand?

    fun get(id: Long): Brand
}
