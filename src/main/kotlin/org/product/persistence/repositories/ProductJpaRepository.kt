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

    @Query(
        value = """
    SELECT pdm.*
    FROM products pdm
    WHERE pdm.brand_id = (
        SELECT brand_id
        FROM (
            SELECT brand_id, SUM(amount) AS total_amount
            FROM products
            WHERE category IN :categories
            GROUP BY brand_id
            HAVING SUM(amount) = (
                SELECT MIN(total_amount)
                FROM (
                    SELECT SUM(amount) AS total_amount
                    FROM products
                    WHERE category IN :categories
                    GROUP BY brand_id
                ) AS totals
            )
        ) AS min_brands
    )
    AND pdm.category IN :categories
    """,
        nativeQuery = true,
    )
    fun findLowestPricedBrandProductsByCategories(
        @Param("categories") categories: List<String>,
    ): List<ProductDataModel>

    fun findByBrandId(brandId: Long): List<ProductDataModel>

    @Query(
        """
    SELECT p.*
    FROM products p
    WHERE p.amount = (
        SELECT MIN(amount)
        FROM products
        WHERE category = :category
    )
    OR p.amount = (
        SELECT MAX(amount)
        FROM products
        WHERE category = :category
    )
        """,
        nativeQuery = true,
    )
    fun findLowestAndHighestPriceProductsByCategory(
        @Param("category") category: String,
    ): List<ProductDataModel>
}
