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
    fun findByCategoryOrderByAmountAsc(
        @Param("category") category: ProductCategory,
    ): List<ProductDataModel>

    @Query(
        """
        SELECT p FROM ProductDataModel p 
        WHERE p.category = :category 
        ORDER BY p.amount DESC 
        """,
    )
    fun findByCategoryOrderByAmountDesc(
        @Param("category") category: ProductCategory,
    ): List<ProductDataModel>

    fun findAllByIsDeletedFalse(): List<ProductDataModel>

    @Query(
        """
    SELECT p
    FROM ProductDataModel p
    WHERE p.amount = (
        SELECT MIN(p2.amount)
        FROM ProductDataModel p2
        WHERE p2.category = p.category
    )
    AND p.category IN :categories
    ORDER BY p.createdDateTime DESC
        """,
    )
    fun findLowestPriceProductsForCategories(
        @Param("categories") categories: List<ProductCategory>,
    ): List<ProductDataModel>
}
