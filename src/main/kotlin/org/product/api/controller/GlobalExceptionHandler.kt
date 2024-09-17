package org.product.api.controller

import org.product.api.exceptions.ErrorResponse
import org.product.api.exceptions.HttpException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HttpException::class)
    fun handleHttpException(exception: HttpException): ResponseEntity<ErrorResponse> =
        ResponseEntity(
            ErrorResponse(
                status = exception.status,
                message = exception.message,
                className = exception.className,
                errorProperties = exception.errorProperties,
            ),
            HttpStatus.valueOf(exception.status),
        )

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: RuntimeException): ResponseEntity<ErrorResponse> {
        exception.printStackTrace()
        return ResponseEntity(
            ErrorResponse(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "Unknown Internal Service Error",
                className = "RuntimeException",
                errorProperties = listOf(),
            ),
            HttpStatus.INTERNAL_SERVER_ERROR,
        )
    }
}
