package org.product.domainmodel.entities

import org.product.domainmodel.exceptions.ErrorProperty
import org.product.domainmodel.exceptions.InvariantViolationException

data class Brand(
    val id: Long?,
    val name: String,
) {
    companion object {
        fun create(name: String): Brand {
            validateInvariant(name)
            return Brand(null, name)
        }

        private fun validateInvariant(name: String) {
            if (name.isEmpty()) {
                throw InvariantViolationException(
                    message = "Brand name cannot be empty",
                    errorProperties =
                        listOf(
                            ErrorProperty(
                                key = "name",
                                reason = ErrorProperty.ErrorReason.NotAvailable,
                            ),
                        ),
                )
            }
        }
    }
}
