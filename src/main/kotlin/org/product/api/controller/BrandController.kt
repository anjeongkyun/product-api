package org.product.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.product.api.exceptions.toHttpException
import org.product.contracts.commands.CreateBrandCommand
import org.product.contracts.commands.DeleteBrandCommand
import org.product.contracts.commands.UpdateBrandCommand
import org.product.domainmodel.exceptions.InvariantViolationException
import org.product.domainmodel.usecases.brand.CreateBrandUseCase
import org.product.domainmodel.usecases.brand.DeleteBrandUseCase
import org.product.domainmodel.usecases.brand.UpdateBrandUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "Brand API Controller")
@RestController
class BrandController(
    private val createBrandUseCase: CreateBrandUseCase,
    private val updateBrandUseCase: UpdateBrandUseCase,
    private val deleteBrandUseCase: DeleteBrandUseCase,
) {
    @Operation(summary = "브랜드 생성 API")
    @PostMapping("/brands")
    fun createBrands(
        @RequestBody command: CreateBrandCommand,
    ) {
        try {
            createBrandUseCase.execute(command)
        } catch (e: InvariantViolationException) {
            throw e.toHttpException(HttpStatus.BAD_REQUEST)
        }
    }

    @Operation(summary = "브랜드 수정 API")
    @PutMapping("/brands")
    fun updateBrand(
        @RequestBody command: UpdateBrandCommand,
    ) {
        try {
            updateBrandUseCase.execute(command)
        } catch (e: InvariantViolationException) {
            throw e.toHttpException(HttpStatus.BAD_REQUEST)
        }
    }

    @Operation(summary = "브랜드 삭제 API")
    @DeleteMapping("/brands")
    fun deleteBrand(
        @RequestBody command: DeleteBrandCommand,
    ) {
        try {
            deleteBrandUseCase.execute(command)
        } catch (e: InvariantViolationException) {
            throw e.toHttpException(HttpStatus.BAD_REQUEST)
        }
    }
}
