package org.product.data.mappers

import org.product.data.model.BrandDataModel
import org.product.domainmodel.entities.Brand

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
