package org.product.contracts.queries

data class ProductCategoryLowestAmountView(
    val productCategory: String,
    val lowestAmountBrand: BrandAmountConfiguration,
)
