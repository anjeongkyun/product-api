package org.product.api.exceptions

import org.product.domainmodel.exceptions.ErrorProperty

data class HttpException(
    val status: Int,
    override val message: String,
    val className: String,
    val errorProperties: List<ErrorProperty>,
) : Error()
