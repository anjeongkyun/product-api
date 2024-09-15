package org.product.persistence.repositories

import org.product.persistence.model.ProductDataModel
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaRepository : JpaRepository<ProductDataModel, Long> {
    fun findByBrandIdAndTitle(
        brandId: Long,
        title: String,
    ): ProductDataModel?
}
