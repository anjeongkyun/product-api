package org.product.domainmodel.usecases.product

import org.product.contracts.queries.BrandAmountConfiguration
import org.product.contracts.queries.GetLowestAndHighestAmountByCategoryQuery
import org.product.contracts.queries.GetLowestAndHighestAmountByCategoryQueryView
import org.product.domainmodel.entities.ProductCategory
import org.product.domainmodel.reader.ProductReader

class GetLowestAndHighestAmountByCategoryUseCase(
    private val productReader: ProductReader,
) {
    fun process(query: GetLowestAndHighestAmountByCategoryQuery): GetLowestAndHighestAmountByCategoryQueryView =
        productReader
            .readLowestAndHighestPriceProductsByCategory(
                category = ProductCategory.valueOf(query.category),
            ).let { products ->
                val lowestPrice = products.minByOrNull { it.amount.amount }?.amount?.amount
                val highestPrice = products.maxByOrNull { it.amount.amount }?.amount?.amount

                GetLowestAndHighestAmountByCategoryQueryView(
                    category = query.category,
                    lowestPrices =
                        products.filter { it.amount.amount == lowestPrice }.map {
                            BrandAmountConfiguration(
                                brandName = it.brand.name,
                                productAmount = it.amount.amount,
                            )
                        },
                    highestPrices =
                        products.filter { it.amount.amount == highestPrice }.map {
                            BrandAmountConfiguration(
                                brandName = it.brand.name,
                                productAmount = it.amount.amount,
                            )
                        },
                )
            }
}
