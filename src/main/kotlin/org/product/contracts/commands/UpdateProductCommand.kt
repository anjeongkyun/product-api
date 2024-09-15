package org.product.contracts.commands

import org.product.domainmodel.valueobject.Money

data class UpdateProductCommand(
    val id: Long,
    val title: String,
    val amount: Money,
)
