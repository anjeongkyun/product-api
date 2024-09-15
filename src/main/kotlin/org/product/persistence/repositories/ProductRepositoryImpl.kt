package org.product.persistence.repositories

import org.product.domainmodel.entities.Product
import org.product.domainmodel.exceptions.ErrorProperty
import org.product.domainmodel.exceptions.InvariantViolationException
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
        id: Long,
        modifier: (Product) -> Product,
    ): Product =
        modifier(get(id))
            .let { productJpaRepository.save(it.toDataModel()) }
            .toDomainEntity()

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

    override fun findById(id: Long): Product? =
        productJpaRepository
            .findById(id)
            .map { it.toDomainEntity() }
            .orElse(null)

    private fun get(id: Long): Product =
        productJpaRepository
            .findById(id)
            .map { it.toDomainEntity() }
            .orElseThrow {
                throw InvariantViolationException(
                    message = "Product not found",
                    errorProperties =
                        listOf(
                            ErrorProperty(
                                key = "id",
                                reason = ErrorProperty.ErrorReason.NotFound,
                            ),
                        ),
                )
            }
}
