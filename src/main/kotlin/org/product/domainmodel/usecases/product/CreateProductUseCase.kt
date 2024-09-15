package org.product.domainmodel.usecases.product

import org.product.contracts.commands.CreateProductCommand
import org.product.domainmodel.entities.Product
import org.product.domainmodel.exceptions.ErrorProperty
import org.product.domainmodel.exceptions.InvariantViolationException
import org.product.domainmodel.repository.BrandRepository
import org.product.domainmodel.repository.ProductRepository
import org.product.domainmodel.valueobject.Money

class CreateProductUseCase(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
) {
    fun execute(command: CreateProductCommand) {
        assertDuplicatedTitle(command)
        val brand = brandRepository.get(command.brandId)
        val product =
            Product.create(
                title = command.title,
                category = command.category,
                brand = brand,
                amount = Money(amount = command.amount),
            )
        productRepository.create(product)
    }

    private fun assertDuplicatedTitle(command: CreateProductCommand) {
        productRepository
            .findByBrandIdAndTitle(
                brandId = command.brandId,
                title = command.title,
            )?.let {
                throw InvariantViolationException(
                    message = "Product with title ${command.title} already exists for brand ${command.brandId}",
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
