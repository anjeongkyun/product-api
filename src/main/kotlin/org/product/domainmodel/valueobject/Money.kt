package org.product.domainmodel.valueobject

import org.product.domainmodel.exceptions.ErrorProperty
import org.product.domainmodel.exceptions.InvariantViolationException

@JvmInline
value class Money(
    val amount: Long,
) {
    init {
        require(amount >= 0) {
            throw InvariantViolationException(
                message = "Money amount cannot be negative",
                errorProperties =
                    listOf(
                        ErrorProperty(
                            key = "amount",
                            reason = ErrorProperty.ErrorReason.NotAvailable,
                        ),
                    ),
            )
        }
    }
}
