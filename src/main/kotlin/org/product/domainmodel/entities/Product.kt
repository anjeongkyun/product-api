package org.product.domainmodel.entities

import org.product.domainmodel.valueobject.Money
import java.time.OffsetDateTime

data class Product(
    val id: Long?,
    val title: String,
    val category: ProductCategory,
    val brand: Brand,
    val amount: Money,
    val createdDateTime: OffsetDateTime,
    val updatedDateTime: OffsetDateTime,
) {
    companion object {
        fun create(
            title: String,
            category: ProductCategory,
            brand: Brand,
            amount: Money,
        ): Product {
            val now = OffsetDateTime.now()
            return Product(
                id = null,
                title = title,
                category = category,
                brand = brand,
                amount = amount,
                createdDateTime = now,
                updatedDateTime = now,
            )
        }
    }
}
