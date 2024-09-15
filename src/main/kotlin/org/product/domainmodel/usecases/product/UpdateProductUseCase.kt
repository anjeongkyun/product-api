package org.product.domainmodel.usecases.product

import org.product.contracts.commands.UpdateProductCommand
import org.product.domainmodel.exceptions.ErrorProperty
import org.product.domainmodel.exceptions.InvariantViolationException
import org.product.domainmodel.repository.ProductRepository
import org.product.domainmodel.valueobject.Money

class UpdateProductUseCase(
    private val productRepository: ProductRepository,
) {
    fun execute(command: UpdateProductCommand) {
        val updatingProduct = getProduct(command.id)
        assertDuplicatedTitle(
            brandId = updatingProduct.brand.id!!,
            titleToChange = command.title,
            productId = updatingProduct.id!!,
        )

        productRepository.update(
            command.id,
            modifier = {
                it.update(
                    title = command.title,
                    amount = Money(amount = command.amount),
                )
            },
        )
    }

    private fun getProduct(id: Long) =
        productRepository.findById(id)
            ?: throw InvariantViolationException(
                message = "Product with id $id not found",
                errorProperties =
                    listOf(
                        ErrorProperty(
                            key = "id",
                            reason = ErrorProperty.ErrorReason.NotFound,
                        ),
                    ),
            )

    private fun assertDuplicatedTitle(
        brandId: Long,
        titleToChange: String,
        productId: Long,
    ) {
        val foundProduct =
            productRepository
                .findByBrandIdAndTitle(
                    brandId = brandId,
                    title = titleToChange,
                )

        if (foundProduct != null && foundProduct.id != productId) {
            throw InvariantViolationException(
                message = "Product with title $titleToChange already exists for brand $brandId",
                errorProperties =
                    listOf(
                        ErrorProperty(
                            key = "title",
                            reason = ErrorProperty.ErrorReason.Duplicated,
                        ),
                    ),
            )
        }
    }
}
