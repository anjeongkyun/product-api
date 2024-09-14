package org.product.domainmodel.exceptions

class InvariantViolationException(
    override val message: String,
    val errorProperties: List<ErrorProperty>,
    val className: String = "InvariantViolationException",
) : Error()
