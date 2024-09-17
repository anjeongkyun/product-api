package org.product.api.config

import org.product.domainmodel.reader.ProductReader
import org.product.domainmodel.repository.BrandRepository
import org.product.domainmodel.repository.ProductRepository
import org.product.domainmodel.usecases.brand.CreateBrandUseCase
import org.product.domainmodel.usecases.brand.DeleteBrandUseCase
import org.product.domainmodel.usecases.brand.UpdateBrandUseCase
import org.product.domainmodel.usecases.product.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig {
    @Bean
    fun createProductUseCase(
        productRepository: ProductRepository,
        brandRepository: BrandRepository,
    ): CreateProductUseCase = CreateProductUseCase(productRepository, brandRepository)

    @Bean
    fun updateProductUseCase(productRepository: ProductRepository): UpdateProductUseCase = UpdateProductUseCase(productRepository)

    @Bean
    fun deleteProductUseCase(productRepository: ProductRepository): DeleteProductUseCase = DeleteProductUseCase(productRepository)

    @Bean
    fun getLowestAmountBrandForAllCategoriesUseCase(productReader: ProductReader): GetLowestAmountBrandForAllCategoriesUseCase =
        GetLowestAmountBrandForAllCategoriesUseCase(productReader)

    @Bean
    fun getLowestAndHighestAmountByCategoryUseCase(productReader: ProductReader): GetLowestAndHighestAmountByCategoryUseCase =
        GetLowestAndHighestAmountByCategoryUseCase(productReader)

    @Bean
    fun getProductLowestAmountPerCategoryUseCase(productReader: ProductReader): GetProductLowestAmountPerCategoryUseCase =
        GetProductLowestAmountPerCategoryUseCase(productReader)

    @Bean
    fun createBrandUseCase(brandRepository: BrandRepository): CreateBrandUseCase = CreateBrandUseCase(brandRepository)

    @Bean
    fun deleteBrandUseCase(brandRepository: BrandRepository): DeleteBrandUseCase = DeleteBrandUseCase(brandRepository)

    @Bean
    fun updateBrandUseCase(brandRepository: BrandRepository): UpdateBrandUseCase = UpdateBrandUseCase(brandRepository)
}
