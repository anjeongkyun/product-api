package org.product.contracts.commands

data class UpdateBrandCommand(
    val id: Long,
    val name: String,
)
