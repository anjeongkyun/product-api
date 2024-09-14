package org.product.data.repositories

import org.product.data.mappers.toDataModel
import org.product.data.mappers.toDomainEntity
import org.product.domainmodel.entities.Brand
import org.product.domainmodel.exceptions.ErrorProperty
import org.product.domainmodel.exceptions.InvariantViolationException
import org.product.domainmodel.repository.BrandRepository
import org.springframework.dao.DataIntegrityViolationException

class BrandRepositoryImpl(
    private val brandJpaRepository: BrandJpaRepository,
) : BrandRepository {
    override fun create(entity: Brand) {
        try {
            brandJpaRepository.save(entity.toDataModel())
        } catch (err: DataIntegrityViolationException) {
            throw InvariantViolationException(
                message = err.message ?: "",
                errorProperties =
                    listOf(
                        ErrorProperty(
                            key = "name",
                            reason = ErrorProperty.ErrorReason.Duplicated,
                        ),
                    ),
            )
        }
    }

    override fun update(
        id: String,
        modifier: (Brand) -> Brand,
    ) {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Brand> = brandJpaRepository.findAll().map { it.toDomainEntity() }

    override fun findByName(name: String): Brand? {
        TODO("Not yet implemented")
    }
}
