package org.product.persistence.repositories

import org.product.domainmodel.entities.Brand
import org.product.domainmodel.exceptions.ErrorProperty
import org.product.domainmodel.exceptions.InvariantViolationException
import org.product.domainmodel.repository.BrandRepository
import org.product.persistence.mappers.toDataModel
import org.product.persistence.mappers.toDomainEntity
import org.springframework.dao.DataIntegrityViolationException

class BrandRepositoryImpl(
    private val brandJpaRepository: BrandJpaRepository,
) : BrandRepository {
    override fun create(entity: Brand): Brand {
        try {
            return brandJpaRepository
                .save(entity.toDataModel())
                .toDomainEntity()
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
        id: Long,
        modifier: (Brand) -> Brand,
    ) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        val dataModel =
            brandJpaRepository
                .findById(id)
                .orElseThrow {
                    throw InvariantViolationException(
                        message = "Brand not found",
                        errorProperties =
                            listOf(
                                ErrorProperty(
                                    key = "id",
                                    reason = ErrorProperty.ErrorReason.NotFound,
                                ),
                            ),
                    )
                }
        val deletingDataModel = dataModel.delete()
        brandJpaRepository.save(deletingDataModel)
    }

    override fun findAll(): List<Brand> = brandJpaRepository.findAllByIsDeletedFalse().map { it.toDomainEntity() }

    override fun findByName(name: String): Brand? {
        TODO("Not yet implemented")
    }
}
