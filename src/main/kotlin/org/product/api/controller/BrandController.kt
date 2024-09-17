package org.product.api.controller

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

@RestController
class BrandController(
    private val createBrandUseCase: CreateBrandUseCase,
    private val updateBrandUseCase: UpdateBrandUseCase,
    private val deleteBrandUseCase: DeleteBrandUseCase,
) {
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
