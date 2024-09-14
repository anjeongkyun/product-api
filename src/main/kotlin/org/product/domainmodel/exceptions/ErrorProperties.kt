package org.product.domainmodel.exceptions

data class ErrorProperty(
    val key: String,
    val reason: ErrorReason,
) {
    enum class ErrorReason {
        NotAvailable,
        Duplicated,
    }
}
