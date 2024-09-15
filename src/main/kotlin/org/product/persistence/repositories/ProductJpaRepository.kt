package org.product.persistence.repositories

import org.product.domainmodel.entities.ProductCategory
import org.product.persistence.model.ProductDataModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ProductJpaRepository : JpaRepository<ProductDataModel, Long> {
    fun findByBrandIdAndTitle(
        brandId: Long,
        title: String,
    ): ProductDataModel?

    @Query(
        """
        SELECT p FROM ProductDataModel p 
        WHERE p.category = :category 
        ORDER BY p.amount ASC
        """,
    )
    fun findTopByCategoryOrderByAmountAsc(
        @Param("category") category: ProductCategory,
    ): List<ProductDataModel>

    @Query(
        """
        SELECT p FROM ProductDataModel p 
        WHERE p.category = :category 
        ORDER BY p.amount DESC 
        """,
    )
    fun findTopByCategoryOrderByAmountDesc(
        @Param("category") category: ProductCategory,
    ): List<ProductDataModel>
}
