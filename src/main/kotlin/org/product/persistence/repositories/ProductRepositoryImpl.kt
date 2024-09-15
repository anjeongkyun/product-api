package org.product.persistence.repositories

import org.product.domainmodel.entities.Product
import org.product.domainmodel.repository.ProductRepository
import org.product.persistence.mappers.toDataModel
import org.product.persistence.mappers.toDomainEntity

class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository,
) : ProductRepository {
    override fun create(entity: Product): Product =
        productJpaRepository
            .save(entity.toDataModel())
            .toDomainEntity()

    override fun update(
        id: String,
        modifier: (Product) -> Product,
    ): Product {
        TODO("Not yet implemented")
    }

    override fun findByBrandIdAndTitle(
        brandId: Long,
        title: String,
    ): Product? =
        productJpaRepository
            .findByBrandIdAndTitle(
                brandId = brandId,
                title = title,
            )?.toDomainEntity()

    override fun findAll(): List<Product> = productJpaRepository.findAll().map { it.toDomainEntity() }
}
