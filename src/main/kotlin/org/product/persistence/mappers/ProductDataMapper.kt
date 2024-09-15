package org.product.persistence.mappers

import org.product.domainmodel.entities.Brand
import org.product.domainmodel.entities.Product
import org.product.domainmodel.valueobject.Money
import org.product.persistence.model.BrandDataModel
import org.product.persistence.model.ProductDataModel

fun Product.toDataModel(): ProductDataModel =
    ProductDataModel(
        id = this.id,
        title = this.title,
        amount = this.amount.amount,
        category = this.category,
        brand =
            BrandDataModel(
                id = this.brand.id,
                name = this.brand.name,
                isDeleted = false,
            ),
        createdDateTime = this.createdDateTime,
        updatedDateTime = this.updatedDateTime,
    )

fun ProductDataModel.toDomainEntity(): Product =
    Product(
        id = this.id,
        title = this.title,
        amount = Money(this.amount),
        category = this.category,
        brand =
            Brand(
                id = this.brand.id,
                name = this.brand.name,
            ),
        createdDateTime = this.createdDateTime,
        updatedDateTime = this.updatedDateTime,
    )
