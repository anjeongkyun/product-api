package org.product.domainmodel.usecases.product

import org.product.contracts.commands.DeleteProductCommand
import org.product.domainmodel.repository.ProductRepository

class DeleteProductUseCase(
    private val productRepository: ProductRepository,
) {
    fun execute(command: DeleteProductCommand) {
        productRepository.delete(command.id)
    }
}
