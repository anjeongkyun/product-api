package org.product.api.controller

import org.product.api.exceptions.toHttpException
import org.product.contracts.commands.CreateProductCommand
import org.product.contracts.commands.DeleteProductCommand
import org.product.contracts.commands.UpdateProductCommand
import org.product.contracts.queries.GetLowestAndHighestAmountByCategoryQuery
import org.product.domainmodel.exceptions.InvariantViolationException
import org.product.domainmodel.usecases.product.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class ProductController(
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductLowestAmountPerCategoryUseCase: GetProductLowestAmountPerCategoryUseCase,
    private val getLowestAmountBrandForAllCategoriesUseCase: GetLowestAmountBrandForAllCategoriesUseCase,
    private val getLowestAndHighestAmountByCategoryUseCase: GetLowestAndHighestAmountByCategoryUseCase,
) {
    @PostMapping
    fun createProduct(command: CreateProductCommand) {
        try {
            createProductUseCase.execute(command)
        } catch (e: InvariantViolationException) {
            throw e.toHttpException(HttpStatus.BAD_REQUEST)
        }
    }

    @PutMapping
    fun updateProduct(command: UpdateProductCommand) {
        try {
            updateProductUseCase.execute(command)
        } catch (e: InvariantViolationException) {
            throw e.toHttpException(HttpStatus.BAD_REQUEST)
        }
    }

    @DeleteMapping
    fun deleteProduct(command: DeleteProductCommand) {
        try {
            deleteProductUseCase.execute(command)
        } catch (e: InvariantViolationException) {
            throw e.toHttpException(HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping
    fun getProductLowestAmountPerCategory() = getProductLowestAmountPerCategoryUseCase.process()

    @GetMapping
    fun getLowestAmountBrandForAllCategories() = getLowestAmountBrandForAllCategoriesUseCase.process()

    @GetMapping
    fun getLowestAndHighestAmountByCategory(
        @RequestParam query: GetLowestAndHighestAmountByCategoryQuery,
    ) = getLowestAndHighestAmountByCategoryUseCase.process(query)
}
