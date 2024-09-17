package org.product.contracts.queries

data class GetLowestAndHighestAmountByCategoryQueryView(
    val category: String,
    val lowestPrices: List<BrandAmountConfiguration>,
    val highestPrices: List<BrandAmountConfiguration>,
)
