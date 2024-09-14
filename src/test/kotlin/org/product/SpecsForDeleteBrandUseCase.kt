package org.product

import autoparams.AutoSource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.product.api.config.DataSourceConfig
import org.product.contracts.commands.DeleteBrandCommand
import org.product.domainmodel.entities.Brand
import org.product.domainmodel.exceptions.InvariantViolationException
import org.product.domainmodel.repository.BrandRepository
import org.product.domainmodel.usecases.brand.DeleteBrandUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [DataSourceConfig::class, TestRepositoryConfiguration::class])
class SpecsForDeleteBrandUseCase {
    @Autowired
    private lateinit var brandRepository: BrandRepository

    @AutoSource
    @ParameterizedTest
    fun `Sut는 명령을 실행하면 올바르게 브랜드 엔터티를 삭제해야한다`(entity: Brand) {
        // Arrange
        val saved = brandRepository.create(entity)
        val sut = DeleteBrandUseCase(brandRepository)
        val command = DeleteBrandCommand(saved.id!!)

        // Act
        sut.execute(command)

        // Assert
        val actual = brandRepository.findAll().find { it.id === command.id }
        assertThat(actual).isNull()
    }

    @AutoSource
    @ParameterizedTest
    fun `Sut는 명령의 ID가 저장소에 없으면 예외를 던져야한다`(notFoundId: Long) {
        // Arrange
        val sut = DeleteBrandUseCase(brandRepository)
        val command = DeleteBrandCommand(notFoundId)

        // Act && Assert
        assertThrows<InvariantViolationException> { sut.execute(command) }
    }
}
