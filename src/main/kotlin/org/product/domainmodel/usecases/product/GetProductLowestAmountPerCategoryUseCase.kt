package org.product.domainmodel.usecases.product

import org.product.contracts.queries.BrandAmountConfiguration
import org.product.contracts.queries.GetProductLowestAmountPerCategoryQueryResponse
import org.product.contracts.queries.ProductCategoryLowestAmountView
import org.product.domainmodel.entities.Product
import org.product.domainmodel.entities.ProductCategory
import org.product.domainmodel.reader.ProductReader
import org.product.domainmodel.valueobject.Order

class GetProductLowestAmountPerCategoryUseCase(
    private val productReader: ProductReader,
) {
    fun process(): GetProductLowestAmountPerCategoryQueryResponse =
        readLowestPriceProductsForCategories()
            .mapToLowestPriceView()
            .foldLowestAmountView()
            .toResponse()

    private fun readLowestPriceProductsForCategories(): List<Product> =
        productReader
            .readLowestPriceProductsForCategories(
                categories = ProductCategory.entries,
                order = Order.ASC,
            ).distinctBy { it.category }

    private fun List<Product>.mapToLowestPriceView(): List<Pair<ProductCategoryLowestAmountView, Long>> =
        map { product ->
            val lowestPriceView =
                ProductCategoryLowestAmountView(
                    productCategory = product.category.name,
                    lowestAmountBrand =
                        BrandAmountConfiguration(
                            brandName = product.brand.name,
                            productAmount = product.amount.amount,
                        ),
                )

            lowestPriceView to product.amount.amount
        }

    private fun List<Pair<ProductCategoryLowestAmountView, Long>>.foldLowestAmountView(): Pair<List<ProductCategoryLowestAmountView>, Long> =
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
