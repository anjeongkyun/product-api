package org.product.domainmodel.usecases.product

import org.product.contracts.queries.BrandAmountConfiguration
import org.product.contracts.queries.GetProductLowestAmountPerCategoryQueryResponse
import org.product.contracts.queries.ProductCategoryLowestAmountView
import org.product.domainmodel.entities.ProductCategory
import org.product.domainmodel.reader.ProductReader
import org.product.domainmodel.valueobject.Order

class GetProductLowestAmountPerCategoryUseCase(
    private val productReader: ProductReader,
) {
    fun process(): GetProductLowestAmountPerCategoryQueryResponse =
        ProductCategory.entries
            .toList()
            .map(::getLowestPriceViewForCategory)
            .foldLowestCategoriesView()
            .toResponse()

    private fun getLowestPriceViewForCategory(category: ProductCategory): Pair<ProductCategoryLowestAmountView, Long> {
        val minPriceProduct =
            productReader
                .readTopByCategoryOrderByAmount(category, Order.ASC)
                .firstOrNull() ?: throw IllegalStateException("No product found for category: $category")

        val lowestPriceView =
            ProductCategoryLowestAmountView(
                productCategory = category.name,
                lowestAmountBrand =
                    BrandAmountConfiguration(
                        brandName = minPriceProduct.brand.name,
                        productAmount = minPriceProduct.amount.amount,
                    ),
            )

        return lowestPriceView to minPriceProduct.amount.amount
    }

    private fun List<Pair<ProductCategoryLowestAmountView, Long>>.foldLowestCategoriesView(): Pair<List<ProductCategoryLowestAmountView>, Long> =
        fold(
            initial = Pair(emptyList(), 0L),
            operation = { (categoryList, totalAmount), (categoryLowestAmountView, productAmount) ->
                Pair(categoryList + categoryLowestAmountView, totalAmount + productAmount)
            },
        )

    private fun Pair<List<ProductCategoryLowestAmountView>, Long>.toResponse(): GetProductLowestAmountPerCategoryQueryResponse =
        GetProductLowestAmountPerCategoryQueryResponse(
            lowestAmountProducts = first,
            totalAmount = second,
        )
}
