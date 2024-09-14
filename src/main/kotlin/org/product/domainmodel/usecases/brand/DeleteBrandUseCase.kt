package org.product.domainmodel.usecases.brand

import org.product.contracts.commands.DeleteBrandCommand
import org.product.domainmodel.exceptions.ErrorProperty
import org.product.domainmodel.exceptions.InvariantViolationException
import org.product.domainmodel.repository.BrandRepository

class DeleteBrandUseCase(
    private val brandRepository: BrandRepository,
) {
    fun execute(command: DeleteBrandCommand) {
        try {
            brandRepository.delete(command.id)
        } catch (e: InvariantViolationException) {
            if (e.errorProperties.any {
                    it.key == "id" &&
                        it.reason === ErrorProperty.ErrorReason.NotFound
                }
            ) {
                throw e
            }
        }
    }
}
