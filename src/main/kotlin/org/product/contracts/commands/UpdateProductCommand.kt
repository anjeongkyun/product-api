package org.product.contracts.commands

data class UpdateProductCommand(
    val id: Long,
    val title: String,
    val amount: Long,
)
