package org.product.contracts.queries

data class GetProductLowestAmountPerCategoryQueryView(
    val lowestAmountProducts: List<ProductCategoryLowestAmountView>,
    val totalAmount: Long,
)
