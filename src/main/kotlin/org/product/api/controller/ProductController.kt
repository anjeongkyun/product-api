package org.product.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.product.api.exceptions.toHttpException
import org.product.contracts.commands.CreateProductCommand
import org.product.contracts.commands.DeleteProductCommand
import org.product.contracts.commands.UpdateProductCommand
import org.product.contracts.queries.GetLowestAmountBrandForAllCategoriesQueryView
import org.product.contracts.queries.GetLowestAndHighestAmountByCategoryQuery
import org.product.contracts.queries.GetProductLowestAmountPerCategoryQueryView
import org.product.domainmodel.entities.ProductCategory
import org.product.domainmodel.exceptions.InvariantViolationException
import org.product.domainmodel.usecases.product.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Product API Controller")
@RestController
class ProductController(
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductLowestAmountPerCategoryUseCase: GetProductLowestAmountPerCategoryUseCase,
    private val getLowestAmountBrandForAllCategoriesUseCase: GetLowestAmountBrandForAllCategoriesUseCase,
    private val getLowestAndHighestAmountByCategoryUseCase: GetLowestAndHighestAmountByCategoryUseCase,
) {
    @Operation(summary = "상품 생성 API")
    @PostMapping("/products")
    fun createProduct(command: CreateProductCommand) {
        try {
            createProductUseCase.execute(command)
        } catch (e: InvariantViolationException) {
            throw e.toHttpException(HttpStatus.BAD_REQUEST)
        }
    }

    @Operation(summary = "상품 수정 API")
    @PutMapping("/products")
    fun updateProduct(command: UpdateProductCommand) {
        try {
            updateProductUseCase.execute(command)
        } catch (e: InvariantViolationException) {
            throw e.toHttpException(HttpStatus.BAD_REQUEST)
        }
    }

    @Operation(summary = "상품 삭제 API")
    @DeleteMapping("/products")
    fun deleteProduct(command: DeleteProductCommand) {
        try {
            deleteProductUseCase.execute(command)
        } catch (e: InvariantViolationException) {
            throw e.toHttpException(HttpStatus.BAD_REQUEST)
        }
    }

    @Operation(summary = "카테고리 별 최저가격 브랜드와 상품 가격, 총액 조회 API")
    @GetMapping("/products/get-product-lowest-amount-per-category")
    fun getProductLowestAmountPerCategory(): GetProductLowestAmountPerCategoryQueryView = getProductLowestAmountPerCategoryUseCase.process()

    @Operation(
        summary = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액 조회 API",
    )
    @GetMapping("/products/get-lowest-amount-brand-for-all-categories")
    fun getLowestAmountBrandForAllCategories(): GetLowestAmountBrandForAllCategoriesQueryView =
        getLowestAmountBrandForAllCategoriesUseCase.process()

    @Operation(
        summary = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격 조회 API",
    )
    @GetMapping("/products/get-lowest-and-highest-amount-by-category")
    fun getLowestAndHighestAmountByCategory(
        @Parameter(
            schema = Schema(implementation = ProductCategory::class),
        )
        @RequestParam category: String,
    ) = getLowestAndHighestAmountByCategoryUseCase.process(
        GetLowestAndHighestAmountByCategoryQuery(category = category),
    )
}
