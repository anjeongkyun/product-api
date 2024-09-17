package org.product.api.exceptions

import org.product.domainmodel.exceptions.ErrorProperty

data class ErrorResponse(
    val status: Int,
    val message: String,
    val className: String,
    val errorProperties: List<ErrorProperty>,
)
