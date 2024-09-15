package org.product.contracts.queries

data class GetProductLowestAmountPerCategoryQueryResponse(
    val lowestAmountProducts: List<ProductCategoryLowestAmountView>,
    val totalAmount: Long,
)
