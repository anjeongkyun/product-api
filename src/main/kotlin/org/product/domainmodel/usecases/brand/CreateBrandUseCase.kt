package org.product.domainmodel.usecases.brand

import org.product.contracts.commands.CreateBrandCommand
import org.product.domainmodel.entities.Brand
import org.product.domainmodel.repository.BrandRepository

class CreateBrandUseCase(
    private val brandRepository: BrandRepository,
) {
    fun execute(command: CreateBrandCommand) {
        val creatingBrand = Brand.create(command.name)
        brandRepository.create(creatingBrand)
    }
}
