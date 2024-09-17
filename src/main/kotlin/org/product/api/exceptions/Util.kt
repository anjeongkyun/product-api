package org.product.api.exceptions

import org.product.domainmodel.exceptions.InvariantViolationException
import org.springframework.http.HttpStatusCode

fun InvariantViolationException.toHttpException(status: HttpStatusCode): HttpException =
    HttpException(
        status.value(),
        this.message,
        this.className,
        this.errorProperties,
    )
