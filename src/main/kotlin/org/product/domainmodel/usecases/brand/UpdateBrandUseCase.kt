package org.product.domainmodel.usecases.brand

import org.product.contracts.commands.UpdateBrandCommand
import org.product.domainmodel.repository.BrandRepository

class UpdateBrandUseCase(
    private val brandRepository: BrandRepository,
) {
    fun execute(command: UpdateBrandCommand) {
        brandRepository.update(
            id = command.id,
            modifier = { it.changeName(command.name) },
        )
    }
}
