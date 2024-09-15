package org.product.persistence.repositories

import org.product.persistence.model.BrandDataModel
import org.springframework.data.jpa.repository.JpaRepository

interface BrandJpaRepository : JpaRepository<BrandDataModel, Long> {
    fun findAllByIsDeletedFalse(): List<BrandDataModel>
}
