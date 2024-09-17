package org.product.contracts.queries

data class LowestAmountProductBrandConfiguration(
    val brandName: String,
    val categories: List<LowestAmountProductCategoriesConfiguration>,
    val totalAmount: Long,
)
