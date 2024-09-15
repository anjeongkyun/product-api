package org.product.persistence.mappers

import org.product.domainmodel.entities.Brand
import org.product.persistence.model.BrandDataModel

fun Brand.toDataModel() =
    BrandDataModel(
        id = id,
        name = name,
        isDeleted = false,
    )

fun BrandDataModel.toDomainEntity() =
    Brand(
        id = id,
        name = name,
    )
