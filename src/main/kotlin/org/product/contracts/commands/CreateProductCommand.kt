package org.product.contracts.commands

import org.product.domainmodel.entities.ProductCategory

data class CreateProductCommand(
    val title: String,
    val category: ProductCategory,
    val brandId: Long,
    val amount: Long,
)
