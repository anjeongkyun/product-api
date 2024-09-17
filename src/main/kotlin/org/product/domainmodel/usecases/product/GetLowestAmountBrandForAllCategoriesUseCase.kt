package org.product.domainmodel.usecases.product

import org.product.contracts.queries.GetLowestAmountBrandForAllCategoriesQueryView
import org.product.contracts.queries.LowestAmountProductBrandConfiguration
import org.product.contracts.queries.LowestAmountProductCategoriesConfiguration
import org.product.domainmodel.entities.ProductCategory
import org.product.domainmodel.reader.ProductReader

class GetLowestAmountBrandForAllCategoriesUseCase(
    private val productReader: ProductReader,
) {
    fun process(): GetLowestAmountBrandForAllCategoriesQueryView {
        val lowestPricedBrandProducts = productReader.readLowestPricedBrandProductsByCategories(ProductCategory.entries)
        val brandMap = lowestPricedBrandProducts.groupBy { it.brand }
        val brand = brandMap.keys.first()
        val totalAmount = lowestPricedBrandProducts.sumOf { it.amount.amount }

        return GetLowestAmountBrandForAllCategoriesQueryView(
            lowestAmountProductBrand =
                LowestAmountProductBrandConfiguration(
                    brandName = brand.name,
                    categories =
                        lowestPricedBrandProducts.map {
                            LowestAmountProductCategoriesConfiguration(
                                categoryName = it.category.name,
                                amount = it.amount.amount,
                            )
                        },
                    totalAmount = totalAmount,
                ),
        )
    }
}
